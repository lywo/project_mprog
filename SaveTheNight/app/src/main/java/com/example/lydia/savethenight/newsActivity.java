package com.example.lydia.savethenight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class newsActivity extends AppCompatActivity {
    private static final String URL = "http://feeds.nos.nl/nosjournaal";
    NewsAdapter myAdapter; //= new NewsAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        DownloadNewsAsyncTask myAsyncTask = new DownloadNewsAsyncTask(this);
        myAsyncTask.execute(URL);
    }

    protected void setData(ArrayList<NewsItem> currentNews){
        ListView newsLV = (ListView) findViewById(R.id.newsLV);
        myAdapter  = new NewsAdapter(this, currentNews);
        newsLV.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    public void onResume()
    {
        super.onResume();


    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        if (savedInstanceState != null) {
//            weather = (ArrayList<WeatherData>) savedInstanceState.getSerializable("d");
//            WeatherAdapter adapter = new WeatherAdapter(this, weather);
//            ListView listView = (ListView) findViewById(R.id.weatherDataLV);
//            assert listView != null;
//            listView.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putSerializable("d", weather);
    }
}
