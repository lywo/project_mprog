package com.example.lydia.savethenight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class newsActivity extends AppCompatActivity {
    ArrayList<NewsItem> currentNewsItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        new newsAsyncTask(newsActivity.this).execute();

    }

    protected void setData(ArrayList<NewsItem> currentNews){

    }
}
