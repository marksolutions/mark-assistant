package com.example.mark.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.example.mark.services.MyService;

public class Utility {

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void startService(Context context) {
        context.startService(new Intent(context, MyService.class));
    }

    public static void stopService(Context context) {
        context.stopService(new Intent(context, MyService.class));
    }
}
