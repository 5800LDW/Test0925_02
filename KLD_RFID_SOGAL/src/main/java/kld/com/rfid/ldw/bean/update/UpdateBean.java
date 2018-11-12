package kld.com.rfid.ldw.bean.update;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liudongwen on 2018/10/8.
 */

public class UpdateBean {

//    {
//        "update": "Yes",
//            "new_version": "1",
//            "apk_file_url": "http://10.9.22.148:8089/apk/lamp.apk",
//            "update_log": "1、添加手动更新功能",
//            "target_size": "5M",
//            "new_md5":"b97bea014531123f94c3ba7b7afbaad2",
//            "constraint": false
//    }

    @SerializedName(value = "canUpdate", alternate = {"update"})
    private String canUpdate;

    @SerializedName(value = "version", alternate = {"new_version"})
    private String version;

    @SerializedName(value = "updateUrl", alternate = {"apk_file_url"})
    private String updateUrl;

    @SerializedName(value = "versionInfo", alternate = {"update_log"})
    private String versionInfo;

    @SerializedName(value = "apkSize", alternate = {"target_size"})
    private String apkSize;

    @SerializedName(value = "md5Info", alternate = {"new_md5"})
    private String md5Info;

    @SerializedName(value = "constraintUpdate", alternate = {"constraint"})
    private String constraintUpdate;

    public String getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(String canUpdate) {
        this.canUpdate = canUpdate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public String getApkSize() {
        return apkSize;
    }

    public void setApkSize(String apkSize) {
        this.apkSize = apkSize;
    }

    public String getMd5Info() {
        return md5Info;
    }

    public void setMd5Info(String md5Info) {
        this.md5Info = md5Info;
    }

    public String getConstraintUpdate() {
        return constraintUpdate;
    }

    public void setConstraintUpdate(String constraintUpdate) {
        this.constraintUpdate = constraintUpdate;
    }

    @Override
    public String toString() {
        return "UpdateBean{" +
                "canUpdate='" + canUpdate + '\'' +
                ", version='" + version + '\'' +
                ", updateUrl='" + updateUrl + '\'' +
                ", versionInfo='" + versionInfo + '\'' +
                ", apkSize='" + apkSize + '\'' +
                ", md5Info='" + md5Info + '\'' +
                ", constraintUpdate='" + constraintUpdate + '\'' +
                '}';
    }
}
