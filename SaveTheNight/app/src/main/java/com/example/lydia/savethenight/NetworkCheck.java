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
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /*
    Check WiFi connection on correct authentication
     */
    public static boolean isAuthentication (Context context){
        boolean isAuth = true;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        SupplicantState supState = wifiInfo.getSupplicantState();
        if (supState != SupplicantState.COMPLETED){
            isAuth = false;
        }
        return isAuth;
    }
}
