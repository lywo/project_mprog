package com.example.lydia.savethenight;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Lydia on 3-6-2016.
 */
public class newsAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;
    newsActivity activity;

    // constructor
    public newsAsyncTask(newsActivity newsActivity){
        this.activity = newsActivity;
        this.context = this.activity.getApplicationContext();
    }

    @Override
    protected void onPreExecute(){
        Toast.makeText(context, "News is loading from server", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {
        return HTTPRequestHelper.serverDownload( params);
    }

    @Override
    protected void onProgressUpdate(Integer ... values){
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute (String result) {
        ArrayList<NewsItem> currentNewsItems = new ArrayList<>();
        super.onPostExecute(result);
        if (result.length() == 0) {
            Toast.makeText(context, "No news was found", Toast.LENGTH_SHORT).show();
        }
        else if (result.startsWith("ERROR:")) {
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            }
        else {
            // parse RSS feed
            try {
                // for each <item>  get <title> <link> en <description>
                String title = null;
                String description = null;
                String linkString = null;
                URL link = new URL(linkString);

                //  adding values to dataset (title, link description)
                NewsItem newNewsItem = new NewsItem(title, link, description);
                currentNewsItems.add(newNewsItem);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            // update ArrayList
            // call MainActivity to set data to ListView
            this.activity.setData(currentNewsItems);
        }
    }
}
