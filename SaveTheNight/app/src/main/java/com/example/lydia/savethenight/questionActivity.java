package com.example.lydia.savethenight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class questionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
    }

    protected void addQuestion(){
        ListView favouriteQuestions = (ListView) findViewById(R.id.favouriteQuestionsLV);

    }
}
