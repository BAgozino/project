package uk.ac.cam.ba325.Midi.Quantisation;

import uk.ac.cam.ba325.Midi.DrumNoteDeltaSequences;
import uk.ac.cam.ba325.Midi.TickDelta;
import uk.ac.cam.ba325.Tab.Translation.Sequence;
import uk.ac.cam.ba325.Tab.Translation.Strike;

/**
 * Created by root on 05/04/16.
 */
public class DrumNoteDeltaSuffixTree {

    private UkkonenSuffixTree bassDrum;
    private UkkonenSuffixTree crashCymbal;
    private UkkonenSuffixTree floorTom;
    private UkkonenSuffixTree highHat;
    private UkkonenSuffixTree highTom;
    private UkkonenSuffixTree lowTom;
    private UkkonenSuffixTree rideCymbal;
    private UkkonenSuffixTree snareDrum;
    private long length;
    private UkkonenSuffixTree[] drums = new UkkonenSuffixTree[8];

    private int RESOLUTION = 16;

    public Sequence getBestSequence(){

        //find best Repeat
        RepeatedStructure bestRepeat = bassDrum.getRepeatedStructure();
        long perfectLength = length/10;
        long distanceFromPerfect = Math.abs(bestRepeat.getLength() -length/10);

        int bestRepeatIndex = 0;
        long bestRepeatLength=drums[0].getRepeatedStructure().getLength();
        UkkonenSuffixTree drum;
        RepeatedStructure tempRepeat;
        for(int i=1; i<drums.length;i++){
            drum = drums[i];

            tempRepeat = drum.getRepeatedStructure();
            if(Math.abs(bestRepeatLength-perfectLength)
                    >Math.abs(tempRepeat.getLength()-perfectLength)){
                bestRepeatIndex = i;
                bestRepeatLength = tempRepeat.getLength();
            }
        }

        int endIndex = drums[bestRepeatIndex].getRepeatedStructure().getEndTickIndex();

        long endTime = drums[bestRepeatIndex].m_sequence.get(endIndex).getEndTime();

        long startTime = endTime-bestRepeatLength;



        //Creating Sequence from endTime and startTime;
        long[] quanta = new long[RESOLUTION];
        for(int i = 0; i<RESOLUTION; i++){
            quanta[i] = startTime+ i*(endTime-startTime)/RESOLUTION;
        }

        Strike[] strikes = new Strike[RESOLUTION];
        Sequence pattern = new Sequence(RESOLUTION);

        for(int j = 0; j<drums.length; j++){
            UkkonenSuffixTree instrument = drums[j];
            for(int i=0; i<strikes.length;i++){//reset strikes
                strikes[i] = new Strike(0);
            }

            for(TickDelta tick : instrument.m_sequence){
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


    public DrumNoteDeltaSuffixTree(DrumNoteDeltaSequences sequences){
        bassDrum = new UkkonenSuffixTree(sequences.getBassDrum());
        crashCymbal = new UkkonenSuffixTree(sequences.getCrashCymbal());
        floorTom = new UkkonenSuffixTree(sequences.getFloorTom());
        highHat = new UkkonenSuffixTree(sequences.getHighHat());
        highTom = new UkkonenSuffixTree(sequences.getHighTom());
        lowTom = new UkkonenSuffixTree(sequences.getLowTom());
        rideCymbal = new UkkonenSuffixTree(sequences.getRideCymbal());
        snareDrum = new UkkonenSuffixTree(sequences.getSnareDrum());
        length = sequences.getLength();
        drums[0] = highHat;
        drums[1] = bassDrum;
        drums[2] = snareDrum;
        drums[3] = floorTom;
        drums[4] = lowTom;
        drums[5] = highTom;
        drums[6] = crashCymbal;
        drums[7] = rideCymbal;


    }

    public UkkonenSuffixTree getBassDrum() {
        return bassDrum;
    }

    public void setBassDrum(UkkonenSuffixTree bassDrum) {
        this.bassDrum = bassDrum;
    }

    public UkkonenSuffixTree getCrashCymbal() {
        return crashCymbal;
    }

    public void setCrashCymbal(UkkonenSuffixTree crashCymbal) {
        this.crashCymbal = crashCymbal;
    }

    public UkkonenSuffixTree getFloorTom() {
        return floorTom;
    }

    public void setFloorTom(UkkonenSuffixTree floorTom) {
        this.floorTom = floorTom;
    }

    public UkkonenSuffixTree getHighHat() {
        return highHat;
    }

    public void setHighHat(UkkonenSuffixTree highHat) {
        this.highHat = highHat;
    }

    public UkkonenSuffixTree getHighTom() {
        return highTom;
    }

    public void setHighTom(UkkonenSuffixTree highTom) {
        this.highTom = highTom;
    }

    public UkkonenSuffixTree getLowTom() {
        return lowTom;
    }

    public void setLowTom(UkkonenSuffixTree lowTom) {
        this.lowTom = lowTom;
    }

    public UkkonenSuffixTree getRideCymbal() {
        return rideCymbal;
    }

    public void setRideCymbal(UkkonenSuffixTree rideCymbal) {
        this.rideCymbal = rideCymbal;
    }

    public UkkonenSuffixTree getSnareDrum() {
        return snareDrum;
    }

    public void setSnareDrum(UkkonenSuffixTree snareDrum) {
        this.snareDrum = snareDrum;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
