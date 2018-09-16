package kld.com.huizhan.bean.upload.output;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;

/**
 * Created by LDW10000000 on 29/11/2017.
 */

public class OutPutUpLoadList extends UpLoadBaseBean implements Serializable {

    private String BillPerson;
    private String BillDate;
    private String UserDepartment;
    private String UserPerson;
    private String Useful;
    private String NeedReturn;

    private String BillNo;

    private List<OutPutUpLoad> Assets = new ArrayList<>();

    //归还日期 20180730 添加的
    private String PredictReturnDate;



    public OutPutUpLoadList() {
    }

//    public Long getId(){
//        return  getBaseObjId();
//    }


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

    public String getUserDepartment() {
        return UserDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        UserDepartment = userDepartment;
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

    public String getNeedReturn() {
        return NeedReturn;
    }

    public void setNeedReturn(String needReturn) {
        NeedReturn = needReturn;
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    public List<OutPutUpLoad> getAssets() {
        return Assets;
    }

    public void setAssets(List<OutPutUpLoad> assets) {
        Assets = assets;
    }

    public String getPredictReturnDate() {
        return PredictReturnDate;
    }

    public void setPredictReturnDate(String predictReturnDate) {
        PredictReturnDate = predictReturnDate;
    }

    @Override
    public String toString() {
        return "OutPutUpLoadList{" +
                "BillPerson='" + BillPerson + '\'' +
                ", BillDate='" + BillDate + '\'' +
                ", UserDepartment='" + UserDepartment + '\'' +
                ", UserPerson='" + UserPerson + '\'' +
                ", Useful='" + Useful + '\'' +
                ", NeedReturn='" + NeedReturn + '\'' +
                ", BillNo='" + BillNo + '\'' +
                ", Assets=" + Assets +
                ", PredictReturnDate='" + PredictReturnDate + '\'' +
                '}';
    }
}
