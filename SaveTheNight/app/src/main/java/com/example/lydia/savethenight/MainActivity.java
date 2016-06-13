package com.example.lydia.savethenight;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> questions;
    final int MY_PERMISSIONS_REQUEST_SEND_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void phoneClicked(View view){
        final Intent phoneIntent = new Intent(this, PhoneActivity.class);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        final Handler handler = new Handler();
        Toast.makeText(this, "Fake call is initialized",Toast.LENGTH_SHORT).show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // wait half a minute, start fake calling.
                startActivity(phoneIntent);
            }
        }, 10000);
    }

    protected void newsClicked(View view){
        Intent newsIntent = new Intent(this, newsActivity.class);
        startActivity(newsIntent);
    }

    protected void SMSClicked(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                AlertDialog.Builder AlertContact = new AlertDialog.Builder(this);
                AlertContact.setMessage("Permission to send SMS is required to send SMS");
                AlertContact.setTitle("Save the night");
                AlertContact.setPositiveButton("OK", null);
                AlertContact.setCancelable(true);
                AlertContact.create().show();
                AlertContact.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            } else {
                // request permission
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else{
            // get sms message and phonenumber from database
            DBhelper settingsDBhelper = new DBhelper(this);
            String smsMessage = settingsDBhelper.getSMS();
            String phoneNumber = settingsDBhelper.getNumber();

            // send sms
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, smsMessage, null, null);
            Toast.makeText(this, "SMS is send",Toast.LENGTH_SHORT).show();
        }
    }

    protected void questionClicked(View view){
        Bundle b=new Bundle();
        DBhelper questionHelper = new DBhelper(this);
        questions = questionHelper.loadQuestions();

        b.putStringArrayList("questionsArrayList", questions);
        Intent questionIntent = new Intent(this, QuestionActivity.class);
        questionIntent.putExtras(b);
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
