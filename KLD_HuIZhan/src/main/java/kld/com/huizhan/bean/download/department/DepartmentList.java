package kld.com.huizhan.bean.download.department;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.base.BaseBean;

/**
 * Created by LDW10000000 on 22/11/2017.
 */

public class DepartmentList extends BaseBean {

    private List<Department> data = new ArrayList<>();

    public DepartmentList() {
    }

    public List<Department> getData() {
        return data;
    }

    public void setData(List<Department> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DepartmentList{" +
                "data=" + data +
                '}';
    }
}
