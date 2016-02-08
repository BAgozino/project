package uk.ac.cam.ba325.Midi;

/**
 * Created by root on 08/02/16.
 */
public class TickDelta {

    public static final int ERROR_RANGE = 20;

    private long dTick;


    public TickDelta(long dTick){
        this.dTick = dTick;
    }

    public static boolean deltasEqual(TickDelta td1, TickDelta td2){
        long diff = td1.getdTick()-td2.getdTick();
        if((ERROR_RANGE >= diff)&&(diff >= -ERROR_RANGE)){
            return true;
        }

        return false;
    }

    public long getdTick() {
        return dTick;
    }

    public void setdTick(long dTick) {
        this.dTick = dTick;
    }
}
