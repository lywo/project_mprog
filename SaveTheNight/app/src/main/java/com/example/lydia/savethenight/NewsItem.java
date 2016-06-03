package com.example.lydia.savethenight;

import java.net.URL;

/**
 * Created by Lydia on 3-6-2016.
 */
public class NewsItem {
    private String title;
    private URL link;
    private String description;

    // constructor
    public NewsItem (String title, URL link, String description){
        super();
        this.title = title;
        this.link = link;
        this.description = description;
    }

    // methods
    public String getTitle (){return title;}
    public void setTitle (String title){this.title = title;}

    public String getDescription(){return description;}
    public void setDescription(String description) {this.description = description;}

    public URL getLink(){return link;}
    public void setLink(URL link) {this.link = link;}
}
