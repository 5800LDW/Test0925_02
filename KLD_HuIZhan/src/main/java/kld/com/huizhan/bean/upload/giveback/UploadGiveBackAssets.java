package kld.com.huizhan.bean.upload.giveback;

import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;

/**
 * Created by LDW10000000 on 04/12/2017.
 */

public class UploadGiveBackAssets extends UpLoadBaseBean {

    private String AssetsID;
    private String ReturnCheck;

    //todo
    private String ReturnNum;

    private UploadGiveBack uploadGiveBack;

    public UploadGiveBackAssets() {
    }

    public String getAssetsID() {
        return AssetsID;
    }

    public void setAssetsID(String assetsID) {
        AssetsID = assetsID;
    }

    public String getReturnCheck() {
        return ReturnCheck;
    }

    public void setReturnCheck(String returnCheck) {
        ReturnCheck = returnCheck;
    }

    public String getReturnNum() {
        return ReturnNum;
    }

    public void setReturnNum(String returnNum) {
        ReturnNum = returnNum;
    }

    public UploadGiveBack getUploadGiveBack() {
        return uploadGiveBack;
    }

    public void setUploadGiveBack(UploadGiveBack uploadGiveBack) {
        this.uploadGiveBack = uploadGiveBack;
    }

    @Override
    public String toString() {
        return "UploadGiveBackAssets{" +
                "AssetsID='" + AssetsID + '\'' +
                ", ReturnCheck='" + ReturnCheck + '\'' +
                ", ReturnNum='" + ReturnNum + '\'' +
                ", uploadGiveBack=" + uploadGiveBack +
                '}';
    }
}
