package uk.ac.cam.ba325.Tab.Instrument;

/**
 * Created by biko on 19/11/15.
 */
public abstract class Instrument {

    private int time;
    private String hitType;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getHitType() {
        return hitType;
    }

    public void setHitType(String hitType) {
        this.hitType = hitType;
    }
}
