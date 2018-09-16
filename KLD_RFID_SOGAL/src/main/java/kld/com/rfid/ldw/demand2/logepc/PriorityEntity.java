package kld.com.rfid.ldw.demand2.logepc;

import android.support.annotation.NonNull;

/**
 * Created by liudongwen on 2018/9/10.
 */

public class PriorityEntity implements Comparable<PriorityEntity>{

    private int lineUp;

    private String epcID;

    public int getLineUp() {
        return lineUp;
    }

    public void setLineUp(int lineUp) {
        this.lineUp = lineUp;
    }

    public String getEpcID() {
        return epcID;
    }

    public void setEpcID(String epcID) {
        this.epcID = epcID;
    }

    @Override
    public String toString() {
        return "PriorityEntity{" +
                "lineUp=" + lineUp +
                ", epcID='" + epcID + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull PriorityEntity o) {
        return  this.lineUp > o.lineUp ? 1 : this.lineUp < o.lineUp ? -1 : 0;
    }
}
