package com.example.lydia.savethenight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.provider.ContactsContract;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    protected void saveSMS(View view){
        // Button saveSMSButton = (Button)  findViewById(R.id.saveSMSBT);
        EditText smsET = (EditText) findViewById(R.id.smsET);
        String textSMS = smsET.getText().toString();
    }

}
