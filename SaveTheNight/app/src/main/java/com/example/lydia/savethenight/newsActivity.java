package com.example.lydia.savethenight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class newsActivity extends AppCompatActivity {
    ArrayList<NewsItem> currentNewsItems = new ArrayList<>();
    private static final String URL = "http://feeds.nos.nl/nosjournaal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        currentNewsItems = new downloadNewsAsyncTask(newsActivity.this).execute(URL);
    }

    protected void setData(ArrayList<NewsItem> currentNews){
        currentNewsItems = currentNews;
        NewsAdapter myAdapter  = new NewsAdapter(this, currentNewsItems);
        myAdapter.notifyDataSetChanged();
    }
}
