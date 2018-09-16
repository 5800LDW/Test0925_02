package kld.com.huizhan.bean.download.giveback;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.base.BaseBean;

/**
 * Created by LDW10000000 on 04/12/2017.
 */

public class GiveBackBillList extends BaseBean {

    private List<GiveBackBill> data = new ArrayList<>();

    public GiveBackBillList() {
    }

    public List<GiveBackBill> getData() {
        return data;
    }

    public void setData(List<GiveBackBill> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GiveBackBillList{" +
                "data=" + data +
                '}';
    }
}
