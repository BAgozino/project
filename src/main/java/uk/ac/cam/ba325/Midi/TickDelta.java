package uk.ac.cam.ba325.Midi;

/**
 * Created by root on 08/02/16.
 */
public class TickDelta {

    public static final int ERROR_RANGE = 20;
    public static double ROUNDVALUE = 5.0;

    private long dTick;
    private long endTime;


    public TickDelta(long dTick, long tick){
        this.dTick = dTick;
        this.endTime = tick;
    }



    public long getEndTime() {
        return endTime;
    }


    public long getdTick() {
        return dTick;
    }

    public long getdTickRounded(){
        if(dTick <0){
            return dTick;
        }
        return (Math.round(dTick/ROUNDVALUE)*((long) ROUNDVALUE));

    }
    public static long roundTick(long tick){
        return (Math.round(tick/ROUNDVALUE)*((long) ROUNDVALUE));
    }

    public void setdTick(long dTick) {
        this.dTick = dTick;
    }
}
