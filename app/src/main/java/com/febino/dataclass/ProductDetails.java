package com.febino.dataclass;

import android.util.Log;

import com.febino.aquafish.MainActivity;

public class ProductDetails {
    private String SUB_TAG = "ProductDetails";
    public long _id;
    public String productName;
    public String shortName;
    public String description;
    public String created_date;

    public void logValues(){
        Log.i(MainActivity.TAG_NAME + "-" + SUB_TAG, "ID=" + _id);
        Log.i(MainActivity.TAG_NAME + "-" + SUB_TAG, "Name=" + productName);
        Log.i(MainActivity.TAG_NAME + "-" + SUB_TAG, "Short Name=" + shortName);
        Log.i(MainActivity.TAG_NAME + "-" + SUB_TAG, "Description=" + description);
        Log.i(MainActivity.TAG_NAME + "-" + SUB_TAG, "created_date=" + created_date);
    }
}
