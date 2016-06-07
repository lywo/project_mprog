package com.example.lydia.savethenight;

import android.content.Context;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Lydia on 6-6-2016.
 */
public class DownloadNewsAsyncTask extends AsyncTask<String, Void, String> {
    Context context;
    newsActivity activity;
    ArrayList<NewsItem> currentNewsItems = null;

    // constructor
    public DownloadNewsAsyncTask(newsActivity newsActivity){
        this.activity = newsActivity;
        this.context = this.activity.getApplicationContext();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return loadXmlFromNetwork(params[0]);
        } catch (IOException e) {
            return "";
        } catch (XmlPullParserException e) {
            return "";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        this.activity.setData(currentNewsItems);
    }

    /*
    load inputStream and fill currentNewsItems with NewsItem objects.
     */
    private String loadXmlFromNetwork(String newsURL) throws XmlPullParserException, IOException {
        InputStream inputStream = null;

        // Instantiate the parser
        NewsFeedParser myXMLparser = new NewsFeedParser();

        try {
            inputStream = downloadUrl(newsURL);
            currentNewsItems = myXMLparser.parse(inputStream);
            // close inputStream
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        String emptyReturn = "";
        return emptyReturn;
    }

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
