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
                NewsItem newsItem = null;

                try {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(new StringReader(result));

                    int event = xpp.getEventType();

                    while (event != XmlPullParser.END_DOCUMENT) {
                        String name = xpp.getName();

                        if (XmlPullParser.START_TAG == event && name.equalsIgnoreCase(KEY_ITEM)) {
                            Log.d("making new newsitem", "making a new newsitem" + name);
                            newsItem = new NewsItem(null, null, null);
                            event = xpp.next();
                            name = xpp.getName();

                            assert name != null;
                            while (!(event == XmlPullParser.END_TAG && name.equals(KEY_DESCRIPTION))) {
                                Log.d("in item", "looping in item" + name);
                                if (name != null) {
                                    switch (name) {
                                        // start en title
                                        case KEY_TITLE:
                                            Log.d("title", "found title");
                                            event = xpp.next();
                                            newsItem.setTitle(xpp.getText());
                                            break;
                                        case KEY_LINK:
                                            Log.d("link", "found link");
                                            event = xpp.next();
                                            newsItem.setLink(xpp.getText());
                                            break;
                                        case KEY_DESCRIPTION:
                                            Log.d("description", "found description");
                                            event = xpp.next();
                                            newsItem.setDescription(xpp.getText());
                                            currentNewsItems.add(newsItem);
                                            break;
                                        default:
                                            break;
                                    }
                                }
//                                break;
                                event = xpp.next();
                            }
                        }
                        Log.d("next", "go to next event");
                        event = xpp.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // fill data in ListView
            Log.d("return ArrayList", "AynsTask returned:" + currentNewsItems.toString());
            this.activity.setData(currentNewsItems);
        }
    }
}

//                            switch (event) {
//                                //                            case XmlPullParser.START_TAG:
//                                //                                if (name.equalsIgnoreCase(KEY_ITEM)) {
//                                //                                    Log.d("making new newsitem", "making a new newsitem");
//                                //                                    newsItem = new NewsItem(title, link, description);
//                                //                                }
//                                //                                break;
//
//                                case XmlPullParser.TEXT:
//                                    curText = xpp.getText();
//                                    break;
//
//                                case XmlPullParser.END_TAG:
//                                    if (name.equalsIgnoreCase(KEY_ITEM)) {
//                                        Log.d("adding new newsitem", "adding a new newsitem");
//                                        currentNewsItems.add(newsItem);
//                                    } else if (name.equalsIgnoreCase(KEY_TITLE)) {
//                                        assert newsItem != null;
//                                        newsItem.setTitle(curText);
//                                    } else if (name.equalsIgnoreCase(KEY_LINK)) {
//                                        assert newsItem != null;
//                                        newsItem.setLink(curText);
//                                    } else if (name.equalsIgnoreCase(KEY_DESCRIPTION)) {
//                                        assert newsItem != null;
//                                        newsItem.setDescription(curText);
//                                    }
//                                    break;
//                                default:
//                                    event = xpp.next();
//                            }
//                        }
//                        event = xpp.next();
//                    }


