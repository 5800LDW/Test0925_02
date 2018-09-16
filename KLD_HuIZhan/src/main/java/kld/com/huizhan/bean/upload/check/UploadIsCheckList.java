package kld.com.huizhan.bean.upload.check;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;

/**
 * Created by LDW10000000 on 08/12/2017.
 */

/*** 这个我就不修改了, 因为涉及到的东西比较多,上传的时候是用UpLoadIsCheck , 这个作为本地数据库的保留*/
public class UploadIsCheckList extends UpLoadBaseBean {

    private List<UpLoadIsCheck> data = new ArrayList<>();

    public UploadIsCheckList() {
    }

    public List<UpLoadIsCheck> getData() {
        return data;
    }

    public void setData(List<UpLoadIsCheck> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UploadIsCheckList{" +
                "data=" + data +
                '}';
    }
}
