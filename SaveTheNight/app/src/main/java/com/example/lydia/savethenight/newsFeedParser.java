package com.example.lydia.savethenight;

import android.util.Log;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lydia on 6-6-2016.
 */
public class NewsFeedParser {
    private static final String ns = null;
    private static ArrayList <NewsItem> currentNewsItems = new ArrayList<>();

    public static ArrayList<NewsItem> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            parser.nextTag();
            readFeed(parser);
            return currentNewsItems;
        } catch (Exception e){
            Log.d(" ", e.getMessage());
            return null;
        }
    }

    /*
    Read the RSS feed and select all news items, put them in currentNewsItems.
    Run readItem() to fill in title, description and link.
     */
    private static ArrayList<NewsItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        Log.d(" ", parser.getName());
//        parser.require(XmlPullParser.START_TAG, ns, "rss");
        parser.require(XmlPullParser.START_TAG, ns, "channel");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("item")) {
                currentNewsItems.add(NewsItem.readItem(parser));
            } else {
                skip(parser);
            }
        }
        return currentNewsItems;
    }

    public static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
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
