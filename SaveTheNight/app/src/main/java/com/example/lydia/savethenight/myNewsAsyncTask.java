package com.example.lydia.savethenight;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Lydia on 14-6-2016.
 */
public class myNewsAsyncTask extends AsyncTask<String, Integer, String>{
    Context context;
    Activity activity;

//    // Declare variables.
//    static final String KEY_ITEM = "item";
//    static final String KEY_LINK = "link";
//    static final String KEY_DESCRIPTION = "description";
//    static final String KEY_TITLE = "title";

    // constructor
    public myNewsAsyncTask(newsActivity newsActivity) {
        this.activity = newsActivity;
        this.context = this.activity.getApplicationContext();
    }

    @Override
    protected void onPreExecute(){
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
    protected void onPostExecute(String result){
        ArrayList<NewsItem> currentNewsItems = new ArrayList<>();
        super.onPostExecute(result);
        if (result.length() == 0) {
            Toast.makeText(context, "No news was found", Toast.LENGTH_SHORT).show();
        } else {
            if (result.startsWith("ERROR:")) {
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            } else {
                // convert RSS feed XML result to JSON
                JSONObject jsonObj = null;
                try {
                    jsonObj = XML.toJSONObject(result);
                } catch (JSONException e) {
                    Log.e("JSON exception", e.getMessage());
                    e.printStackTrace();
                }

                Log.d("XML", result);

                Log.d("JSON", jsonObj.toString());

                // parse JSON

            }
            activity.setData(currentNewsItems);
        }
    }
}
