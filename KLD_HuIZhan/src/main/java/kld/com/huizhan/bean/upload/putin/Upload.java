package kld.com.huizhan.bean.upload.putin;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;

/**
 * Created by LDW10000000 on 27/11/2017.
 */

public class Upload extends UpLoadBaseBean implements Serializable{

    @SerializedName(value = "BillNo", alternate = {"BillNo_"})
    private String BillNo;

    @SerializedName(value = "BillPerson", alternate = {"BillPerson_"})
    private String BillPerson;

    @SerializedName(value = "BillDate", alternate = {"BillDate_"})
    private String BillDate;

    @SerializedName(value = "StockID", alternate = {"StockID_"}) //"StockID": "1",
    private String StockID;

    @SerializedName(value = "StockName", alternate = {"StockName_"}) //"StockName": "资产仓",
    private String StockName;

    @SerializedName(value = "AssetsID", alternate = {"AssetsID_"})//"AssetsID": "1",
    private String AssetsID;

    @SerializedName(value = "InstockNum", alternate = {"InStockNum_"})
    private String InstockNum;

    @SerializedName(value = "InDate", alternate = {"InDate_"})
    private String InDate;

    private String SupplierName;

//20180730 修改要求 :1、入库上传的时候，增加字段AssetName、SpecModel、CataCode、CateName、InstockCode



    @SerializedName(value = "AssetName", alternate = {"name_"})
    private String AssetName; //"AssetsName": "太阳伞",

    @SerializedName(value = "SpecModel", alternate = {"model_"})
    private String SpecModel; //"AssetSpecModel": "WS33223",

    @SerializedName(value = "CataCode", alternate = {"cateCode_"})
    private String CataCode; //"AssetsCateCode": "60-634101",

    @SerializedName(value = "CateName", alternate = {"cateName_"})
    private String CateName; //"AssetsCateName": "电饭锅",

    @SerializedName(value = "InstockCode", alternate = {"code_"})
    private String InstockCode; //"AssetCode": "HZJT000001-002",




    public Upload() {
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    public String getBillPerson() {
        return BillPerson;
    }

    public void setBillPerson(String billPerson) {
        BillPerson = billPerson;
    }

    public String getBillDate() {
        return BillDate;
    }

    public void setBillDate(String billDate) {
        BillDate = billDate;
    }

    public String getStockID() {
        return StockID;
    }

    public void setStockID(String stockID) {
        StockID = stockID;
    }

    public String getStockName() {
        return StockName;
    }

    public void setStockName(String stockName) {
        StockName = stockName;
    }

    public String getAssetsID() {
        return AssetsID;
    }

    public void setAssetsID(String assetsID) {
        AssetsID = assetsID;
    }

    public String getInstockNum() {
        return InstockNum;
    }

    public void setInstockNum(String instockNum) {
        InstockNum = instockNum;
    }

    public String getInDate() {
        return InDate;
    }

    public void setInDate(String inDate) {
        InDate = inDate;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getAssetName() {
        return AssetName;
    }

    public void setAssetName(String assetName) {
        AssetName = assetName;
    }

    public String getSpecModel() {
        return SpecModel;
    }

    public void setSpecModel(String specModel) {
        SpecModel = specModel;
    }

    public String getCataCode() {
        return CataCode;
    }

    public void setCataCode(String cataCode) {
        CataCode = cataCode;
    }

    public String getCateName() {
        return CateName;
    }

    public void setCateName(String cateName) {
        CateName = cateName;
    }

    public String getInstockCode() {
        return InstockCode;
    }

    public void setInstockCode(String instockCode) {
        InstockCode = instockCode;
    }

    @Override
    public String toString() {
        return "Upload{" +
                "BillNo='" + BillNo + '\'' +
                ", BillPerson='" + BillPerson + '\'' +
                ", BillDate='" + BillDate + '\'' +
                ", StockID='" + StockID + '\'' +
                ", StockName='" + StockName + '\'' +
                ", AssetsID='" + AssetsID + '\'' +
                ", InstockNum='" + InstockNum + '\'' +
                ", InDate='" + InDate + '\'' +
                ", SupplierName='" + SupplierName + '\'' +
                ", AssetName='" + AssetName + '\'' +
                ", SpecModel='" + SpecModel + '\'' +
                ", CataCode='" + CataCode + '\'' +
                ", CateName='" + CateName + '\'' +
                ", InstockCode='" + InstockCode + '\'' +
                '}';
    }


    //    @SerializedName(value = "bill", alternate = {"BillNo"})
//    private String bill;
//
//    @SerializedName(value = "name", alternate = {"BillPerson"})
//    private String name;
//
//    @SerializedName(value = "putInDate", alternate = {"BillDate"})
//    private String putInDate;
//
//    @SerializedName(value = "storageID", alternate = {"StockID"}) //"StockID": "1",
//    private String storageID;
//
//    @SerializedName(value = "storageName", alternate = {"StockName"}) //"StockName": "资产仓",
//    private String storageName;
//
//    @SerializedName(value = "assetID", alternate = {"AssetsID"})//"AssetsID": "1",
//    private String assetID;
//
//    @SerializedName(value = "number", alternate = {"InStockNum"})
//    private String number;
//
//    @SerializedName(value = "dateAvailable", alternate = {"InDate"})
//    private String dateAvailable;
//
//    public Upload() {
//    }
//
//    public String getBill() {
//        return bill;
//    }
//
//    public void setBill(String bill) {
//        this.bill = bill;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPutInDate() {
//        return putInDate;
//    }
//
//    public void setPutInDate(String putInDate) {
//        this.putInDate = putInDate;
//    }
//
//    public String getStorageID() {
//        return storageID;
//    }
//
//    public void setStorageID(String storageID) {
//        this.storageID = storageID;
//    }
//
//    public String getStorageName() {
//        return storageName;
//    }
//
//    public void setStorageName(String storageName) {
//        this.storageName = storageName;
//    }
//
//    public String getAssetID() {
//        return assetID;
//    }
//
//    public void setAssetID(String assetID) {
//        this.assetID = assetID;
//    }
//
//    public String getNumber() {
//        return number;
//    }
//
//    public void setNumber(String number) {
//        this.number = number;
//    }
//
//    public String getDateAvailable() {
//        return dateAvailable;
//    }
//
//    public void setDateAvailable(String dateAvailable) {
//        this.dateAvailable = dateAvailable;
//    }
//
//    @Override
//    public String toString() {
//        return "Upload{" +
//                "bill='" + bill + '\'' +
//                ", name='" + name + '\'' +
//                ", putInDate='" + putInDate + '\'' +
//                ", storageID='" + storageID + '\'' +
//                ", storageName='" + storageName + '\'' +
//                ", assetID='" + assetID + '\'' +
//                ", number='" + number + '\'' +
//                ", dateAvailable='" + dateAvailable + '\'' +
//                '}';
//    }
}
