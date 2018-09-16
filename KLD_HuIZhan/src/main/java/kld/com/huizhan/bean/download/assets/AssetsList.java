package kld.com.huizhan.bean.download.assets;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.base.BaseBean;

/**
 * Created by LDW10000000 on 22/11/2017.
 */

public class AssetsList extends BaseBean {

    private List<Assets> data = new ArrayList<>();

    public AssetsList() {
    }

    public List<Assets> getData() {
        return data;
    }

    public void setData(List<Assets> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AssetsList{" +
                "data=" + data +
                '}';
    }
}
