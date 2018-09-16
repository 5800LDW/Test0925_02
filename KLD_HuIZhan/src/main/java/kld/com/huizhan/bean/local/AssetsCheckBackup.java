//package kld.com.huizhan.bean.local;
//
//import kld.com.huizhan.bean.base.BaseBean;
//
///**
// * Created by LDW10000000 on 11/12/2017.
// */
//
//public class AssetsCheckBackup extends BaseBean {
//
//////    @SerializedName(value = "assetID", alternate = {"AssetsID"})//"AssetsID": "1",
////    private String assetID;
////    @SerializedName(value = "code", alternate = {"AssetCode"})//"AssetCode": "SF313344676",
//    private String code;
////    @SerializedName(value = "name", alternate = {"AssetsName"}) // "AssetsName": "矿泉水",
//    private String name;
////    @SerializedName(value = "model", alternate = {"AssetSpecModel"}) //"AssetSpecModel": "XE30O",
//    private String model;
//////    @SerializedName(value = "cateCode", alternate = {"AssetsCateCode"}) // "AssetsCateCode": "11-028101",
////    private String cateCode;
//////    @SerializedName(value = "cateName", alternate = {"AssetsCateName"}) //"AssetsCateName": "职工宿舍",
////    private String cateName;
//////    @SerializedName(value = "price", alternate = {"AssetsPrice"}) // "AssetsPrice": "123",
////    private String price;
//////    @SerializedName(value = "unit1", alternate = {"AssestUnit1"}) //"AssestUnit1": "箱",
////    private String unit1;
////    @SerializedName(value = "number1", alternate = {"AssestNum1"}) //"AssestNum1": "1",
//    private String number1;
//////    @SerializedName(value = "unit2", alternate = {"AssetsUnit2"}) // "AssetsUnit2": "瓶",
////    private String unit2;
//////    @SerializedName(value = "number2", alternate = {"AssetsNum2"}) //"AssetsNum2": "12",
////    private String number2;
//////    @SerializedName(value = "department", alternate = {"ManageDepartment"}) //"ManageDepartment": "仓库-资产仓",
////    private String department;
//////    @SerializedName(value = "controller", alternate = {"Manager"}) //"Manager": "",
////    private String controller;
//////    @SerializedName(value = "places", alternate = {"Place"}) //"Place": "",
////    private String places;
//////    @SerializedName(value = "provider", alternate = {"Supplier"}) // "Supplier": "广东康利达物联科技股份有限公司",
////    private String provider;
//////    @SerializedName(value = "validity", alternate = {"Indate"}) // "Indate": "",
////    private String validity;
//////    @SerializedName(value = "maxBuy", alternate = {"MaxStock"}) //"MaxStock": "30",
////    private String maxBuy;
//////    @SerializedName(value = "minBuy", alternate = {"MinStock"}) // "MinStock": "10",
////    private String minBuy;
//////    @SerializedName(value = "maintenancePeriod", alternate = {"MaintenanceCycle"}) //"MaintenanceCycle": "45",
////    private String maintenancePeriod;
//////    @SerializedName(value = "recentMaintain", alternate = {"LastMaintennace"}) //"LastMaintennace": "",
////    private String recentMaintain;
//////    @SerializedName(value = "maintainResult", alternate = {"MaintenanceResult"}) //"MaintenanceResult": "",
////    private String maintainResult;
//////    @SerializedName(value = "storageID", alternate = {"StockID"}) //"StockID": "1",
////    private String storageID;
//////    @SerializedName(value = "storageName", alternate = {"StockName"}) //"StockName": "资产仓",
////    private String storageName;
//////    @SerializedName(value = "codeType", alternate = {"BarCodeType"}) //"BarCodeType": "独立条码"只能是1 "下标条码"只能是1, //只有 "共用条码" 才能输入数量
////    private String codeType;
////
//////    @SerializedName(value = "state", alternate = {"AssetState"}) //"AssetState": "在库",
////    private String state;
////
//////    @SerializedName(value = "explain", alternate = {"Remark"}) // "Remark": ""
////    private String explain;
//
//
//
//
//    private String checkIsSucceed = "未盘"; //"已盘
//
//    private String actualNum = "1";
//
//    private String upLoadIsSucceed = "未上传";
//
//    public AssetsCheckBackup() {
//    }
//
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
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
//    public String getModel() {
//        return model;
//    }
//
//    public void setModel(String model) {
//        this.model = model;
//    }
//
//    public String getNumber1() {
//        return number1;
//    }
//
//    public void setNumber1(String number1) {
//        this.number1 = number1;
//    }
//
//    public String getCheckIsSucceed() {
//        return checkIsSucceed;
//    }
//
//    public void setCheckIsSucceed(String checkIsSucceed) {
//        this.checkIsSucceed = checkIsSucceed;
//    }
//
//    public String getActualNum() {
//        return actualNum;
//    }
//
//    public void setActualNum(String actualNum) {
//        this.actualNum = actualNum;
//    }
//
//    public String getUpLoadIsSucceed() {
//        return upLoadIsSucceed;
//    }
//
//    public void setUpLoadIsSucceed(String upLoadIsSucceed) {
//        this.upLoadIsSucceed = upLoadIsSucceed;
//    }
//
//    @Override
//    public String toString() {
//        return "AssetsCheck{" +
////                "assetID='" + assetID + '\'' +
////                ", code='" + code + '\'' +
////                ", name='" + name + '\'' +
////                ", model='" + model + '\'' +
////                ", cateCode='" + cateCode + '\'' +
////                ", cateName='" + cateName + '\'' +
////                ", price='" + price + '\'' +
////                ", unit1='" + unit1 + '\'' +
////                ", number1='" + number1 + '\'' +
////                ", unit2='" + unit2 + '\'' +
////                ", number2='" + number2 + '\'' +
////                ", department='" + department + '\'' +
////                ", controller='" + controller + '\'' +
////                ", places='" + places + '\'' +
////                ", provider='" + provider + '\'' +
////                ", validity='" + validity + '\'' +
////                ", maxBuy='" + maxBuy + '\'' +
////                ", minBuy='" + minBuy + '\'' +
////                ", maintenancePeriod='" + maintenancePeriod + '\'' +
////                ", recentMaintain='" + recentMaintain + '\'' +
////                ", maintainResult='" + maintainResult + '\'' +
////                ", storageID='" + storageID + '\'' +
////                ", storageName='" + storageName + '\'' +
////                ", codeType='" + codeType + '\'' +
////                ", state='" + state + '\'' +
////                ", explain='" + explain + '\'' +
//                ", checkIsSucceed='" + checkIsSucceed + '\'' +
//                ", actualNum='" + actualNum + '\'' +
//                ", upLoadIsSucceed='" + upLoadIsSucceed + '\'' +
//                '}';
//    }
//
//
//}
