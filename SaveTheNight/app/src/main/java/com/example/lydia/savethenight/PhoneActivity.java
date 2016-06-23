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
import android.os.Handler;
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
        final Handler handler = new Handler();

        // Check phone settings ringtone volume to adapt ringtone to ringtone, vibration or silent
        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                break;
            case AudioManager.RINGER_MODE_VIBRATE: // telephone has vibration for notifications on
                v= (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

                // Initiate vibration forever
                long[] pattern = {0, 1000, 1000};
                v.vibrate(pattern, 0);
                break;
            case AudioManager.RINGER_MODE_NORMAL: // Telephone has sound ringtone on

                // Play ringtone
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                mp = MediaPlayer.create(getApplicationContext(), notification);

                // Start
                mp.start();
                break;
        }

        // If user does not stop the call, end after 25 seconds
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Wait 25 seconds and end the activity, close the calling screen
                stopRingtone();
            }
        }, 25000);
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
