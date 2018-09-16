package kld.com.rfid.ldw.bean.channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudongwen on 2018/9/4.
 */

public class ChannelInfo {

    private String success;
    private String msg;
    private List<ChannelInfoListBean> data = new ArrayList<>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ChannelInfoListBean> getData() {
        return data;
    }

    public void setData(List<ChannelInfoListBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OrgInfo{" +
                "success='" + success + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
