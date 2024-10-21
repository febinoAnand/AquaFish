package com.febino.dependencies;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import com.febino.dataclass.BillDetails;

import androidx.core.app.ActivityCompat;

public class ProjectUtils {

    public static final int STORAGE_PERMISSION = 1;

    public static String[] STORAGE_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    public static boolean hasPermission(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }

        return true;
    }
}
