package kld.com.huizhan.bean.upload.repair;

import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;

/**
 * Created by LDW10000000 on 06/12/2017.
 */

public class UpLoadRepair extends UpLoadBaseBean{

    private String AssetID;
    private String AssetCode;
    private String Reulst ;
    private String Desc;
    private String BillPerson;//经办人


// "AssetsName": "矿泉水",
    private String name;
//"AssetSpecModel": "XE30O",
    private String model;

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

    public UpLoadRepair() {
    }

    public String getAssetID() {
        return AssetID;
    }

    public void setAssetID(String assetID) {
        AssetID = assetID;
    }

    public String getAssetCode() {
        return AssetCode;
    }

    public void setAssetCode(String assetCode) {
        AssetCode = assetCode;
    }

    public String getReulst() {
        return Reulst;
    }

    public void setReulst(String reulst) {
        Reulst = reulst;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getBillPerson() {
        return BillPerson;
    }

    public void setBillPerson(String billPerson) {
        BillPerson = billPerson;
    }

    @Override
    public String toString() {
        return "UpLoadRepair{" +
                "AssetID='" + AssetID + '\'' +
                ", AssetCode='" + AssetCode + '\'' +
                ", Reulst='" + Reulst + '\'' +
                ", Desc='" + Desc + '\'' +
                ", BillPerson='" + BillPerson + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
