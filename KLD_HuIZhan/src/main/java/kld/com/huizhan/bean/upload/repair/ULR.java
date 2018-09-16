package kld.com.huizhan.bean.upload.repair;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;

/**
 * Created by LDW10000000 on 06/12/2017.
 */

public class ULR extends UpLoadBaseBean {

    private List<UpLoadRepair> data = new ArrayList<>();

    public ULR() {
    }

    public List<UpLoadRepair> getData() {
        return data;
    }

    public void setData(List<UpLoadRepair> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ULR{" +
                "data=" + data +
                '}';
    }
}
