package com.example.lydia.savethenight;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PhoneActivity extends AppCompatActivity {
    MediaPlayer mp;
    Vibrator v;
    AudioManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        switch (am.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                v= (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate infinite
                long[] pattern = {0, 100, 1000};
                v.vibrate(pattern, 0);
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                // play ringtone
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                mp = MediaPlayer.create(getApplicationContext(), notification);
                mp.start();
                break;
        }
    }

    protected void pickUpPhone(View view){
        stopRingtone();
    }

    public void onBackPressed(){
        stopRingtone();
    }

    /*
    Check the
     */
    protected void stopRingtone(){
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
