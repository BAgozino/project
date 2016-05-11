package uk.ac.cam.ba325.Midi.Quantisation;

/**
 * Created by root on 11/04/16.
 */
public class RepeatedStructure {
    private int endTickIndex;
    private long length;
    private int numberOfChildren;


    public RepeatedStructure(int endTickIndex, long length, int numberOfchildren) {
        this.endTickIndex = endTickIndex;
        this.length = length;
        this.numberOfChildren = numberOfchildren;

    }





    public void setStructure(int endTick, long length, int numberOfchildren) {
        this.endTickIndex = endTick;
        this.length = length;
        this.numberOfChildren = numberOfchildren;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public int getEndTickIndex() {
        return endTickIndex;
    }

    public void setEndTickIndex(int endTickIndex) {
        this.endTickIndex = endTickIndex;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
