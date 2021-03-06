package com.example.lydia.savethenight;
/**
 * Created by Lydia on 6-6-2016.
 * NewsAdapter.java is adapter to handle ArrayList of NewItems
 */

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/*
Class gets ArrayList and Fill ListView as assigned
 */
public class NewsAdapter extends ArrayAdapter {
    public NewsAdapter(Context context, ArrayList<NewsItem> currentNews) {
        super(context, 0, currentNews);
    }

    /*
    Get View, fill View with data and return filled View
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.row_layout_news, parent, false);

        // get newsData and TextViews
        NewsItem currentNewsItem= (NewsItem) getItem(position);
        TextView newsTitleTV = (TextView) view.findViewById(R.id.newsTitleTV);
        TextView newsDescriptionTV = (TextView) view.findViewById(R.id.newsDescriptionTV);

        // split description for clean information
        String currentDescription = currentNewsItem.description;
        String[] separatedDescription = currentDescription.split("<");

        // fill TextViews
        newsTitleTV.setText(Html.fromHtml("<a href=\""+ currentNewsItem.link + "\">" + currentNewsItem.title + "</a>"));
        newsDescriptionTV.setText(separatedDescription[0]);

        // make title clickable to link
        newsTitleTV.setClickable(true);
        newsTitleTV.setMovementMethod (LinkMovementMethod.getInstance());

        return view;
    }
}
