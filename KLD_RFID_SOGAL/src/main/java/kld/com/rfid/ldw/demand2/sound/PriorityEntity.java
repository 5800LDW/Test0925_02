package kld.com.rfid.ldw.demand2.sound;

import android.support.annotation.NonNull;

/**
 * Created by liudongwen on 2018/9/10.
 */

public class PriorityEntity implements Comparable<PriorityEntity>{

    private int lineUp;

    private int soundID;

    public int getLineUp() {
        return lineUp;
    }

    public void setLineUp(int lineUp) {
        this.lineUp = lineUp;
    }

    public int getSoundID() {
        return soundID;
    }

    public void setSoundID(int soundID) {
        this.soundID = soundID;
    }

    @Override
    public String toString() {
        return "PriorityEntity{" +
                "lineUp=" + lineUp +
                ", soundID=" + soundID +
                '}';
    }

    @Override
    public int compareTo(@NonNull PriorityEntity o) {
        return  this.lineUp > o.lineUp ? 1 : this.lineUp < o.lineUp ? -1 : 0;
    }
}
