package kld.com.huizhan.bean.upload.putin;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;

/**
 * Created by LDW10000000 on 27/11/2017.
 */

public class UploadList extends UpLoadBaseBean {

    private List<Upload> data = new ArrayList<>();

    public UploadList() {
    }

    public List<Upload> getData() {
        return data;
    }

    public void setData(List<Upload> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UploadList{" +
                "data=" + data +
                '}';
    }
}
