package kld.com.huizhan.bean.upload.output;

import java.io.Serializable;

import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;

/**
 * Created by LDW10000000 on 29/11/2017.
 */

public class OutPutUpLoad extends UpLoadBaseBean implements Serializable {

    private String AssetsID;
    private String AssetCode;
    private String OutDepotNum;
    private String AssetStockID;
    private String AssetStockName;

    private String BillNo;

    private OutPutUpLoadList outPutUpLoadList;


    public OutPutUpLoad() {
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    public String getAssetsID() {
        return AssetsID;
    }

    public void setAssetsID(String assetsID) {
        AssetsID = assetsID;
    }

    public String getAssetCode() {
        return AssetCode;
    }

    public void setAssetCode(String assetCode) {
        AssetCode = assetCode;
    }

    public String getOutDepotNum() {
        return OutDepotNum;
    }

    public void setOutDepotNum(String outDepotNum) {
        OutDepotNum = outDepotNum;
    }

    public String getAssetStockID() {
        return AssetStockID;
    }

    public void setAssetStockID(String assetStockID) {
        AssetStockID = assetStockID;
    }

    public String getAssetStockName() {
        return AssetStockName;
    }

    public void setAssetStockName(String assetStockName) {
        AssetStockName = assetStockName;
    }

    public OutPutUpLoadList getOutPutUpLoadList() {
        return outPutUpLoadList;
    }

    public void setOutPutUpLoadList(OutPutUpLoadList outPutUpLoadList) {
        this.outPutUpLoadList = outPutUpLoadList;
    }

    @Override
    public String toString() {
        return "OutPutUpLoad{" +
                "AssetsID='" + AssetsID + '\'' +
                ", AssetCode='" + AssetCode + '\'' +
                ", OutDepotNum='" + OutDepotNum + '\'' +
                ", AssetStockID='" + AssetStockID + '\'' +
                ", AssetStockName='" + AssetStockName + '\'' +
                '}';
    }
}

