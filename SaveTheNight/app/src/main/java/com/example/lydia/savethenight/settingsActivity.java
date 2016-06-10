package com.example.lydia.savethenight;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.provider.ContactsContract;
import android.widget.TextView;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    DBhelper settingsHelper;
    static final int PICK_CONTACT=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsHelper = new DBhelper(this);
        TextView contactNameTV = (TextView) findViewById(R.id.contactTV);
        String contactName = settingsHelper.getName();
        contactNameTV.setText("Saved contact = " + contactName);
    }

    protected void goToContacts(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    protected void saveSMS(View view){
        EditText smsET = (EditText) findViewById(R.id.smsET);
        String textSMS = smsET.getText().toString();
        settingsHelper = new DBhelper(this);
        settingsHelper.saveSMS(textSMS);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {

        if (requestCode == PICK_CONTACT)
        {
            getContactInfo(intent);
            // Your class variables now have the data, so do something with it.
        }
    }

    protected void getContactInfo(Intent intent)
    {
        Cursor cursor =  managedQuery(intent.getData(), null, null, null, null);
        DBhelper contactDBhelper = new DBhelper(this);
        String name = null;
        String phoneNumber = null;
        while (cursor.moveToNext())
        {
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

            String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if ( hasPhone.equalsIgnoreCase("1"))
                hasPhone = "true";
            else
                hasPhone = "false" ;

            if (Boolean.parseBoolean(hasPhone))
            {
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,null, null);
                while (phones.moveToNext())
                {
                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phones.close();
            }
        }
        contactDBhelper.saveContact(name, phoneNumber);
        cursor.close();
    }
}
