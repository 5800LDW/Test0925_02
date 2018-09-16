package kld.com.huizhan.bean.base;

import com.ldw.xyz.bean.Widget;

import org.litepal.crud.DataSupport;

/**
 * Created by LDW10000000 on 22/11/2017.
 */

public class BaseBean extends DataSupport implements Widget {

    private String type = "";
    private String msg = "";


    public BaseBean() {
    }

    public BaseBean(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "type='" + type + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}