package kld.com.huizhan.bean.download.user;

import com.google.gson.annotations.SerializedName;

import kld.com.huizhan.bean.base.BaseBean;

/**
 * Created by LDW10000000 on 22/11/2017.
 */

public class User extends BaseBean {

    @SerializedName(value = "uID", alternate = {"UserID"}) //"UserID": "26",
    private String uID;
    @SerializedName(value = "companyID", alternate = {"SiteID"})//"SiteID": "1",
    private String companyID;
    @SerializedName(value = "companyName", alternate = {"SiteName"}) //"SiteName": "中国航展（珠海）会展集团有限公司",
    private String companyName;
    @SerializedName(value = "sectionID", alternate = {"GroupID"}) //"GroupID": "592",
    private String sectionID;
    @SerializedName(value = "sectionCode", alternate = {"GroupCode"}) //"GroupCode": "010106",
    private String sectionCode;
    @SerializedName(value = "sectionName", alternate = {"GroupName"}) // "GroupName": "综合部",
    private String sectionName;
    @SerializedName(value = "uCode", alternate = {"UserCode"}) //  "UserCode": "002",
    private String uCode;
    @SerializedName(value = "lName", alternate = {"LoginName"}) //"LoginName": "fa05",
    private String lName;
    @SerializedName(value = "uName", alternate = {"UserName"}) //"UserName": "fa05",
    private String uName;
    @SerializedName(value = "password", alternate = {"LoginPassword"}) //"LoginPassword": "dd443639f7b86d210352a769d340c896",
    private String password;
    @SerializedName(value = "e_mail", alternate = {"Email"}) // "Email": "",
    private String e_mail;
    @SerializedName(value = "telephone", alternate = {"Tel"}) //"Tel": "1",
    private String telephone;
    @SerializedName(value = "address", alternate = {"Adrr"}) // "Adrr": "",
    private String address;
    @SerializedName(value = "enable", alternate = {"Enabled"}) //   "Enabled": "True",
    private String enable;
    @SerializedName(value = "explain", alternate = {"Remark"}) // "Remark": ""
    private String explain;


    @SerializedName(value = "storageID", alternate = {"StockID"}) //"StockID": "1",
    private String storageID;

    @SerializedName(value = "storageName", alternate = {"StockName"}) //"StockName": "资产仓",
    private String storageName;


   //LDW添加,作为标志位
   private String fill ;//1是true；0是false

    public User() {
    }


    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getuCode() {
        return uCode;
    }

    public void setuCode(String uCode) {
        this.uCode = uCode;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
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

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    @Override
    public String toString() {
        return "User{" +
                "uID='" + uID + '\'' +
                ", companyID='" + companyID + '\'' +
                ", companyName='" + companyName + '\'' +
                ", sectionID='" + sectionID + '\'' +
                ", sectionCode='" + sectionCode + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", uCode='" + uCode + '\'' +
                ", lName='" + lName + '\'' +
                ", uName='" + uName + '\'' +
                ", password='" + password + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", enable='" + enable + '\'' +
                ", explain='" + explain + '\'' +
                ", storageID='" + storageID + '\'' +
                ", storageName='" + storageName + '\'' +
                ", fill='" + fill + '\'' +
                '}';
    }
}
