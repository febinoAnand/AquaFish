package com.febino.dataclass;

public class BillDetails {
    private long _id;
    private String uuid;
    private long billNo;
    private String billDate;
    private long traderID;
    private String createDateTime;
    private String updateDateTime;
    private float balanceAmount;
    private float oldBalanceAmount;

    private float billAmount;


    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getBillNo() {
        return billNo;
    }

    public void setBillNo(long billNo) {
        this.billNo = billNo;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public long getTraderID() {
        return traderID;
    }

    public void setTraderID(long traderID) {
        this.traderID = traderID;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public float getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(float balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public float getOldBalanceAmount() {
        return oldBalanceAmount;
    }

    public void setOldBalanceAmount(float oldBalanceAmount) {
        this.oldBalanceAmount = oldBalanceAmount;
    }

    public float getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(float billAmount) {
        this.billAmount = billAmount;
    }
}
