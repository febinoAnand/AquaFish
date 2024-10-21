package com.febino.dataclass;

public class OrderDetails {
    private long _id;
    private String createDateTime;
    private String updateDateTime;
    private String orderDate;
    private long traderID;
    private long productID;
    private int totalBox;
    private float totalKG;
    private float ratePerKG;

    private float kgPerBox;
    private boolean isBilled;
    private long billID;

    public boolean isSelected = false;



    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getUpdateDateTime(){return updateDateTime;}

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public long getTraderID() {
        return traderID;
    }

    public void setTraderID(long traderNameID) {
        this.traderID = traderNameID;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public int getTotalBox() {
        return totalBox;
    }

    public void setTotalBox(int totalBox) {
        this.totalBox = totalBox;
    }

    public float getTotalKG() {
        return totalKG;
    }

    public void setTotalKG(float totalKG) {
        this.totalKG = totalKG;
    }

    public float getRatePerKG() {
        return ratePerKG;
    }

    public void setRatePerKG(float ratePerKG) {
        this.ratePerKG = ratePerKG;
    }

    public float getKgPerBox() {
        return kgPerBox;
    }

    public void setKgPerBox(float kgPerBox) {
        this.kgPerBox = kgPerBox;
    }

    public boolean isBilled() {
        return isBilled;
    }

    public void setBilled(boolean billed) {
        isBilled = billed;
    }

    public long getBillID() {
        return billID;
    }

    public void setBillID(long billID) {
        this.billID = billID;
    }



    public String logString(){
        String orderDetails = "";

        orderDetails = "ID="+this._id+"\n"+
                        "createDate="+this.createDateTime+"\n"+
                        "updateDate="+this.updateDateTime+"\n"+
                        "orderDate="+this.orderDate+"\n"+
                        "traderID="+this.traderID+"\n"+
                        "productID="+this.productID+"\n"+
                        "totalBox="+this.totalBox+"\n"+
                        "totalKG="+this.totalKG+"\n"+
                        "ratePerKG="+this.ratePerKG+"\n";

        return orderDetails;
    }

}
