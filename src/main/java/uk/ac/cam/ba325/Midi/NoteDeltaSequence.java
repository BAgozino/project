package uk.ac.cam.ba325.Midi;

import java.util.ArrayList;


/**
 * Created by root on 08/02/16.
 */
public class NoteDeltaSequence extends ArrayList<TickDelta> {


    private long timeOfLastTick = 0;



    public boolean prefixTest(int position, TickDelta tickDelta){

        return this.get(position).getdTickRounded() ==tickDelta.getdTickRounded();
    }

    public void addTick(long tick){
        if (timeOfLastTick == 0){
            timeOfLastTick = tick;
        } else {
            TickDelta t = new TickDelta(tick-timeOfLastTick);
            timeOfLastTick = tick;
            this.add(t);
        }
    }

}
