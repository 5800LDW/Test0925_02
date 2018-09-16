package kld.com.huizhan.bean.download.assets;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import kld.com.huizhan.bean.base.BaseBean;

/**
 * Created by LDW10000000 on 22/11/2017.
 */

public class Assets extends BaseBean implements Serializable {

    @SerializedName(value = "assetID", alternate = {"AssetsID"})//"AssetsID": "1",
    private String assetID;
    @SerializedName(value = "code", alternate = {"AssetCode"})//"AssetCode": "SF313344676",
    private String code;
    @SerializedName(value = "name", alternate = {"AssetsName"}) // "AssetsName": "矿泉水",
    private String name;
    @SerializedName(value = "model", alternate = {"AssetSpecModel"}) //"AssetSpecModel": "XE30O",
    private String model;
    @SerializedName(value = "cateCode", alternate = {"AssetsCateCode"}) // "AssetsCateCode": "11-028101",
    private String cateCode;
    @SerializedName(value = "cateName", alternate = {"AssetsCateName"}) //"AssetsCateName": "职工宿舍",
    private String cateName;
    @SerializedName(value = "price", alternate = {"AssetsPrice"}) // "AssetsPrice": "123",
    private String price;
    @SerializedName(value = "unit1", alternate = {"AssestUnit1"}) //"AssestUnit1": "箱",
    private String unit1;
    @SerializedName(value = "number1", alternate = {"AssestNum1"}) //"AssestNum1": "1",
    private String number1;
    @SerializedName(value = "unit2", alternate = {"AssetsUnit2"}) // "AssetsUnit2": "瓶",
    private String unit2;
    @SerializedName(value = "number2", alternate = {"AssetsNum2"}) //"AssetsNum2": "12",
    private String number2;
    @SerializedName(value = "department", alternate = {"ManageDepartment"}) //"ManageDepartment": "仓库-资产仓",
    private String department;
    @SerializedName(value = "controller", alternate = {"Manager"}) //"Manager": "",
    private String controller;
    @SerializedName(value = "places", alternate = {"Place"}) //"Place": "",
    private String places;
    @SerializedName(value = "provider", alternate = {"Supplier"}) // "Supplier": "广东康利达物联科技股份有限公司",
    private String provider;
    @SerializedName(value = "validity", alternate = {"Indate"}) // "Indate": "",
    private String validity;
    @SerializedName(value = "maxBuy", alternate = {"MaxStock"}) //"MaxStock": "30",
    private String maxBuy;
    @SerializedName(value = "minBuy", alternate = {"MinStock"}) // "MinStock": "10",
    private String minBuy;
    @SerializedName(value = "maintenancePeriod", alternate = {"MaintenanceCycle"}) //"MaintenanceCycle": "45",
    private String maintenancePeriod;
    @SerializedName(value = "recentMaintain", alternate = {"LastMaintennace"}) //"LastMaintennace": "",
    private String recentMaintain;
    @SerializedName(value = "maintainResult", alternate = {"MaintenanceResult"}) //"MaintenanceResult": "",
    private String maintainResult;
    @SerializedName(value = "storageID", alternate = {"StockID"}) //"StockID": "1",
    private String storageID;
    @SerializedName(value = "storageName", alternate = {"StockName"}) //"StockName": "资产仓",
    private String storageName;
    @SerializedName(value = "codeType", alternate = {"BarCodeType"}) //"BarCodeType": "独立条码"只能是1 "下标条码"只能是1, //只有 "共用条码" 才能输入数量
    private String codeType;

    @SerializedName(value = "state", alternate = {"AssetState"}) //"AssetState": "在库",
    private String state;

    @SerializedName(value = "explain", alternate = {"Remark"}) // "Remark": ""
    private String explain;

    public Assets() {
    }

    public String getAssetID() {
        return assetID;
    }

    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getCateCode() {
        return cateCode;
    }

    public void setCateCode(String cateCode) {
        this.cateCode = cateCode;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit1() {
        return unit1;
    }

    public void setUnit1(String unit1) {
        this.unit1 = unit1;
    }

    public String getNumber1() {
        return number1;
    }

    public void setNumber1(String number1) {
        this.number1 = number1;
    }

    public String getUnit2() {
        return unit2;
    }

    public void setUnit2(String unit2) {
        this.unit2 = unit2;
    }

    public String getNumber2() {
        return number2;
    }

    public void setNumber2(String number2) {
        this.number2 = number2;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getMaxBuy() {
        return maxBuy;
    }

    public void setMaxBuy(String maxBuy) {
        this.maxBuy = maxBuy;
    }

    public String getMinBuy() {
        return minBuy;
    }

    public void setMinBuy(String minBuy) {
        this.minBuy = minBuy;
    }

    public String getMaintenancePeriod() {
        return maintenancePeriod;
    }

    public void setMaintenancePeriod(String maintenancePeriod) {
        this.maintenancePeriod = maintenancePeriod;
    }

    public String getRecentMaintain() {
        return recentMaintain;
    }

    public void setRecentMaintain(String recentMaintain) {
        this.recentMaintain = recentMaintain;
    }

    public String getMaintainResult() {
        return maintainResult;
    }

    public void setMaintainResult(String maintainResult) {
        this.maintainResult = maintainResult;
    }

    public String getStorageID() {
        return storageID;
    }

    public void setStorageID(String storageID) {
        this.storageID = storageID;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    @Override
    public String toString() {
        return "Assets{" +
                "assetID='" + assetID + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", cateCode='" + cateCode + '\'' +
                ", cateName='" + cateName + '\'' +
                ", price='" + price + '\'' +
                ", unit1='" + unit1 + '\'' +
                ", number1='" + number1 + '\'' +
                ", unit2='" + unit2 + '\'' +
                ", number2='" + number2 + '\'' +
                ", department='" + department + '\'' +
                ", controller='" + controller + '\'' +
                ", places='" + places + '\'' +
                ", provider='" + provider + '\'' +
                ", validity='" + validity + '\'' +
                ", maxBuy='" + maxBuy + '\'' +
                ", minBuy='" + minBuy + '\'' +
                ", maintenancePeriod='" + maintenancePeriod + '\'' +
                ", recentMaintain='" + recentMaintain + '\'' +
                ", maintainResult='" + maintainResult + '\'' +
                ", storageID='" + storageID + '\'' +
                ", storageName='" + storageName + '\'' +
                ", codeType='" + codeType + '\'' +
                ", state='" + state + '\'' +
                ", explain='" + explain + '\'' +
                '}';
    }
}
