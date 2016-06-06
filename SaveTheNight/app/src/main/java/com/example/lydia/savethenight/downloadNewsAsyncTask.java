package com.example.lydia.savethenight;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lydia on 6-6-2016.
 */
public class downloadNewsAsyncTask extends AsyncTask<String, Void, String> {
    Context context;
    newsActivity activity;
    ArrayList<NewsItem> currentNewsItems = null;

    // constructor
    public downloadNewsAsyncTask(newsActivity newsActivity){
        this.activity = newsActivity;
        this.context = this.activity.getApplicationContext();
    }


    @Override
    protected String doInBackground(String... params) {
        StringBuilder htmlString = new StringBuilder();
         /*
        De newsFeedParser returns a List (called "currentNewsItems") of NewsItem objects.
        Each NewsItem object represents a single post in the XML feed.
        This section processes the currentNewsItems list to combine each NewsItem with HTML markup.
        Each NewsItem is displayed in the UI as a title, link and description in a ListView.
        */
        for (NewsItem newsItem : currentNewsItems) {
            htmlString.append("<p><a href='");
            htmlString.append(newsItem.link);
            htmlString.append("'>" + newsItem.title + "</a></p>");
            htmlString.append(newsItem.description);
        }
        return htmlString.toString();
    }

    // @Override
    protected void onPostExecute(String result) {
        loadXmlFromNetwork(result);
        this.activity.setData(currentNewsItems);
    }


    private List<NewsItem> loadXmlFromNetwork(String newsURL) throws XmlPullParserException, IOException {
        InputStream inputStream = null;

        // Instantiate the parser
        newsFeedParser myXMLparser = new newsFeedParser();
        List<NewsItem> currentNewsItems = null;
        String title = null;
        String url = null;
        String description = null;

        try {
            inputStream = downloadUrl(newsURL);
            currentNewsItems = myXMLparser.parse(inputStream);
            // close inputStream
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return currentNewsItems;
    }

        /*
        De newsFeedParser returns a List (called "currentNewsItems") of NewsItem objects.
        Each NewsItem object represents a single post in the XML feed.
        This section processes the currentNewsItems list to combine each NewsItem with HTML markup.
        Each NewsItem is displayed in the UI as a title, link and description in a ListView.
        */
//        for (NewsItem newsItem : currentNewsItems) {
//            htmlString.append("<p><a href='");
//            htmlString.append(newsItem.link);
//            htmlString.append("'>" + newsItem.title + "</a></p>");
//            htmlString.append(newsItem.description);
//        }
//        return htmlString.toString();
//    }

    /*
    Sets up a connection and gets an input stream.
     */
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);

        // Starts the query
        connection.connect();
        return connection.getInputStream();
    }
}
