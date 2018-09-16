package kld.com.huizhan.bean.download.supplier;

import com.google.gson.annotations.SerializedName;

import kld.com.huizhan.bean.base.BaseBean;

/**
 * Created by LDW10000000 on 08/12/2017.
 */

public class Supplier extends BaseBean {

    @SerializedName(value = "sID", alternate = {"SupplierID"}) //"StockID": "1",
    private String sID;
    @SerializedName(value = "sNo", alternate = {"SupplierNo"}) //"StockID": "1",
    private String sNo;
    @SerializedName(value = "name", alternate = {"SupplierName"}) //"StockID": "1",
    private String name;

    public Supplier() {
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "sID='" + sID + '\'' +
                ", sNo='" + sNo + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    //      "SupplierID": "1",
//              "SupplierNo": "0001",
//              "SupplierName": "广东康利达物联科技股份有限公司"
}
