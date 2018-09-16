package kld.com.huizhan.bean.upload.giveback;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;

/**
 * Created by LDW10000000 on 04/12/2017.
 */

public class UploadGiveBackList extends UpLoadBaseBean {


    private List<UploadGiveBack> data = new ArrayList<>();

    public UploadGiveBackList() {
    }

    public List<UploadGiveBack> getData() {
        return data;
    }

    public void setData(List<UploadGiveBack> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UploadGiveBackList{" +
                "data=" + data +
                '}';
    }
}
