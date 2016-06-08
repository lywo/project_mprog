package com.example.lydia.savethenight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        // get newsData and content strings
        NewsItem currentNewsItem= (NewsItem) getItem(position);
        TextView newsTitleTV = (TextView) view.findViewById(R.id.newsTitleTV);
        TextView newsLinkTV = (TextView) view.findViewById(R.id.newsLinkTV);
        TextView newsDescriptionTV = (TextView) view.findViewById(R.id.newsDescriptionTV);

        newsTitleTV.setText(currentNewsItem.title);
        newsLinkTV.setText(currentNewsItem.link);
        newsDescriptionTV.setText(currentNewsItem.description);

        return view;
    }
}
