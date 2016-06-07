package com.example.lydia.savethenight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class newsActivity extends AppCompatActivity {
    private static final String URL = "http://feeds.nos.nl/nosjournaal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        DownloadNewsAsyncTask myAsyncTask = new DownloadNewsAsyncTask(this);
        myAsyncTask.execute(URL);
    }

    protected void setData(ArrayList<NewsItem> currentNews){
        NewsAdapter myAdapter  = new NewsAdapter(this, currentNews);
        myAdapter.notifyDataSetChanged();
    }
}
