package kld.com.huizhan.bean.upload.output;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;

/**
 * Created by LDW10000000 on 30/11/2017.
 */

public class OPUL extends UpLoadBaseBean {

    private List<OutPutUpLoadList> data = new ArrayList<>();

    public OPUL() {
    }

    public List<OutPutUpLoadList> getData() {
        return data;
    }

    public void setData(List<OutPutUpLoadList> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OPUL{" +
                "data=" + data +
                '}';
    }
}
