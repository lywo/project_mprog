package com.example.lydia.savethenight;

/*
Lydia Wolfs
MainActivity.java
Activity for main/first screen
 */

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.telephony.TelephonyManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> questions;
    final int MY_PERMISSIONS_REQUEST_SEND_SMS = 2;
    private boolean initialized;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    check if call is already initialized
    starts after 10 sec delay phoneActivity , via intent
     */
    protected void phoneClicked(View view){
        // Check if call is already initialized from boolean in shared preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        initialized = prefs.getBoolean("init", false);

        // Call is already initialized
        if (initialized){
            Toast.makeText(this, "Call already initialized", Toast.LENGTH_SHORT).show();
        }

        // Call is not yet initialized
        else {
            // Change boolean init to true and update user
            editor.putBoolean("init", true).apply();
            Toast.makeText(this, "Fake call is initialized", Toast.LENGTH_SHORT).show();

            // Do not allow screen to sleep
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            // After delay, go to next PhoneActivity
            final Intent phoneIntent = new Intent(this, PhoneActivity.class);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // wait half a minute, start fake calling.
                    startActivity(phoneIntent);
                }
            }, 10000);
        }
    }

    /*
    If correct connection, go to NewsActivity and load news
     */
    protected void newsClicked(View view){
        if(NetworkCheck.isNetworkAvailable(this)){
            // Check extra Wifi connections
            if(NetworkCheck.isWifi()){
                // Check is correct wifi connection
                if(!NetworkCheck.isAuthentication(this)){
                    // Message to user no wifi authentication
                    Toast.makeText(this, "No WiFi authentication", Toast.LENGTH_SHORT).show();
                }
                // Start NewsActivity
                else{
                    Intent newsIntent = new Intent(this, NewsActivity.class);
                    startActivity(newsIntent);
                }
            }
            // Start NewsActivity
            else {
                Intent newsIntent = new Intent(this, NewsActivity.class);
                startActivity(newsIntent);
            }
        }
        else{
            // Message to user no internet connection
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    Check if authorized to send sms
    Check is contact selected and sms typed
    Load sms and contact from database, send sms and check response data
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void SMSClicked(View view) {
        // Permission to send sms is not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                Log.d("in de if ", " nu in de if");
                ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);

            }
            // First  time  permission check of never ask again
            else {
                Log.d("in de else ", " nu in de else");
                // Request permission
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);

                // Else //  never ask again clicked
                // Show Dialog with warning for user
                showDialogOK("Permission to send sms is needed, please allow", "Permission Error",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            }
        }
        // Permission to send sms is granted
        else {
            // Check is contact selected and sms typed
            final DBhelper settingsDBhelper = new DBhelper(this);
            if (settingsDBhelper.getName().length() == 0 || settingsDBhelper.getNumber().length() == 0){
                showDialogOK("Select a contact from contact list to send SMS", "Settings Error",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
            else if (settingsDBhelper.getSMS().length() == 0 ){
                showDialogOK("No sms text was found, please type your sms message", "Settings Error",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
            else{
                // Get sms message and phonenumber from database
                String smsMessage = settingsDBhelper.getSMS();
                String phoneNumber = settingsDBhelper.getNumber();
                String SENT = "SMS_SENT";
                String DELIVERED = "SMS_DELIVERED";

                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                // Check device is able to send sms
                if(telephonyManager.isSmsCapable()){
                    // Check connection
                    PendingIntent sendingPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
                    PendingIntent deliveryPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

                    // Send sms
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, smsMessage, sendingPendingIntent, deliveryPendingIntent);

                    // Check response code
                    if ( sendingPendingIntent.getCreatorUid() == RESULT_OK) { // response is OK
                        Toast.makeText(this, "SMS is send", Toast.LENGTH_SHORT).show();
                        // Check if SMS is delivered
                        if (deliveryPendingIntent== null){
                            Toast.makeText(this, "SMS delivery failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else{ // SMS could not be send (No connection)
                        Toast.makeText(this, "SMS failed, please check your connection and try again later.",
                            Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    /*
    Load hardcoded questions
    Give questions to next QuestionActivity
     */
    protected void questionClicked(View view){
        Bundle questionBundle=new Bundle();
        DBhelper questionHelper = new DBhelper(this);
        questions = questionHelper.loadQuestions();

        // Load questions from XML in Bundle
        questionBundle.putStringArrayList("questionsArrayList", questions);

        // Prepare for QuestionActivity
        Intent questionIntent = new Intent(this, QuestionActivity.class);
        questionIntent.putExtras(questionBundle);
        startActivity(questionIntent);
    }

    /*
    Start next SettingsActivity
     */
    protected void settingsClicked(View view){
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    /*
    Shows Dialog with ok button, input message and input title
     */
    private void showDialogOK(String message, String title, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder AlertContact = new AlertDialog.Builder(this);
        AlertContact.setMessage(message);
        AlertContact.setTitle(title);
        AlertContact.setPositiveButton("OK", okListener);
        AlertContact.setCancelable(true);
        AlertContact.create().show();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
