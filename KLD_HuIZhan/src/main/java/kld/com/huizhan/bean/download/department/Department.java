package kld.com.huizhan.bean.download.department;

import com.google.gson.annotations.SerializedName;

import kld.com.huizhan.bean.base.BaseBean;

/**
 * Created by LDW10000000 on 22/11/2017.
 */

public class Department extends BaseBean {

    @SerializedName(value = "sectionID", alternate = {"GroupID"}) //"GroupID": "587",
    private String sectionID;

    @SerializedName(value = "companyID", alternate = {"SiteID"}) //"SiteID": "",
    private String companyID;

    @SerializedName(value = "sectionCode", alternate = {"GroupCode"}) //"GroupCode": "01",
    private String sectionCode;

    @SerializedName(value = "sectionName", alternate = {"GroupName"}) //"GroupName": "中国航展",
    private String sectionName;

    @SerializedName(value = "linkMan", alternate = {"ContactMan"}) // "ContactMan": "",
    private String linkMan;

    @SerializedName(value = "phone", alternate = {"ContactTel"}) //"ContactTel": "",
    private String phone;

    @SerializedName(value = "duty", alternate = {"Responsibility"}) //"Responsibility": "",
    private String duty;

    @SerializedName(value = "pID", alternate = {"ParentID"}) // "ParentID": "",
    private String pID;

    @SerializedName(value = "f_ID", alternate = {"FID"}) //"FID": "00000000-0000-0000-0000-000000000000CCE7AED4",
    private String f_ID;

    @SerializedName(value = "explain", alternate = {"Remark"}) // "Remark": ""
    private String explain;

    public Department() {
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
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

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getF_ID() {
        return f_ID;
    }

    public void setF_ID(String f_ID) {
        this.f_ID = f_ID;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    @Override
    public String toString() {
        return "Department{" +
                "sectionID='" + sectionID + '\'' +
                ", companyID='" + companyID + '\'' +
                ", sectionCode='" + sectionCode + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", linkMan='" + linkMan + '\'' +
                ", phone='" + phone + '\'' +
                ", duty='" + duty + '\'' +
                ", pID='" + pID + '\'' +
                ", f_ID='" + f_ID + '\'' +
                ", explain='" + explain + '\'' +
                '}';
    }

    //
//
//
//
//                        "Remark": ""


}
