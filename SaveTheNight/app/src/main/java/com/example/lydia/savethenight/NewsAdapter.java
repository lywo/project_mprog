package com.example.lydia.savethenight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Lydia on 6-6-2016.
 */
public class NewsAdapter extends ArrayAdapter {
    public NewsAdapter(Context context, ArrayList<NewsItem> currentNews) {
        super(context, 0, currentNews);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.row_layout_news, parent, false);

        // get WeatherData and content strings


        return view;
    }
}
