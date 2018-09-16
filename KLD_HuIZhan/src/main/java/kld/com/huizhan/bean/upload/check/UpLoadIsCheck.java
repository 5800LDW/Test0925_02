package kld.com.huizhan.bean.upload.check;

import com.google.gson.annotations.SerializedName;

import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;

/**
 * Created by LDW10000000 on 13/12/2017.
 */

public class UpLoadIsCheck  extends UpLoadBaseBean {
//    AssetID
//    InvenDate
//    InventPerson
//    InventNum
//    InventResult


//    private String AssetsID; // 这里不要修改, 修改了还要修改DataFunctionUtil
//    private String Num;   //这里修改了的话, DataFunctionUtil也要修改
//    private String Person;
//    private String Date;
//    private String Result;
    @SerializedName(value = "AssetID", alternate = {"AssetsID"})
    private String AssetID;

    @SerializedName(value = "InventNum", alternate = {"Num"})
    private String InventNum;

    @SerializedName(value = "InventPerson", alternate = {"Person"})
    private String InventPerson;

    @SerializedName(value = "InvenDate", alternate = {"Date"})
    private String InvenDate;

    @SerializedName(value = "InventResult", alternate = {"Result"})
    private String InventResult = "已盘";

    public UpLoadIsCheck() {
    }

    public String getAssetID() {
        return AssetID;
    }

    public void setAssetID(String assetID) {
        AssetID = assetID;
    }

    public String getInventNum() {
        return InventNum;
    }

    public void setInventNum(String inventNum) {
        InventNum = inventNum;
    }

    public String getInventPerson() {
        return InventPerson;
    }

    public void setInventPerson(String inventPerson) {
        InventPerson = inventPerson;
    }

    public String getInvenDate() {
        return InvenDate;
    }

    public void setInvenDate(String invenDate) {
        InvenDate = invenDate;
    }

    public String getInventResult() {
        return InventResult;
    }

    public void setInventResult(String inventResult) {
        InventResult = inventResult;
    }

    @Override
    public String toString() {
        return "UpLoadIsCheck{" +
                "AssetID='" + AssetID + '\'' +
                ", InventNum='" + InventNum + '\'' +
                ", InventPerson='" + InventPerson + '\'' +
                ", InvenDate='" + InvenDate + '\'' +
                ", InventResult='" + InventResult + '\'' +
                '}';
    }
}
