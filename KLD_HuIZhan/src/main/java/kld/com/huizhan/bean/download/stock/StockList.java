package kld.com.huizhan.bean.download.stock;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.base.BaseBean;

/**
 * Created by LDW10000000 on 22/11/2017.
 */

public class StockList extends BaseBean {

    private List<Stock> data = new ArrayList<>();

    public StockList() {
    }

    public List<Stock> getData() {
        return data;
    }

    public void setData(List<Stock> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StockList{" +
                "data=" + data +
                '}';
    }
}
