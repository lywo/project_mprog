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

}
