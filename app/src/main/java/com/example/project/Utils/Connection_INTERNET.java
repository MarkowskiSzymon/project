package com.example.project.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.provider.Settings.Secure;

public class Connection_INTERNET {

    private static String androidId = null;
    private static boolean state = false;

    public Connection_INTERNET(Context context)
    {
        androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        ConnectivityManager  cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        state = (cm.getActiveNetworkInfo()!=null) && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    public boolean isInternetAvailable() {
        return state;
    }
    public String getDeviceId(){
        return androidId;
    }
}