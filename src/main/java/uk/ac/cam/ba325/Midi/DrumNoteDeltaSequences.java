package uk.ac.cam.ba325.Midi;

/**
 * Created by root on 07/03/16.
 */
public class DrumNoteDeltaSequences {

    private NoteDeltaSequence bassDrum;
    private NoteDeltaSequence crashCymbal;
    private NoteDeltaSequence floorTom;
    private NoteDeltaSequence highHat;
    private NoteDeltaSequence highTom;
    private NoteDeltaSequence lowTom;
    private NoteDeltaSequence rideCymbal;
    private NoteDeltaSequence snareDrum;

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
