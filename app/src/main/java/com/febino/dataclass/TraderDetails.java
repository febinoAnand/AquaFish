package com.febino.dataclass;

import android.util.Log;

import com.febino.aquafish.MainActivity;

public class TraderDetails {

    private String SUB_TAG = "TraderDetails";
    public long _id;
    public String name;
    public String alias;
    public String location;
    public String mobile;
    public String trader_id;
    public String created_date;

    public void logValues(){
        Log.i(MainActivity.TAG_NAME + "-" + SUB_TAG, "ID=" + _id);
        Log.i(MainActivity.TAG_NAME + "-" + SUB_TAG, "Name=" + name);
        Log.i(MainActivity.TAG_NAME + "-" + SUB_TAG, "alias=" + alias);
        Log.i(MainActivity.TAG_NAME + "-" + SUB_TAG, "location=" + location);
        Log.i(MainActivity.TAG_NAME + "-" + SUB_TAG, "mobile=" + mobile);
        Log.i(MainActivity.TAG_NAME + "-" + SUB_TAG, "trader_id=" + trader_id);
        Log.i(MainActivity.TAG_NAME + "-" + SUB_TAG, "created_date=" + created_date);
    }
}
