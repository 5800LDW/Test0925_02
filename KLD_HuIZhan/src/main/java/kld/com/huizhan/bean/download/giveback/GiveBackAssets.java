package kld.com.huizhan.bean.download.giveback;

import kld.com.huizhan.bean.base.BaseBean;

/**
 * Created by LDW10000000 on 04/12/2017.
 *
 *
 *  注意,你可能看到GiveBackAssets这个表和givebackbill这两个表一直没变化,其实, 这两个表不用变化,这只是
 *  下载的时候的初始状态;
 *  真正你点击"确定"或其他的时候,会修改为true是修改在 :UploadGiveBackAssets 和 uploadgivebackbill这两个表
 *
 *
 *
 *
 */

public class GiveBackAssets extends BaseBean {

//       "OutDepotID": "15",
//               "AssetsID": "73",
//               "AssetCode": "24324qwe3-15",
//               "OutDepotNum": "1",
//               "AssetStockID": "1",
//               "AssetStockName": "资产仓"

    private String OutDepotID;
    private String AssetsID;
    private String AssetCode;
    private String OutDepotNum;
    private String AssetStockID;
    private String AssetStockName;

    private String ReturnCheck = "false";

    private GiveBackBill giveBackBill;

    //我另外添加的 , 物资名称
    private String name = "";
    //我另外添加的规格型号;
    private String model = "";

    //TODO 归还的时候增加ReturnNum
    private  String ReturnNum = "0";


    public GiveBackAssets() {
    }

    public String getOutDepotID() {
        return OutDepotID;
    }

    public void setOutDepotID(String outDepotID) {
        OutDepotID = outDepotID;
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

    public String getReturnCheck() {
        return ReturnCheck;
    }

    public void setReturnCheck(String returnCheck) {
        ReturnCheck = returnCheck;
    }

    public GiveBackBill getGiveBackBill() {
        return giveBackBill;
    }

    public void setGiveBackBill(GiveBackBill giveBackBill) {
        this.giveBackBill = giveBackBill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getReturnNum() {
        return ReturnNum;
    }

    public void setReturnNum(String returnNum) {
        ReturnNum = returnNum;
    }

    @Override
    public String toString() {
        return "GiveBackAssets{" +
                "OutDepotID='" + OutDepotID + '\'' +
                ", AssetsID='" + AssetsID + '\'' +
                ", AssetCode='" + AssetCode + '\'' +
                ", OutDepotNum='" + OutDepotNum + '\'' +
                ", AssetStockID='" + AssetStockID + '\'' +
                ", AssetStockName='" + AssetStockName + '\'' +
                ", ReturnCheck='" + ReturnCheck + '\'' +
                ", giveBackBill=" + giveBackBill +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", ReturnNum='" + ReturnNum + '\'' +
                '}';
    }
}
