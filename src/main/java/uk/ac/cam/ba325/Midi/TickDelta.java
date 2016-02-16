package uk.ac.cam.ba325.Midi;

/**
 * Created by root on 08/02/16.
 */
public class TickDelta {

    public static final int ERROR_RANGE = 20;
    public static final double ROUNDVALUE = 10.0;

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

    public long getdTickRounded(){

        return (Math.round(dTick/ROUNDVALUE)*10);

    }

    public void setdTick(long dTick) {
        this.dTick = dTick;
    }
}
