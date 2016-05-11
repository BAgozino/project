package uk.ac.cam.ba325.Midi;

import uk.ac.cam.ba325.Tab.Translation.Sequence;
import uk.ac.cam.ba325.Tab.Translation.Strike;

/**
 * Created by root on 07/03/16.
 */
public class DrumNoteDeltaSequences {
    private static int RESOLUTION = 16;
    private NoteDeltaSequence bassDrum;
    private NoteDeltaSequence crashCymbal;
    private NoteDeltaSequence floorTom;
    private NoteDeltaSequence highHat;
    private NoteDeltaSequence highTom;
    private NoteDeltaSequence lowTom;
    private NoteDeltaSequence rideCymbal;
    private NoteDeltaSequence snareDrum;
    private NoteDeltaSequence[] drums = new NoteDeltaSequence[8];
    private long length;

    public DrumNoteDeltaSequences(long length){
        this.length = length;
        bassDrum = new NoteDeltaSequence(length);
        crashCymbal = new NoteDeltaSequence(length);
        floorTom = new NoteDeltaSequence(length);
        highHat = new NoteDeltaSequence(length);
        highTom = new NoteDeltaSequence(length);
        lowTom = new NoteDeltaSequence(length);
        rideCymbal = new NoteDeltaSequence(length);
        snareDrum = new NoteDeltaSequence(length);
        drums[0] = highHat;
        drums[1] = bassDrum;
        drums[2] = snareDrum;
        drums[3] = floorTom;
        drums[4] = lowTom;
        drums[5] = highTom;
        drums[6] = crashCymbal;
        drums[7] = rideCymbal;
    }

    public Sequence getSequence(){
        int startTime = 0;
        long endTime = length;
        long[] quanta = new long[RESOLUTION];
        for(int i = 0; i<RESOLUTION; i++){
            quanta[i] = startTime+ i*(endTime-startTime)/RESOLUTION;
        }

        Strike[] strikes = new Strike[RESOLUTION];
        Sequence pattern = new Sequence(RESOLUTION);

        for(int j = 0; j<drums.length; j++){
            NoteDeltaSequence instrument = drums[j];
            for(int i=0; i<strikes.length;i++){//reset strikes
                strikes[i] = new Strike(0);
            }

            for(TickDelta tick : instrument){
                int index = quantaIndex(tick,quanta);
                if(index!=-1){
                    strikes[index].setValue(1);
                }
            }
            pattern.setLine(j,strikes);
        }

        return pattern;
    }

    public int quantaIndex(TickDelta tick, long[] quanta){
        if(tick.getEndTime()<quanta[0] && tick.getEndTime()>quanta[RESOLUTION-1]){
            return -1;
        }else{
            for(int i=0; i<quanta.length; i++){
                if(quanta[i] >tick.getEndTime()){
                    return i-1;
                }
            }
            return 15;
        }
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public NoteDeltaSequence getBassDrum() {
        return bassDrum;
    }

    public void setBassDrum(NoteDeltaSequence bassDrum) {
        this.bassDrum = bassDrum;
    }

    public NoteDeltaSequence getCrashCymbal() {
        return crashCymbal;
    }

    public void setCrashCymbal(NoteDeltaSequence crashCymbal) {
        this.crashCymbal = crashCymbal;
    }

    public NoteDeltaSequence getFloorTom() {
        return floorTom;
    }

    public void setFloorTom(NoteDeltaSequence floorTom) {
        this.floorTom = floorTom;
    }

    public NoteDeltaSequence getHighHat() {
        return highHat;
    }

    public void setHighHat(NoteDeltaSequence highHat) {
        this.highHat = highHat;
    }

    public NoteDeltaSequence getHighTom() {
        return highTom;
    }

    public void setHighTom(NoteDeltaSequence highTom) {
        this.highTom = highTom;
    }

    public NoteDeltaSequence getLowTom() {
        return lowTom;
    }

    public void setLowTom(NoteDeltaSequence lowTom) {
        this.lowTom = lowTom;
    }

    public NoteDeltaSequence getRideCymbal() {
        return rideCymbal;
    }

    public void setRideCymbal(NoteDeltaSequence rideCymbal) {
        this.rideCymbal = rideCymbal;
    }

    public NoteDeltaSequence getSnareDrum() {
        return snareDrum;
    }

    public void setSnareDrum(NoteDeltaSequence snareDrum) {
        this.snareDrum = snareDrum;
    }
}
