/*
Lydia Wolfs
PhoneActivity
Called from MainActivity when phone icon is clicked
 */
package com.example.lydia.savethenight;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/*
Listen to Ringtone settings of user to depend volume
2 ways to stop ringtone or vibration, on back pressed and icon clicked
Stop via function which is called twice
 */
public class PhoneActivity extends AppCompatActivity {
    MediaPlayer mp;
    Vibrator v;
    AudioManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        // Check phone settings ringtone volume to adapt ringtone to ringtone, vibration or silent
        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                v= (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

                // vibrate for maximum 25 seconds
                v.vibrate(25000);
                break;
            case AudioManager.RINGER_MODE_NORMAL:

                // play ringtone
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                mp = MediaPlayer.create(getApplicationContext(), notification);

                // set end time
                mp.start();
                break;
        }
    }

    /*
    Phone icon is clicked, stop vibration or ringtone and return to MainActivity
     */
    protected void pickUpPhone(View view){
        stopRingtone();
    }

    /*
    Back Button is pressed, stop vibration or ringtone and return to MainActivity
     */
    public void onBackPressed(){
        stopRingtone();
    }

    /*
    Check the Sound settings and stop if needed sounds/ vibration
     */
    protected void stopRingtone(){

        // Set Boolean fake call initialized to false so a new fake call can be initialized in MainActivity
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("init", false).apply();

        // Check ringTone volume settings from phone to adapt RingTone stop
        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                v.cancel();
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                mp.stop();
                break;
        }
        finish();
    }
}
