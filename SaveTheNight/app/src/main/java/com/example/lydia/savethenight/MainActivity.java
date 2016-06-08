package com.example.lydia.savethenight;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Question> questionObjects = new ArrayList<>();
    Resources res = getResources();
    String[] questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DBhelper myDBhelper = new DBhelper(this);

        // if empty, put questions in questionobjects
        if(questions.length == 0){
            questions = res.getStringArray(R.array.questions);
            for (int i = 0, n = questions.length; i < n; i++){
                Question newQuestion = new Question(questions[i], i + 1, false);
                questionObjects.add(newQuestion);
            }
        }
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

        String smsMessage = null;
        String phoneNumber = null;

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, smsMessage, null, null);
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
