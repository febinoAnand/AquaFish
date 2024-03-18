package com.febino.aquafish;

public class OrderDetails {
    private int _id;
    private String createDate;
    private String entryDate;
    private int traderNameID;
    private int item_id;
    private float totalBox;
    private float totalKG;
    private float ratePerKG;
    private int billID;

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

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public int getTraderNameID() {
        return traderNameID;
    }

    public void setTraderNameID(int traderNameID) {
        this.traderNameID = traderNameID;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public float getTotalBox() {
        return totalBox;
    }

    public void setTotalBox(float totalBox) {
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

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }
}
