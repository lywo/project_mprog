package com.example.lydia.savethenight;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    DBhelper settingsHelper;
    static final int PICK_CONTACT=1;
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsHelper = new DBhelper(this);
        TextView contactNameTV = (TextView) findViewById(R.id.contactTV);
        String contactName = settingsHelper.getName();
        String savedSMS = settingsHelper.getSMS();
        if(contactName.length() != 0){
            assert contactNameTV != null;
            contactNameTV.setText("Saved contact is " + contactName);
        }
        else{
            assert contactNameTV != null;
            contactNameTV.setText("No saved contact " + contactName);
        }
        TextView savedSMSText = (TextView) findViewById(R.id.savedSMSTV);
        if(savedSMS.length() != 0){
            assert savedSMSText != null;
            savedSMSText.setText("Your saved sms: " + savedSMS);
        }

        EditText textSMS = (EditText) findViewById(R.id.smsET);
        final Button saveSMSBT = (Button) findViewById(R.id.saveSMSBT);
        assert saveSMSBT != null;
        saveSMSBT.setEnabled(false);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // fill editText with saved text and set cursor to end
        assert textSMS != null;
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        textSMS.setText(prefs.getString("autoSave", ""));
        textSMS.setSelection(textSMS.getText().length());


        textSMS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    saveSMSBT.setEnabled(false);
                } else {
                    saveSMSBT.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.edit().putString("autoSave", s.toString()).apply();
            }
        });
    }

    protected void goToContacts(View view){
        // permission check
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_CONTACTS},MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                // request permission
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_CONTACTS},MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
        else{
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Do the contacts-related task you need to do.
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);

                } else { // request array = 0 when request is denied
                   // TODO Disable the functionality that depends on this permission.
                }
                return;
            }
        }
    }

    protected void saveSMS(View view){
        EditText smsET = (EditText) findViewById(R.id.smsET);
        String textSMS = smsET.getText().toString();
        settingsHelper = new DBhelper(this);
        settingsHelper.saveSMS(textSMS);
        smsET.setText("");

        // Show new sms in TextView
        String savedSMS = settingsHelper.getSMS();
        TextView savedSMSText = (TextView) findViewById(R.id.savedSMSTV);
        if(savedSMS.length() != 0){
            savedSMSText.setText("Your saved sms: " + savedSMS);
        }

        // hide keyboard when sms is saved
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == PICK_CONTACT)
        {
            getContactInfo(intent);
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

        TextView contactNameTV = (TextView) findViewById(R.id.contactTV);
        contactNameTV.setText("Saved contact is " + name);
        contactDBhelper.saveContact(name, phoneNumber);
        cursor.close();
    }
}
