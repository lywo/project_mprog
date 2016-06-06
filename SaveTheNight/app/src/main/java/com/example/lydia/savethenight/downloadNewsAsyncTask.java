package com.example.lydia.savethenight;

import android.content.Context;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Lydia on 6-6-2016.
 */
public class downloadNewsAsyncTask extends AsyncTask<String, Void, Void> {
    Context context;
    newsActivity activity;

    @Override
    protected Void doInBackground(String... urls) {
        try {
            return loadXmlFromNetwork(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        // setContentView(R.layout.main);
        // Displays the HTML string in the UI via a WebView
//        WebView myWebView = (WebView) findViewById(R.id.webview);
//        myWebView.loadData(result, "text/html", null);

        // this.activity.setData(currentNewsItems);
    }

    private Void loadXmlFromNetwork(String newsURL) throws XmlPullParserException, IOException {
        InputStream inputStream = null;

        // Instantiate the parser
        newsFeedParser myXMLparser = new newsFeedParser();
        List<NewsItem> currentNewsItems = null;
        String title = null;
        String url = null;
        String description = null;

        StringBuilder htmlString = new StringBuilder();
        // htmlString.append("<h3>" + getResources().getString(R.string.page_title) + "</h3>");
        // htmlString.append("<em>" + getResources().getString(R.string.updated) + " " +
        // formatter.format(rightNow.getTime()) + "</em>");

        try {
            inputStream = downloadUrl(newsURL);
            currentNewsItems = newsFeedParser.parse(inputStream);
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        // StackOverflowXmlParser returns a List (called "entries") of Entry objects.
        // Each Entry object represents a single post in the XML feed.
        // This section processes the entries list to combine each entry with HTML markup.
        // Each entry is displayed in the UI as a link that optionally includes
        // a text summary.
        for (NewsItem newsItem : currentNewsItems) {
            htmlString.append("<p><a href='");
            htmlString.append(newsItem.link);
            htmlString.append("'>" + newsItem.title + "</a></p>");
            htmlString.append(newsItem.description);
        }
        return htmlString.toString();
    }

    // Given a string representation of a URL, sets up a connection and gets
// an input stream.
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setReadTimeout(10000 /* milliseconds */);
//        connection.setConnectTimeout(15000 /* milliseconds */);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        // Starts the query
        connection.connect();
        return connection.getInputStream();
    }
}
