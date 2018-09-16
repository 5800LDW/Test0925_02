package kld.com.huizhan.bean.download.supplier;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.base.BaseBean;

/**
 * Created by LDW10000000 on 08/12/2017.
 */

public class SupplierList extends BaseBean {

    private List<Supplier> data = new ArrayList<>();

    public SupplierList() {
    }

    public List<Supplier> getData() {
        return data;
    }

    public void setData(List<Supplier> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SupplierList{" +
                "data=" + data +
                '}';
    }
}
