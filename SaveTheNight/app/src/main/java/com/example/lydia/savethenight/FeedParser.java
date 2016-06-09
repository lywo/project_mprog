package com.example.lydia.savethenight;

import android.net.ParseException;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lydia on 9-6-2016.
 */
public class FeedParser {
    // Constants indicting XML element names of interest
    private static final int TAG_TITLE = 1;
    private static final int TAG_LINK = 2;
    private static final int TAG_DESCRIPTION = 3;

    // We don't use XML namespaces
    private static final String ns = null;

    /*
     Parse an Atom feed, returning a collection of NewsItem objects.
     */
    public ArrayList<NewsItem> parse(InputStream in)
            throws XmlPullParserException, IOException, ParseException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    /**
     * Decode a feed attached to an XmlPullParser.
     */
    private ArrayList<NewsItem> readFeed(XmlPullParser parser)
            throws XmlPullParserException, IOException, ParseException {
        ArrayList<NewsItem> newsItems = new ArrayList<NewsItem>();
        parser.require(XmlPullParser.START_TAG, ns, "html");
        //parser.require(XmlPullParser.START_TAG, ns, "rss");
        String testName = parser.getName();
       //  parser.require(XmlPullParser.START_TAG, ns, "channel");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("item")) {
                newsItems.add(readItem(parser));
            } else {
                skip(parser);
            }
        }
        return newsItems;
    }

    /**
     * Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them
     * off to their respective "read" methods for processing. Otherwise, skips the tag.
     */
    private NewsItem readItem(XmlPullParser parser)
            throws XmlPullParserException, IOException, ParseException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String link = null;
        String description = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")){
                title = readTag(parser, TAG_TITLE);
            } else if (name.equals("link")) {
                String tempLink = readTag(parser, TAG_LINK);
                if (tempLink != null) {
                    link = tempLink;
                }
                // Example: <title>Article title</title>
            } else if (name.equals("description")) {
                    description = readTag(parser, TAG_LINK);
            } else {
                skip(parser);
            }
        }
        return new NewsItem(title, link, description);
    }

    /**
     * Process an incoming tag and read the selected value from it.
     */
    private String readTag(XmlPullParser parser, int tagType)
            throws IOException, XmlPullParserException {
        String tag = null;
        String endTag = null;

        switch (tagType) {
            case TAG_TITLE:
                return readBasicTag(parser, "title");
            case TAG_LINK:
                return readAlternateLink(parser);
            case TAG_DESCRIPTION:
                return readBasicTag(parser, "description");
            default:
                throw new IllegalArgumentException("Unknown tag type: " + tagType);
        }
    }

    /**
     * Reads the body of a basic XML tag, which is guaranteed not to contain any nested elements.
     */
    private String readBasicTag(XmlPullParser parser, String tag)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String result = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    /**
     * Processes link tags in the feed.
     */
    private String readAlternateLink(XmlPullParser parser)
            throws IOException, XmlPullParserException {
        String link = null;
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String tag = parser.getName();
        String relType = parser.getAttributeValue(null, "rel");
        if (relType.equals("alternate")) {
            link = parser.getAttributeValue(null, "href");
        }
        while (true) {
            if (parser.nextTag() == XmlPullParser.END_TAG) break;
            // Intentionally break; consumes any remaining sub-tags.
        }
        return link;
    }

    /**
     * For the tags title and summary, extracts their text values.
     */
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = null;
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    /**
     * Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
     * if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
     * finds the matching END_TAG (as indicated by the value of "depth" being 0).
     */
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}