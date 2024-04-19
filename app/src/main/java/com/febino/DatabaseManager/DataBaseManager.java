package com.febino.DatabaseManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.febino.aquafish.MainActivity;
import com.febino.dataclass.OrderDetails;
import com.febino.dataclass.ProductDetails;
import com.febino.dataclass.TraderDetails;
import androidx.annotation.RequiresApi;


public class DataBaseManager {
    private SQLiteDatabase db;
    private static final String DB_NAME = "aqua_fish";
    private final String SUB_TAG_NAME = "Database";
    private static final int DB_VERSION = 1;
//    public static final String BACKUP_FOLDER_PATH = MainActivity.APP_NAME+"/backup/";
//    public static final String DATABASE_FILE = Environment.getDataDirectory()+"/data/com.modernscale.poultry/databases/" + DB_NAME;
//    public static final String DATABASE_PATH = Environment.getDataDirectory()+"/data/com.modernscale.poultry/databases/";

    /////////////////table name and row name for the trader registartion //////////////////////////
    public static final String TRADER_REG_TABLE = "trader_registration";
    public static final String TRADER_REG_ROW_ID = "_id";
    public static final String TRADER_REG_ROW_NAME = "name";
    public static final String TRADER_REG_ROW_ALIAS = "alias";
    public static final String TRADER_REG_ROW_LOCATION = "location";
    public static final String TRADER_REG_ROW_MOBILE_NO = "mobile_no";
    public static final String TRADER_REG_ROW_TRADER_ID = "trader_id";
    public static final String TRADER_REG_ROW_CREATE_DATE = "create_date_time";

    //////////////////////////////////////////////////////////////////////////////////////////////




    /////////////////table name and row name for the trader registartion //////////////////////////
    public static final String ORDER_TABLE = "order_table";
    public static final String ORDER_ROW_ID = "_id";
    public static final String ORDER_TRADER_ID = "trader_id";
    public static final String ORDER_PRODUCT_ID = "product_id";
    public static final String ORDER_CREATE_DATE_TIME = "order_create_date";
    public static final String ORDER_UPDATE_DATE_TIME = "order_update_date";
    public static final String ORDER_BOX = "box";
    public static final String ORDER_KG = "kg";
    public static final String ORDER_RATE = "rate";
    public static final String ORDER_DATE = "order_date";

    //////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////table name and row name for the Product items////////////////////////////////////
    public static final String PRODUCT_TABLE = "product_table";
    public static final String PRODUCT_ROW_ID = "_id";
    public static final String PRODUCT_ROW_NAME = "product_name";
    public static final String PRODUCT_ROW_SHORT_NAME = "short_name";
    public static final String PRODUCT_ROW_DESCRIPTION = "description";
    public static final String PRODUCT_ROW_CREATE_DATE = "create_date_time";

    ////////////////////////////////////////////////////////////////////////////////////////////////



    public DataBaseManager(Context context){
        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
        db = helper.getWritableDatabase();
        db.disableWriteAheadLogging();
    }



//    public String[] copyString(Cursor c){
//        String copyArrayString[] = new String[c.getCount()];
//        for (int i = 0; i < c.getCount(); i++) {
//            c.moveToNext();
//            copyArrayString[i] = c.getString(0);
//        }
//        return copyArrayString;
//    }




    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
         public CustomSQLiteOpenHelper(Context context){
             super(context, DB_NAME, null, DB_VERSION);
         }

        @Override
        public void onCreate(SQLiteDatabase db) {
             createTraderTable(db);
             createProductTable(db);
             createOrderTable(db);
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



        }

    }

    private void createOrderTable(SQLiteDatabase db) {
        String createTraderTableQueryString;
        createTraderTableQueryString = "create table " + ORDER_TABLE + " ("
                + ORDER_ROW_ID
                + " integer primary key autoincrement not null, "
                + ORDER_TRADER_ID
                + " integer not null,"
                + ORDER_PRODUCT_ID
                + " integer not null,"
                + ORDER_CREATE_DATE_TIME
                + " DATETIME DEFAULT (datetime('now','localtime')) not null,"
                + ORDER_UPDATE_DATE_TIME
                + " DATETIME,"
                + ORDER_DATE
                + " DATETIME not null,"
                + ORDER_BOX
                + " integer default 0,"
                + ORDER_KG
                + " real default 0,"
                + ORDER_RATE
                + " real default 0"
                + ");";

        db.execSQL(createTraderTableQueryString);
        log("Created Order Table");
    }

    public long addOrderInOrderTableReturnID(OrderDetails orderDetails) {
        ContentValues orderDetailsValues = new ContentValues();
        orderDetailsValues.put(ORDER_TRADER_ID,orderDetails.getTraderID());
        orderDetailsValues.put(ORDER_PRODUCT_ID,orderDetails.getProductID());
        orderDetailsValues.put(ORDER_DATE,orderDetails.getOrderDate());
        orderDetailsValues.put(ORDER_KG, orderDetails.getTotalKG());
        orderDetailsValues.put(ORDER_RATE, orderDetails.getRatePerKG());
        orderDetailsValues.put(ORDER_BOX, orderDetails.getTotalBox());

        long lastID = db.insert(ORDER_TABLE, null, orderDetailsValues);
        return lastID;
    }



    public void updateOrderInOrderTable(OrderDetails orderDetails){
        String updateOrderQuery = "UPDATE " + ORDER_TABLE + " SET " +
                ORDER_TRADER_ID + " = " + orderDetails.getTraderID() + ", " +
                ORDER_PRODUCT_ID + " = " + orderDetails.getProductID() + ", " +
                ORDER_DATE + " = '" + orderDetails.getOrderDate() + "', " +
                ORDER_UPDATE_DATE_TIME + " = dateTime('now'), " +
                ORDER_KG + " = " + orderDetails.getTotalKG() + ", " +
                ORDER_RATE + " = " + orderDetails.getRatePerKG() + ", " +
                ORDER_BOX + " = " + orderDetails.getTotalBox() +
                " WHERE " + ORDER_ROW_ID + " = " + orderDetails.get_id();

        db.execSQL(updateOrderQuery);
        log("Updated Order detail successfully");
    }



    public Cursor getOrderTotalByProduct(String selectedDate){
        String queryString = "SELECT "+ORDER_ROW_ID+","+ORDER_TRADER_ID+","+ORDER_PRODUCT_ID+","+ORDER_CREATE_DATE_TIME+","+ORDER_UPDATE_DATE_TIME+", "+ORDER_DATE+", sum("+ORDER_BOX+") as "+ORDER_BOX+",sum("+ORDER_KG+") as "+ORDER_KG+", "+ORDER_RATE+" FROM "+ORDER_TABLE+" WHERE "+ORDER_DATE+" = '"+selectedDate+"'  GROUP BY "+ORDER_PRODUCT_ID+" order by "+ORDER_PRODUCT_ID+" ASC";
        Cursor c = db.rawQuery(queryString, null);
        return c;
    }

    public Cursor getOrderFromOrderTableByDate(String date){
        String queryString = "SELECT * FROM "+ ORDER_TABLE + " WHERE "+ ORDER_DATE +" = '"+date+"' ORDER BY " + ORDER_ROW_ID + " ASC";
        Cursor c = db.rawQuery(queryString,null);
        return c;
    }

    public Cursor getOrderFromOrderTableWithDateAndOrderBy(String date){
        String getOrderQuery = "SELECT * FROM " + ORDER_TABLE + " WHERE "+ ORDER_DATE + " = '"+ date+"' ORDER BY "+ ORDER_TRADER_ID + " , "+ ORDER_PRODUCT_ID;
        Cursor c = db.rawQuery(getOrderQuery,null);
//        c.moveToFirst();
        return c;
    }

    public void deleteOrderByID(long id){
        String deleteQuery = "DELETE FROM "+ ORDER_TABLE + " WHERE "+ ORDER_ROW_ID +" = " + id;
        db.execSQL(deleteQuery);
    }

    public void deleteOrderByTraderID(long traderID){
        String deleteQuery = "DELETE FROM "+ ORDER_TABLE + " WHERE "+ ORDER_TRADER_ID +" = " + traderID;
        db.execSQL(deleteQuery);
    }

    public void deleteOrderByProductID(long productID){
        String deleteQuery = "DELETE FROM "+ ORDER_TABLE + " WHERE "+ ORDER_PRODUCT_ID +" = " + productID;
        db.execSQL(deleteQuery);
    }



    private void createProductTable(SQLiteDatabase db) {
        String createTraderTableQueryString;
        createTraderTableQueryString = "create table " + PRODUCT_TABLE + " ("
                + PRODUCT_ROW_ID
                + " integer primary key autoincrement not null, "
                + PRODUCT_ROW_NAME
                + " text not null,"
                + PRODUCT_ROW_SHORT_NAME
                + " text not null unique,"
                + PRODUCT_ROW_CREATE_DATE
                + " DATETIME DEFAULT (datetime('now','localtime')) not null,"
                + PRODUCT_ROW_DESCRIPTION
                + " text"
                + ");";

        db.execSQL(createTraderTableQueryString);
        log("Created Product Table");

    }

    public long addProductInProductTableReturnID(ProductDetails productDetails) {
        ContentValues productDetailsValues = new ContentValues();
        productDetailsValues.put(PRODUCT_ROW_NAME,productDetails.productName);
        productDetailsValues.put(PRODUCT_ROW_DESCRIPTION,productDetails.description);
        productDetailsValues.put(PRODUCT_ROW_SHORT_NAME,productDetails.shortName);
        long lastID = db.insert(PRODUCT_TABLE, null, productDetailsValues);
        return lastID;
    }



    public boolean checkProductShortNameExist(String shortName){
        String checkProductShortNameQuery = "SELECT * FROM "+ PRODUCT_TABLE + " WHERE " + PRODUCT_ROW_SHORT_NAME + " = '" + shortName + "'";
        Cursor c = db.rawQuery(checkProductShortNameQuery, null);
        log("count--" + c.getCount());
        if(c.getCount() > 0)
            return true;
        return false;
    }

    public void deleteProductDetailsByID(long id){
        String deleteProductByIDQuery = "DELETE FROM " + PRODUCT_TABLE + " WHERE " + PRODUCT_ROW_ID + " = " + id;
        db.execSQL(deleteProductByIDQuery);
    }

    public void updateProductInProductTable(ProductDetails productDetails){
        String updateTraderQuery = "UPDATE " + PRODUCT_TABLE + " SET " +
                PRODUCT_ROW_NAME + " = '" + productDetails.productName + "'," +
                PRODUCT_ROW_SHORT_NAME + " = '" + productDetails.shortName +  "'," +
                PRODUCT_ROW_DESCRIPTION + " = '" + productDetails.description +  "'" +
                " WHERE " + PRODUCT_ROW_ID + " = " + productDetails._id ;

        db.execSQL(updateTraderQuery);
        log("Updated Product detail successfully");
    }

    public Cursor getAllProductFromProductTable(){
        String queryString = "SELECT * FROM "+ PRODUCT_TABLE + " ORDER BY " + PRODUCT_ROW_ID + " ASC";
        Cursor c = db.rawQuery(queryString,null);
        return c;
    }

    public Cursor getProductFromProductTableByID(long id){
        String getProductString = "SELECT * FROM "+ PRODUCT_TABLE +" WHERE "+PRODUCT_ROW_ID + " = " + id;
        Cursor c = db.rawQuery(getProductString,null);
        c.moveToFirst();
        return c;
    }

    public void createTraderTable(SQLiteDatabase db){
        String createTraderTableQueryString;
        createTraderTableQueryString = "create table " + TRADER_REG_TABLE + " ("
                + TRADER_REG_ROW_ID
                + " integer primary key autoincrement not null, "
                + TRADER_REG_ROW_NAME
                + " text not null,"
                + TRADER_REG_ROW_LOCATION
                + " text,"
                + TRADER_REG_ROW_CREATE_DATE
                + " DATETIME DEFAULT (datetime('now','localtime')) not null,"
                + TRADER_REG_ROW_ALIAS
                + " text,"
                + TRADER_REG_ROW_MOBILE_NO
                + " text,"
                + TRADER_REG_ROW_TRADER_ID
                + " text unique"
                + ");";

        db.execSQL(createTraderTableQueryString);
        log("Created Trader Table");
    }

    public void addTraderInTraderTable(TraderDetails traderDetails) {
        String query = "INSERT INTO "+TRADER_REG_TABLE+"("+
                TRADER_REG_ROW_NAME+", "+
                TRADER_REG_ROW_LOCATION+", "+
                TRADER_REG_ROW_ALIAS+", "+
                TRADER_REG_ROW_MOBILE_NO+", "+
                TRADER_REG_ROW_TRADER_ID+
                ")"+
                "VALUES ("+"'"+traderDetails.name+"'"+", "+
                "'" + traderDetails.location + "'"+", "+
                "'" + traderDetails.alias + "'"+", "+
                "'" + traderDetails.mobile+ "'"+", "+
                "'" +traderDetails.trader_id + "'"+
                ");";
        db.execSQL(query);
    }



    public long addTraderInTraderTableReturnID(TraderDetails traderDetails) {

        ContentValues traderDetailsValues = new ContentValues();
        traderDetailsValues.put(TRADER_REG_ROW_NAME,traderDetails.name);
        traderDetailsValues.put(TRADER_REG_ROW_LOCATION,traderDetails.location);
        traderDetailsValues.put(TRADER_REG_ROW_ALIAS,traderDetails.alias);
        traderDetailsValues.put(TRADER_REG_ROW_MOBILE_NO,traderDetails.mobile);
        traderDetailsValues.put(TRADER_REG_ROW_TRADER_ID,traderDetails.trader_id);
        long lastID = db.insert(TRADER_REG_TABLE, null, traderDetailsValues);
        return lastID;
    }

    public Cursor getTraderInTraderTableByID(long id){
        String getTraderQueryString = "SELECT * FROM "+ TRADER_REG_TABLE +" WHERE "+TRADER_REG_ROW_ID + " = " + id;
        Cursor c = db.rawQuery(getTraderQueryString,null);
        c.moveToFirst();
        return c;
    }

    public Cursor getTraderInTraderTableByTraderID(int traderID){
        String getTraderByTraderIDQuery = "SELECT * FROM "+ TRADER_REG_TABLE + " WHERE "+TRADER_REG_ROW_TRADER_ID + " = " + traderID;
        Cursor c = db.rawQuery(getTraderByTraderIDQuery, null);
        return c;
    }

    public void deleteDetailsByID(long id){
        String deleteTraderByID = "DELETE FROM " + TRADER_REG_TABLE + " WHERE " + TRADER_REG_ROW_ID + " = " + id;
        db.execSQL(deleteTraderByID);
//        log("Deleted Trader Successfully");
    }

    public void updateTraderInTraderTable(TraderDetails traderDetails){
        String updateTraderQuery = "UPDATE " + TRADER_REG_TABLE + " SET " +
                TRADER_REG_ROW_NAME + " = '" + traderDetails.name + "'," +
                TRADER_REG_ROW_LOCATION + " = '" + traderDetails.location +  "'," +
                TRADER_REG_ROW_MOBILE_NO + " = '" + traderDetails.mobile +  "'," +
                TRADER_REG_ROW_ALIAS + " = '" + traderDetails.alias +  "'," +
                TRADER_REG_ROW_TRADER_ID + " = '" + traderDetails.trader_id + "'"+
                " WHERE " + TRADER_REG_ROW_ID + " = " + traderDetails._id ;
        db.execSQL(updateTraderQuery);
        log("Updated Trader detail successfully");
    }



    public Cursor getAllTraderFromTraderTable(){
        String queryString = "SELECT * FROM "+TRADER_REG_TABLE + " ORDER BY " + TRADER_REG_ROW_ID + " ASC";
        Cursor c = db.rawQuery(queryString,null);
        return c;
    }

    public boolean checkTraderIDExist(String traderID){
        String checkTraderIDQuery = "SELECT * FROM "+ TRADER_REG_TABLE + " WHERE " + TRADER_REG_ROW_TRADER_ID + " = '" + traderID + "'";
        Cursor c = db.rawQuery(checkTraderIDQuery, null);
        log("count--" + c.getCount());
        if(c.getCount() > 0)
            return true;
        return false;
    }

    private void log(String message) {
        Log.i(MainActivity.TAG_NAME+"-"+this.SUB_TAG_NAME,message);
    }
}
