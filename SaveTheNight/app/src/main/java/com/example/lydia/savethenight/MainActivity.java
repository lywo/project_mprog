package com.example.lydia.savethenight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Question> Questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DBhelper myDBhelper = new DBhelper(this);
    }

    protected void phoneClicked(){
        Intent phoneIntent = new Intent(this, phoneActivity.class);
        startActivity(phoneIntent);
    }

    protected void newsClicked(){
        Intent newsIntent = new Intent(this, newsActivity.class);
        startActivity(newsIntent);
    }

    protected void smsClicked(){
        Intent smsIntent= new Intent(this, smsActivity.class);
        startActivity(smsIntent);
    }

    protected void questionClicked(){
        Intent questionIntent = new Intent(this, questionActivity.class);
        startActivity(questionIntent);
    }

    protected void settingsClicked(){
        Intent settingsIntent = new Intent(this, settingsActivity.class);
        startActivity(settingsIntent);
    }

}
