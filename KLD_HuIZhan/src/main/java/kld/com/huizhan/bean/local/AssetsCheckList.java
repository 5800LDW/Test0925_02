package kld.com.huizhan.bean.local;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;

/**
 * Created by LDW10000000 on 11/12/2017.
 */

public class AssetsCheckList extends UpLoadBaseBean {


    private List<AssetsCheck> data = new ArrayList<>();

    public AssetsCheckList() {
    }

    public List<AssetsCheck> getData() {
        return data;
    }

    public void setData(List<AssetsCheck> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AssetsCheckList{" +
                "data=" + data +
                '}';
    }
}
