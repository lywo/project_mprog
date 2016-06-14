package com.example.lydia.savethenight;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Lydia on 14-6-2016.
 */
public class myNewsAsyncTask extends AsyncTask<String, Integer, String>{
    Context context;
    Activity activity;

    // Declare variables.
    static final String KEY_ITEM = "item";
    static final String KEY_LINK = "link";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_TITLE = "title";


    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }

    @Override
    protected void onPreExecute(){
        Toast.makeText(context, "News is loading from server", Toast.LENGTH_SHORT).show();
    }
}
