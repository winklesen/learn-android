package com.samuelbernard147.smslistenerapp;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class PermissionManager {
    public static void check(Activity activity, String permission, int requestCode){
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[]{permission},requestCode);
        }
    }
}
