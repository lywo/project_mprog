/*
Lydia Wolfs
NewsActivity
Reached from MainActivity via intent when news icon is clicked
 */
package com.example.lydia.savethenight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

/*
Called from MainActivity
Activity calls AsyncTask and ListView is filled with news
 */
public class NewsActivity extends AppCompatActivity {
    NewsAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Call NewsAsynTask to get News Feed
        NewsAsyncTask newsFeedHandler = new NewsAsyncTask(NewsActivity.this);
        newsFeedHandler.execute();
    }

    /*
    Called from NewsAsyncTask
    Set NewsItems in ListView
     */
    protected void setData(ArrayList<NewsItem> currentNews){
        ListView newsLV = (ListView) findViewById(R.id.newsLV);
        myAdapter  = new NewsAdapter(this, currentNews);
        if (currentNews!=null) {
            assert newsLV != null;
            newsLV.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
        }
    }

    /*
    When back button pressed finish activity to reload news every time
     */
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        this.finish();
    }
}
