package kld.com.huizhan.bean.upload.check.local;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;

/**
 * Created by LDW10000000 on 13/12/2017.
 */

public class UploadCheckList extends UpLoadBaseBean {

    private List<UploadCheck> data = new ArrayList<>();

    public UploadCheckList() {
    }

    public List<UploadCheck> getData() {
        return data;
    }

    public void setData(List<UploadCheck> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UploadCheckList{" +
                "data=" + data +
                '}';
    }
}
