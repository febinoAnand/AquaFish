package com.febino.dependencies;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import com.febino.dataclass.BillDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static boolean isDateRangeValid(String fromDateStr, String toDateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date fromDate = dateFormat.parse(fromDateStr);
            Date toDate = dateFormat.parse(toDateStr);

            return fromDate.compareTo(toDate) <= 0; // Returns true if valid range, false otherwise
        } catch (ParseException e) {
            System.out.println("Error parsing dates: " + e.getMessage());
            return false; // Return false if there is a parsing error
        }
    }

    public static String convertDateFormatToNormal(String dateStr) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");


        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {

            Date date = inputFormat.parse(dateStr);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;  // Return null if parsing fails
        }
    }
}
