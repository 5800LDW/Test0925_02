package kld.com.huizhan.bean.upload.giveback;

import java.util.ArrayList;
import java.util.List;

import kld.com.huizhan.bean.upload.base.UpLoadBaseBean;

/**
 * Created by LDW10000000 on 04/12/2017.
 */

public class UploadGiveBack extends UpLoadBaseBean {


    private String OutDepotID;
    private String ReturnPerson;
    private String ReturnDate;

    private List<UploadGiveBackAssets> Assets = new ArrayList<>();

    public UploadGiveBack() {
    }

    public String getOutDepotID() {
        return OutDepotID;
    }

    public void setOutDepotID(String outDepotID) {
        OutDepotID = outDepotID;
    }

    public String getReturnPerson() {
        return ReturnPerson;
    }

    public void setReturnPerson(String returnPerson) {
        ReturnPerson = returnPerson;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }

    public List<UploadGiveBackAssets> getAssets() {
        return Assets;
    }

    public void setAssets(List<UploadGiveBackAssets> assets) {
        Assets = assets;
    }
}
