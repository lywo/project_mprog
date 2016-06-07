package com.example.lydia.savethenight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Question> Questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DBhelper myDBhelper = new DBhelper(this);
    }

    protected void phoneClicked(View view){
        Intent phoneIntent = new Intent(this, PhoneActivity.class);
        startActivity(phoneIntent);
    }

    protected void newsClicked(View view){
        Intent newsIntent = new Intent(this, newsActivity.class);
        startActivity(newsIntent);
    }

    protected void smsClicked(View view){
        Intent smsIntent= new Intent(this, SmsActivity.class);
        startActivity(smsIntent);
    }

    protected void questionClicked(View view){
        Intent questionIntent = new Intent(this, QuestionActivity.class);
        startActivity(questionIntent);
    }

    protected void settingsClicked(View view){
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
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
