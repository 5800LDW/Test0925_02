package kld.com.huizhan.bean.download.user;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.base.BaseBean;

/**
 * Created by LDW10000000 on 22/11/2017.
 */

public class UserList extends BaseBean {

    private List<User> data = new ArrayList<>();

    public UserList() {
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserList{" +
                "data=" + data +
                '}';
    }
}
