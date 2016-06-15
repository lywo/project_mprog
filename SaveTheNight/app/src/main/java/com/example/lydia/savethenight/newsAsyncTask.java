package com.example.lydia.savethenight;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Lydia on 3-6-2016.
 */
public class newsAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;
    newsActivity activity;

    // Declare variables.
    static final String KEY_ITEM = "item";
    static final String KEY_LINK = "link";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_TITLE = "title";

    // constructor
    public newsAsyncTask(newsActivity newsActivity) {
        this.activity = newsActivity;
        this.context = this.activity.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "News is loading from server", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {
        return HTTPRequestHelper.serverDownload(params);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        ArrayList<NewsItem> currentNewsItems = new ArrayList<>();
        super.onPostExecute(result);
        if (result.length() == 0) {
            Toast.makeText(context, "No news was found", Toast.LENGTH_SHORT).show();
        } else {
            if (result.startsWith("ERROR:")) {
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            }
            else {
                // parse RSS feed
                NewsItem newsItem;

                try {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(new StringReader(result));

                    int event = xpp.getEventType();

                    while (event != XmlPullParser.END_DOCUMENT) {
                        String name = xpp.getName();

                        if (XmlPullParser.START_TAG == event && name.equalsIgnoreCase(KEY_ITEM)) {
                            newsItem = new NewsItem(null, null, null);
                            event = xpp.nextTag();
                            name = xpp.getName();

                            assert name != null;
                            while (!(event == XmlPullParser.END_TAG && name.equals(KEY_DESCRIPTION))) {
                                if (name != null && event == XmlPullParser.START_TAG) {
                                    switch (name) {
                                        // start en title
                                        case KEY_TITLE:
                                            event = xpp.nextToken();
                                            String title = xpp.getText();
                                            newsItem.setTitle(title);
                                            break;
                                        case KEY_LINK:
                                            event = xpp.next();
                                            newsItem.setLink(xpp.getText());
                                            break;
                                        case KEY_DESCRIPTION:
                                            event = xpp.next();
                                            newsItem.setDescription(xpp.getText());
                                            break;
                                        default:
                                            event = xpp.nextTag();
                                    }
                                    name = xpp.getName();
                                }
                                else{
                                    event = xpp.next();
                                    name= xpp.getName();
                                }
                            }
                            currentNewsItems.add(newsItem);
                        }
                        event = xpp.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // fill data in ListView
            this.activity.setData(currentNewsItems);
        }
    }
}

