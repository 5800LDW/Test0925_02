package com.example.android.api24adaptation;

import java.io.Serializable;

/**
 * Created by Serena on 2016-01-06.
 * description:图片信息
 */
public class Pictrue implements Serializable {
    private String picId;
    private String url;
    private boolean isCanDelete;//标示图片是否可以被删除


    public Pictrue(String picid, String url, boolean isCanDelete) {
        this.picId = picid;
        this.url = url;
        this.isCanDelete=isCanDelete;
    }

    public boolean isCanDelete() {
        return isCanDelete;
    }

    public void setIsCanDelete(boolean isCanDelete) {
        this.isCanDelete = isCanDelete;
    }


    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
