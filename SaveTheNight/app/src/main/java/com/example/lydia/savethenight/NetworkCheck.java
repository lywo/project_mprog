package com.example.lydia.savethenight;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by Lydia on 16-6-2016.
 */
public class NetworkCheck {
    static ConnectivityManager cm;

    /*
    Check is there is any internet connection WiFi or mobile data
     */
    public static boolean isNetworkAvailable(Context context) {
       cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /*
    Check if internet connection is a WiFi connection
     */
    public static boolean isWifi() {
        return cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();
    }

    /*
    Check WiFi connection on correct authentication
     */
    public static boolean isAuthentication (Context context){
        boolean isAuth = false;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        SupplicantState supState = wifiInfo.getSupplicantState();
        if (supState == SupplicantState.COMPLETED){
            isAuth = true;
        }
        return isAuth;
    }

}
