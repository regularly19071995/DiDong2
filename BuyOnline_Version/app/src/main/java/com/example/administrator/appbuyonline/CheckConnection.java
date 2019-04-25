package com.example.administrator.appbuyonline;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckConnection {
   public static boolean haveNetworkConnection(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static void showToast_Short(Context context, String thongbao){
        Toast.makeText(context, thongbao, Toast.LENGTH_SHORT).show();
    }
}
