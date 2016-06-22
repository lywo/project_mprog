/*
Lydia Wolfs
SettingsActivity
From MainActivity when settings icon is clicked
 */
package com.example.lydia.savethenight;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

/*
Gets user input and saves text message and selected contact in SQLitedatabase
 */
public class SettingsActivity extends AppCompatActivity {
    DBhelper settingsHelper;
    static final int PICK_CONTACT=1;
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Fill Contact name TextView with information from the database
        settingsHelper = new DBhelper(this);
        TextView contactNameTV = (TextView) findViewById(R.id.contactTV);
        String contactName = settingsHelper.getName();
        String savedSMS = settingsHelper.getSMS();

        // A contact is already saved in the database
        if(contactName.length() != 0){
            assert contactNameTV != null;
            contactNameTV.setText(getString(R.string.savedcontact) + contactName);
        }

        // There is no contact saved in database
        else{
            assert contactNameTV != null;
            contactNameTV.setText(getString(R.string.nosavedcontact) + contactName);
        }

        // Fill TextView with saved sms is there is already a sms saved in the database
        TextView savedSMSText = (TextView) findViewById(R.id.savedSMSTV);
        if(savedSMS.length() != 0){
            assert savedSMSText != null;
            savedSMSText.setText(getString(R.string.savedsms) + savedSMS);
        }

        // Set save button standard on false, so it is not possible to save empty sms message
        EditText textSMS = (EditText) findViewById(R.id.smsET);
        final Button saveSMSBT = (Button) findViewById(R.id.saveSMSBT);
        assert saveSMSBT != null;
        saveSMSBT.setEnabled(false);

        // Hide keyboard for a cleaner screen
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Fill editText with saved text and set cursor to end when screen was left before saving
        assert textSMS != null;
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        textSMS.setText(prefs.getString("autoSave", ""));
        textSMS.setSelection(textSMS.getText().length());

        textSMS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            // When text in EditText is changed, saving sms is possible and button is enabled
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    saveSMSBT.setEnabled(false);
                } else {
                    saveSMSBT.setEnabled(true);
                }
            }

            // Save typed text in EditText so user can continue typing sms after leaving Activity
            @Override
            public void afterTextChanged(Editable s) {
                prefs.edit().putString("autoSave", s.toString()).apply();
            }
        });
    }

    /*
    Redirects user to the contacts application of the phone
    Returns to SaveTheNight app when user has selected a contact
     */
    protected void goToContacts(View view){

        // User did not gave permission to read contacts
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED){

            // Permission was asked before and 'never show again' box is not checked
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Request user to allow permission again
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_CONTACTS},MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }

            // Permission was not asked before or 'never asked again' box was checked
            else {

                // Request permission when first time asking
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_CONTACTS},MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }

        // User gave permission to read contacts
        else{

            // Redirect to contacts application to pick a contact
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        }
    }

    /*
    Get user input and save the sms message in the database
     */
    protected void saveSMS(View view){

        // Get user input as a String
        EditText smsET = (EditText) findViewById(R.id.smsET);
        assert smsET != null;
        String textSMS = smsET.getText().toString();

        // Save sms in database
        settingsHelper = new DBhelper(this);
        settingsHelper.saveSMS(textSMS);

        // Clear EditText
        smsET.setText("");

        // Show new sms in TextView
        String savedSMS = settingsHelper.getSMS();
        TextView savedSMSText = (TextView) findViewById(R.id.savedSMSTV);
        if(savedSMS.length() != 0){
            assert savedSMSText != null;
            savedSMSText.setText(getString(R.string.savedsms)+ savedSMS);
        }

        // Hide keyboard when sms is saved
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    /*
    Check is everything is oki and start collect data from selected contact
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == PICK_CONTACT)
        {
            if (resultCode == RESULT_OK){ // user picked a contact
                getContactInfo(intent);
            }
            else{
                Toast.makeText(this, getString(R.string.nocontact), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
    Get the data of interest from the selected contact
     */
    protected void getContactInfo(Intent intent)
    {
        Cursor cursor =  managedQuery(intent.getData(), null, null, null, null);
        DBhelper contactDBhelper = new DBhelper(this);
        String name = null;
        String phoneNumber = null;

        // Read over result from intent when user clicked on a contact in the contact app
        while (cursor.moveToNext())
        {
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
            String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            // Check is phone number is filled in contact information
            if ( hasPhone.equalsIgnoreCase("1"))
                hasPhone = "true";
            else
                hasPhone = "false" ;

            // If phone number(s) exist
            if (Boolean.parseBoolean(hasPhone))
            {

                // Read over saved phone numbers
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,null, null);
                while (phones.moveToNext())
                {

                    // Save phone number as a String
                    phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phones.close();
            }
        }

        // Update TextView with name from selected contact
        TextView contactNameTV = (TextView) findViewById(R.id.contactTV);
        assert contactNameTV != null;
        contactNameTV.setText("Saved contact is " + name);

        // Save the name and phone number from selected contact
        contactDBhelper.saveContact(name, phoneNumber);
        cursor.close();
    }
}
