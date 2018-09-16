package kld.com.huizhan.bean.upload.check.local;

import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;

/**
 * Created by LDW10000000 on 08/12/2017.
 */

/*** 这个我就不修改了, 因为涉及到的东西比较多,上传的时候是用UpLoadIsCheck , 这个作为本地数据库的保留*/
public class UploadCheck  extends UpLoadBaseBean {

    private String AssetsID; // 这里不要修改, 修改了还要修改DataFunctionUtil
    private String Num;   //这里修改了的话, DataFunctionUtil也要修改
    private String Person;
    private String Date;
    private String Result;

    /**我自己定义的字段*/
    private String upLoadIsSucceed = "未提交";

    public UploadCheck() {
    }

    public String getAssetsID() {
        return AssetsID;
    }

    public void setAssetsID(String assetsID) {
        AssetsID = assetsID;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getPerson() {
        return Person;
    }

    public void setPerson(String person) {
        Person = person;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getUpLoadIsSucceed() {
        return upLoadIsSucceed;
    }

    public void setUpLoadIsSucceed(String upLoadIsSucceed) {
        this.upLoadIsSucceed = upLoadIsSucceed;
    }

    @Override
    public String toString() {
        return "UploadCheck{" +
                "AssetsID='" + AssetsID + '\'' +
                ", Num='" + Num + '\'' +
                ", Person='" + Person + '\'' +
                ", Date='" + Date + '\'' +
                ", Result='" + Result + '\'' +
                ", upLoadIsSucceed='" + upLoadIsSucceed + '\'' +
                '}';
    }
}
