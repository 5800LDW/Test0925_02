package kld.com.rfid.ldw.bean.orginfo;

/**
 * Created by liudongwen on 2018/9/4.
 */

public class ORGANIZATION {

    private String ORGANIZATION_ID;

    private String ORGANIZATION_NAME;

    public String getORGANIZATION_ID() {
        return ORGANIZATION_ID;
    }

    public void setORGANIZATION_ID(String ORGANIZATION_ID) {
        this.ORGANIZATION_ID = ORGANIZATION_ID;
    }

    public String getORGANIZATION_NAME() {
        return ORGANIZATION_NAME;
    }

    public void setORGANIZATION_NAME(String ORGANIZATION_NAME) {
        this.ORGANIZATION_NAME = ORGANIZATION_NAME;
    }

    @Override
    public String toString() {
        return "ORGANIZATION{" +
                "ORGANIZATION_ID='" + ORGANIZATION_ID + '\'' +
                ", ORGANIZATION_NAME='" + ORGANIZATION_NAME + '\'' +
                '}';
    }
}
