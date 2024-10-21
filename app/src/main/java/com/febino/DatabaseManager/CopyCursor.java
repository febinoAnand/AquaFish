package com.febino.DatabaseManager;

import android.database.Cursor;
import android.util.Log;

import com.febino.aquafish.MainActivity;
import com.febino.dataclass.BillDetails;
import com.febino.dataclass.OrderDetails;
import com.febino.dataclass.ProductDetails;
import com.febino.dataclass.TraderDetails;

import java.lang.reflect.Array;
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

    public OrderDetails copyOrderFromCursor(Cursor c){
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.set_id(c.getLong(c.getColumnIndex(DataBaseManager.ORDER_ROW_ID)));
        orderDetails.setTraderID(c.getLong(c.getColumnIndex(DataBaseManager.ORDER_TRADER_ID)));
        orderDetails.setProductID(c.getLong(c.getColumnIndex(DataBaseManager.ORDER_PRODUCT_ID)));
        orderDetails.setOrderDate(c.getString(c.getColumnIndex(DataBaseManager.ORDER_DATE)));
        orderDetails.setTotalKG(c.getFloat(c.getColumnIndex(DataBaseManager.ORDER_KG)));
        orderDetails.setRatePerKG(c.getFloat(c.getColumnIndex(DataBaseManager.ORDER_RATE)));
        orderDetails.setTotalBox(c.getInt(c.getColumnIndex(DataBaseManager.ORDER_BOX)));
        orderDetails.setCreateDateTime(c.getString(c.getColumnIndex(DataBaseManager.ORDER_CREATE_DATE_TIME)));
        orderDetails.setUpdateDateTime(c.getString(c.getColumnIndex(DataBaseManager.ORDER_UPDATE_DATE_TIME)));

//        update at version 2

        int boolInt = c.getInt(c.getColumnIndex(DataBaseManager.ORDER_IS_BILLED));


        orderDetails.setBilled(boolInt == 1 ? true : false);

//        float kgPerBox = 35.0f;
//        try{
//            kgPerBox = c.getFloat(c.getColumnIndex(DataBaseManager.ORDER_KG_PER_BOX));
//            if(kgPerBox==0.0f) kgPerBox = 35.0f;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        orderDetails.setKgPerBox(kgPerBox);

        orderDetails.setKgPerBox(c.getFloat(c.getColumnIndex(DataBaseManager.ORDER_KG_PER_BOX)));
        orderDetails.setBillID(c.getLong(c.getColumnIndex(DataBaseManager.ORDER_BILL_ID)));

        return orderDetails;

    }

    public ArrayList<OrderDetails> copyOrderListFromCursor(Cursor c){
        ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<OrderDetails>();
        if(c.getCount() > 0) {
            while (c.moveToNext()) {
                orderDetailsArrayList.add(copyOrderFromCursor(c));
            }
        }
        return orderDetailsArrayList;
    }

    public ArrayList<BillDetails> copyBillListFromCursor(Cursor c){
        ArrayList<BillDetails> billDetailsArrayList = new ArrayList<BillDetails>();
        if(c.getCount() > 0) {
            while (c.moveToNext()) {
                billDetailsArrayList.add(copyBillFromCursor(c));
            }
        }
        return billDetailsArrayList;
    }

    public ArrayList<ArrayList<OrderDetails>> copyArrayOfOrderListFromCursor(Cursor cursor, int traderCount, int productCount) {
        ArrayList<ArrayList<OrderDetails>> orderDetailsArrayListArray = new ArrayList<ArrayList<OrderDetails>>(traderCount);
//        Log.i("Order Count", ""+cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            OrderDetails orderDetails = copyOrderFromCursor(cursor);
            long lastTraderID = orderDetails.getTraderID();
            ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<OrderDetails>();
            do{
                orderDetails = copyOrderFromCursor(cursor);
                if(lastTraderID != orderDetails.getTraderID()){
                    orderDetailsArrayListArray.add(orderDetailsArrayList);
                    orderDetailsArrayList = new ArrayList<OrderDetails>();
                }
                orderDetailsArrayList.add(orderDetails);
                lastTraderID = orderDetails.getTraderID();
            }while (cursor.moveToNext());
            orderDetailsArrayListArray.add(orderDetailsArrayList);
        }
        return orderDetailsArrayListArray;
    }

    public ArrayList<ArrayList<OrderDetails>> copyArrayOfOrderListFromCursor(Cursor cursor, ArrayList<TraderDetails> traderDetailsArrayList, ArrayList<ProductDetails> productDetailsArrayList) {
        ArrayList<ArrayList<OrderDetails>> orderDetailsArrayListArray = new ArrayList<ArrayList<OrderDetails>>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            OrderDetails orderDetails = copyOrderFromCursor(cursor);
            for (int i = 0; i < traderDetailsArrayList.size(); i++) {
                ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<OrderDetails>();
                for (int j = 0; j < productDetailsArrayList.size(); j++) {

//                    Log.i("Order Details Trader",orderDetails.getTraderID()+"");
//                    Log.i("Order Details Product",orderDetails.getProductID()+"");
//                    Log.i("Order Trader", traderDetailsArrayList.get(i)._id+"");
//                    Log.i("Order Product", productDetailsArrayList.get(j)._id+"");

                    if(traderDetailsArrayList.get(i)._id == orderDetails.getTraderID()){
                        if(productDetailsArrayList.get(j)._id == orderDetails.getProductID()){
//                            Log.i("Order Details-->",orderDetails.logString());
                            orderDetailsArrayList.add(orderDetails);
                            if(cursor.moveToNext()) orderDetails = copyOrderFromCursor(cursor);

                        }else{
                            OrderDetails tempOrderDetails= new OrderDetails();
                            orderDetailsArrayList.add(tempOrderDetails);
                        }
                    }else{
                        OrderDetails tempOrderDetails= new OrderDetails();
                        orderDetailsArrayList.add(tempOrderDetails);
                    }
                }
                orderDetailsArrayListArray.add(orderDetailsArrayList);
            }

        }else{
            for (int i = 0; i < traderDetailsArrayList.size(); i++) {
                ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<OrderDetails>();
                for (int j = 0; j < productDetailsArrayList.size(); j++) {
                    OrderDetails tempOrderDetails= new OrderDetails();
                    orderDetailsArrayList.add(tempOrderDetails);
                }
                orderDetailsArrayListArray.add(orderDetailsArrayList);
            }

        }
        return orderDetailsArrayListArray;
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


    public BillDetails copyBillFromCursor(Cursor c){
        BillDetails billDetails = new BillDetails();
        billDetails.set_id(c.getLong(c.getColumnIndex(DataBaseManager.BILL_ROW_ID)));
        billDetails.setBillNo(c.getLong(c.getColumnIndex(DataBaseManager.BILL_NO)));
        billDetails.setBillDate(c.getString(c.getColumnIndex(DataBaseManager.BILL_DATE)));
        billDetails.setUuid(c.getString(c.getColumnIndex(DataBaseManager.BILL_UNIQUE_ID)));
        billDetails.setTraderID(c.getLong(c.getColumnIndex(DataBaseManager.BILL_TRADER_ID)));
        billDetails.setCreateDateTime(c.getString(c.getColumnIndex(DataBaseManager.BILL_CREATE_DATE_TIME)));
        billDetails.setUpdateDateTime(c.getString(c.getColumnIndex(DataBaseManager.BILL_UPDATE_DATE_TIME)));
        billDetails.setBalanceAmount(c.getFloat(c.getColumnIndex(DataBaseManager.BILL_BALANCE_AMOUNT)));
        billDetails.setOldBalanceAmount(c.getFloat(c.getColumnIndex(DataBaseManager.BILL_OLD_BALANCE_AMOUNT)));
        billDetails.setBillAmount(c.getFloat(c.getColumnIndex(DataBaseManager.BILL_AMOUNT)));
        return billDetails;
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
