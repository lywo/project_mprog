package com.example.lydia.savethenight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lydia on 8-6-2016.
 */
public class QuestionAdapter extends ArrayAdapter {
    public QuestionAdapter(Context context, ArrayList<String> favouriteQuestions) {
        super(context, 0, favouriteQuestions);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.row_layout_questions, parent, false);

        // load questions and fill list with strings
        String currentQuestion= (String) getItem(position);
        TextView questionText = (TextView) view.findViewById(R.id.questionRowTV);
        questionText.setText(currentQuestion);

        return view;
    }

    @Override
    public void notifyDataSetChanged() // Create this function in your adapter class
    {
        super.notifyDataSetChanged();
    }
}
