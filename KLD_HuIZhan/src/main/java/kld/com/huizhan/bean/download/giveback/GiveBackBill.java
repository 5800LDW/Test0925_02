package kld.com.huizhan.bean.download.giveback;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.base.BaseBean;

/**
 * Created by LDW10000000 on 04/12/2017.
 */

public class GiveBackBill extends BaseBean {

//              "OutDepotID": "15",
//                      "OutDepotNo": "CK000009",
//                      "BillPerson": "fa02      ",
//                      "BillDate": "2017/12/1 0:00:00",
//                      "UseDepartment": "综合部",
//                      "UserPerson": "fa02",
//                      "Useful": "冬季航展会",
//                      "BillState": "审核通过",

    private String OutDepotID ;
    private String OutDepotNo ;
    private String BillPerson ;
    private String BillDate ;
    private String UseDepartment ;
    private String UserPerson ;
    private String Useful ;
    private String BillState ;

    private String ReturnDate;
    private String ReturnPerson;


    private List<GiveBackAssets> Assets = new ArrayList<>();

    public GiveBackBill() {
    }

    public String getOutDepotID() {
        return OutDepotID;
    }

    public void setOutDepotID(String outDepotID) {
        OutDepotID = outDepotID;
    }

    public String getOutDepotNo() {
        return OutDepotNo;
    }

    public void setOutDepotNo(String outDepotNo) {
        OutDepotNo = outDepotNo;
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

    public String getUseDepartment() {
        return UseDepartment;
    }

    public void setUseDepartment(String useDepartment) {
        UseDepartment = useDepartment;
    }

    public String getUserPerson() {
        return UserPerson;
    }

    public void setUserPerson(String userPerson) {
        UserPerson = userPerson;
    }

    public String getUseful() {
        return Useful;
    }

    public void setUseful(String useful) {
        Useful = useful;
    }

    public String getBillState() {
        return BillState;
    }

    public void setBillState(String billState) {
        BillState = billState;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }

    public String getReturnPerson() {
        return ReturnPerson;
    }

    public void setReturnPerson(String returnPerson) {
        ReturnPerson = returnPerson;
    }

    public List<GiveBackAssets> getAssets() {
        return Assets;
    }

    public void setAssets(List<GiveBackAssets> assets) {
        Assets = assets;
    }

    @Override
    public String toString() {
        return "GiveBackBill{" +
                "OutDepotID='" + OutDepotID + '\'' +
                ", OutDepotNo='" + OutDepotNo + '\'' +
                ", BillPerson='" + BillPerson + '\'' +
                ", BillDate='" + BillDate + '\'' +
                ", UseDepartment='" + UseDepartment + '\'' +
                ", UserPerson='" + UserPerson + '\'' +
                ", Useful='" + Useful + '\'' +
                ", BillState='" + BillState + '\'' +
                ", ReturnDate='" + ReturnDate + '\'' +
                ", ReturnPerson='" + ReturnPerson + '\'' +
                ", Assets=" + Assets +
                '}';
    }
}
