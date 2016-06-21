package com.example.lydia.savethenight;
/**
 * Created by Lydia on 3-6-2016.
 * NewsAsyncTask.java is the AsyncTask to handle a Http request needed to get news Feed xml from NOS
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/*
NewsAsyncTask calls HttpReQuestHelper
Handles response
If ok, parsing result
Return result by calling setData function from NewsActivity with ArrayList with NewsItems
 */
public class NewsAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;
    NewsActivity activity;

    // Declare variables.
    static final String KEY_ITEM = "item";
    static final String KEY_LINK = "link";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_TITLE = "title";

    // constructor
    public NewsAsyncTask(NewsActivity newsActivity) {
        this.activity = newsActivity;
        this.context = this.activity.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "News is loading from server", Toast.LENGTH_SHORT).show();
    }

    /*
    Initiate HTTPRequestHelper and call it's function serverDownload
     */
    @Override
    protected String doInBackground(String... params) {
        return HTTPRequestHelper.serverDownload(params);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    /*
    Deal with response
    Returns ArrayList by calling setData function in NewsActivity
     */
    @Override
    protected void onPostExecute(String result) {
        ArrayList<NewsItem> currentNewsItems = new ArrayList<>();
        super.onPostExecute(result);

        // Nothing was returned
        if (result.length() == 0) {
            Toast.makeText(context, "No news was found", Toast.LENGTH_SHORT).show();
        }

        // Read response code
        else {
            // Response consists of Error
            if (result.startsWith("ERROR:")) {
                // Show Error to user
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            }
            else {
                // Parse RSS feed
                NewsItem newsItem;
                try {
                    // Initiate xml Parser
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(new StringReader(result));

                    int event = xpp.getEventType();

                    // Loop over xml file
                    while (event != XmlPullParser.END_DOCUMENT) {
                        String name = xpp.getName();

                        // Look for an Item start tag and go read the item
                        if (XmlPullParser.START_TAG == event && name.equalsIgnoreCase(KEY_ITEM)) {
                            newsItem = new NewsItem(null, null, null);
                            event = xpp.nextTag();
                            name = xpp.getName();

                            // Until Description End Tag is found, read in item
                            assert name != null;
                            while (!(event == XmlPullParser.END_TAG && name.equals(KEY_DESCRIPTION))) {

                                // Look in Item for Start Tags
                                if (name != null && event == XmlPullParser.START_TAG) {
                                    // Look for Title Link and Description
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

                                // No StartTag in Item, go to Next and update name
                                else{
                                    event = xpp.next();
                                    name= xpp.getName();
                                }
                            }
                            // Add filled NewsItem to ArrayList with NewsItems
                            currentNewsItems.add(newsItem);
                        }
                        // Look for next Item
                        event = xpp.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Call setData from NewsActivity with ArrayList to fill ListView
            this.activity.setData(currentNewsItems);
        }
    }
}

