package com.example.lydia.savethenight;
/**
 * Created by Lydia on 3-6-2016.
 * NewsItem.java is a NewsItem class
 */

/*
Allows components of the News returned from newsFeed Parser to be saved together
Has a constructor and 3 functions
 */
public class NewsItem {
    public String title;
    public String link;
    public String description;

    // constructor
    public NewsItem(String title, String link, String description) {
        super();
        this.title = title;
        this.link = link;
        this.description = description;
    }

    // Methods
    // Only setters are used
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
