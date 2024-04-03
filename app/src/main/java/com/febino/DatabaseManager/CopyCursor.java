package com.febino.DatabaseManager;

import android.database.Cursor;
import android.util.Log;

import com.febino.aquafish.MainActivity;
import com.febino.dataclass.ProductDetails;
import com.febino.dataclass.TraderDetails;

import java.util.ArrayList;

public class CopyCursor {

    private static final String SUB_TAG_NAME = "Copy-cursor";
    public TraderDetails copyTraderFromCursor(Cursor c){
        TraderDetails traderDetails = new TraderDetails();
        traderDetails._id = c.getLong(c.getColumnIndex(DataBaseManager.TRADER_REG_ROW_ID));
        traderDetails.name = c.getString(c.getColumnIndex(DataBaseManager.TRADER_REG_ROW_NAME));
        traderDetails.alias = c.getString(c.getColumnIndex(DataBaseManager.TRADER_REG_ROW_ALIAS));
        traderDetails.mobile = c.getString(c.getColumnIndex(DataBaseManager.TRADER_REG_ROW_MOBILE_NO));
        traderDetails.location = c.getString(c.getColumnIndex(DataBaseManager.TRADER_REG_ROW_LOCATION));
        traderDetails.trader_id = c.getString(c.getColumnIndex(DataBaseManager.TRADER_REG_ROW_TRADER_ID));
        traderDetails.created_date = c.getString(c.getColumnIndex(DataBaseManager.TRADER_REG_ROW_CREATE_DATE));
        return traderDetails;
    }

    public ArrayList<TraderDetails> copyTraderListFromCurser(Cursor c){
        ArrayList<TraderDetails> traderDetailsArrayList = new ArrayList<TraderDetails>();
        if(c.getCount() > 0) {
            while (c.moveToNext()) {
                traderDetailsArrayList.add(copyTraderFromCursor(c));
            }
        }
        return traderDetailsArrayList;
    }


    public ProductDetails copyProductFromCursor(Cursor c){
        ProductDetails productDetails = new ProductDetails();
        productDetails._id = c.getLong(c.getColumnIndex(DataBaseManager.PRODUCT_ROW_ID));
        productDetails.productName = c.getString(c.getColumnIndex(DataBaseManager.PRODUCT_ROW_NAME));
        productDetails.shortName = c.getString(c.getColumnIndex(DataBaseManager.PRODUCT_ROW_SHORT_NAME));
        productDetails.description = c.getString(c.getColumnIndex(DataBaseManager.PRODUCT_ROW_DESCRIPTION));
        productDetails.created_date = c.getString(c.getColumnIndex(DataBaseManager.PRODUCT_ROW_CREATE_DATE));
        return productDetails;
    }

    public ArrayList<ProductDetails> copyProductListFromCurser(Cursor c){
        ArrayList<ProductDetails> productDetailsArrayList = new ArrayList<ProductDetails>();
        if(c.getCount() > 0) {
            while (c.moveToNext()) {
                productDetailsArrayList.add(copyProductFromCursor(c));
            }
        }
        return productDetailsArrayList;
    }

    public void showCursorData(Cursor c){

        log("DB values:-");
        while (c.moveToNext()){
            log("------------------------------------------------------");
            for(int i = 0 ;i<c.getColumnCount();i++)
                log(c.getColumnName(i)+" = "+c.getString(i));

        }
    }

    private void log(String message) {
        Log.i(MainActivity.TAG_NAME+"-"+this.SUB_TAG_NAME,message);
    }
}
