package com.febino.aquafish;

import android.util.Log;

public class TraderDetails {
    private int _id;
    private String createDate;
    private String name;
    private String alias;
    private String location;
    private String phone;

    public void printLog() {
        Log.i("Id", "" + _id);
        Log.i("Create Date", createDate);
        Log.i("Name", name);
        Log.i("Alias", alias);
        Log.i("Location", location);
        Log.i("Phone", phone);

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
