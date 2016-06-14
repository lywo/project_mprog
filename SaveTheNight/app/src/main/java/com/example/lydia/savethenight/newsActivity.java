package com.example.lydia.savethenight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class newsActivity extends AppCompatActivity {
    private static final String URL = "http://feeds.nos.nl/nosjournaal";
    NewsAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsAsyncTask newsFeedHandler = new newsAsyncTask(newsActivity.this);
        newsFeedHandler.execute(URL);
    }

    /*
    Set data from AsyncTask in ListView
     */
    protected void setData(ArrayList<NewsItem> currentNews){
        ListView newsLV = (ListView) findViewById(R.id.newsLV);
        myAdapter  = new NewsAdapter(this, currentNews);
        if (currentNews!=null) {
            newsLV.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
