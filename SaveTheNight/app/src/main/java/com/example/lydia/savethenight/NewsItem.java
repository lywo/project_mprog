package com.example.lydia.savethenight;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Lydia on 3-6-2016.
 */
public class NewsItem {
    public String title;
    public String link;
    public String description;

    private static final String ns = null;

    // constructor
    public NewsItem(String title, String link, String description) {
        super();
        this.title = title;
        this.link = link;
        this.description = description;
    }

    // methods
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

//    /*
//    Parses the contents of an Item. If it encounters a title, description, or link tag, hands them off
//    to their respective "read" methods for processing. Otherwise, skips the tag.
//    */
//    public static NewsItem readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
//        parser.require(XmlPullParser.START_TAG, ns, "item");
//        String title = null;
//        String description = null;
//        String link = null;
//        while (parser.next() != XmlPullParser.END_TAG) {
//            if (parser.getEventType() != XmlPullParser.START_TAG) {
//                continue;
//            }
//            String name = parser.getName();
//            if (name.equals("title")) {
//                title = readTitle(parser);
//            }
//            else if (name.equals("description")) {
//                description = readDescription(parser);
//            }
//            else if (name.equals("link")) {
//                link = readLink(parser);
//            }
//            else {
//                NewsFeedParser.skip(parser);
//            }
//        }
//        return new NewsItem(title, description, link);
//    }
//
//    // Processes title tags in the feed.
//    private static String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
//        parser.require(XmlPullParser.START_TAG, ns, "title");
//        String title = readText(parser);
//        parser.require(XmlPullParser.END_TAG, ns, "title");
//        parser.nextTag();
//        return title;
//    }
//
//    // Processes link tags in the feed.
//    private static String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
//        String link = "";
//        parser.require(XmlPullParser.START_TAG, ns, "link");
//        String tag = parser.getName();
//        String relType = parser.getAttributeValue(null, "rel");
//        if (tag.equals("link")) {
//            if (relType.equals("alternate")) {
//                link = parser.getAttributeValue(null, "href");
//                parser.nextTag();
//            }
//        }
//        parser.require(XmlPullParser.END_TAG, ns, "link");
//        return link;
//    }
//
//    // Processes summary tags in the feed.
//    private static String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
//        parser.require(XmlPullParser.START_TAG, ns, "description");
//        String summary = readText(parser);
//        parser.require(XmlPullParser.END_TAG, ns, "description");
//        // parser.nextTag();
//        return summary;
//    }
//
//    // For the tags title and summary, extracts their text values.
//    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
//        String ns = "";
//        if (parser.next() == XmlPullParser.TEXT) {
//            ns = parser.getText();
//            parser.nextTag();
//        }
//        return ns;
//    }
}
