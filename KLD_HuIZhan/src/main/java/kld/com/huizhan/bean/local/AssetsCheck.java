package kld.com.huizhan.bean.local;

import kld.com.huizhan.bean.download.assets.Assets;

/**
 * Created by LDW10000000 on 11/12/2017.
 */

public class AssetsCheck extends Assets {


    private String checkIsSucceed = "未盘"; //"已盘

    private String actualNum = "1";

    private String upLoadIsSucceed = "未上传";

    public AssetsCheck() {
    }

    public String getCheckIsSucceed() {
        return checkIsSucceed;
    }

    public void setCheckIsSucceed(String checkIsSucceed) {
        this.checkIsSucceed = checkIsSucceed;
    }

    public String getActualNum() {
        return actualNum;
    }

    public void setActualNum(String actualNum) {
        this.actualNum = actualNum;
    }

    public String getUpLoadIsSucceed() {
        return upLoadIsSucceed;
    }

    public void setUpLoadIsSucceed(String upLoadIsSucceed) {
        this.upLoadIsSucceed = upLoadIsSucceed;
    }

    @Override
    public String toString() {
        return "AssetsCheck{" +
                "checkIsSucceed='" + checkIsSucceed + '\'' +
                ", actualNum='" + actualNum + '\'' +
                ", upLoadIsSucceed='" + upLoadIsSucceed + '\'' +
                '}';
    }
}
