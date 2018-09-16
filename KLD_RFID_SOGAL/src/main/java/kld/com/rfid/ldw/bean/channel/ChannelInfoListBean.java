package kld.com.rfid.ldw.bean.channel;

/**
 * Created by liudongwen on 2018/9/4.
 */

public class ChannelInfoListBean {

    private String STAGE_NMAE;
    private String STAGE_CODE;
    private String ORG_ID;


    public String getSTAGE_NMAE() {
        return STAGE_NMAE;
    }

    public void setSTAGE_NMAE(String STAGE_NMAE) {
        this.STAGE_NMAE = STAGE_NMAE;
    }

    public String getSTAGE_CODE() {
        return STAGE_CODE;
    }

    public void setSTAGE_CODE(String STAGE_CODE) {
        this.STAGE_CODE = STAGE_CODE;
    }

    public String getORG_ID() {
        return ORG_ID;
    }

    public void setORG_ID(String ORG_ID) {
        this.ORG_ID = ORG_ID;
    }

    @Override
    public String toString() {
        return "OrgInfoListBean{" +
                "STAGE_NMAE='" + STAGE_NMAE + '\'' +
                ", STAGE_CODE='" + STAGE_CODE + '\'' +
                ", ORG_ID='" + ORG_ID + '\'' +
                '}';
    }
}
