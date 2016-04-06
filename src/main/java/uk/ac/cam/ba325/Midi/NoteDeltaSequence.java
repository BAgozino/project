package uk.ac.cam.ba325.Midi;

import java.util.ArrayList;
import java.util.List;


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
            if(TickDelta.roundTick(tick) != timeOfLastTick) {


                TickDelta t = new TickDelta(tick - timeOfLastTick);
                timeOfLastTick = tick;
                this.add(t);
            }
        }
    }


    /**
     *
     * @param start inclusive
     * @param end
     * @return New list so changing it may not change the original sequence
     */
    @Override
    public NoteDeltaSequence subList(int start, int end){
        NoteDeltaSequence retval = new NoteDeltaSequence();

        for(int i =start; i<end;i++){
            retval.add(this.get(i));
        }
        return retval;
    }

    public void printSubList(int start, int end){
        StringBuilder sb = new StringBuilder();
        for(TickDelta tickDelta:this.subList(start,end)){
            sb.append(tickDelta.getdTick());
        }
        System.out.print(sb);
    }

}
