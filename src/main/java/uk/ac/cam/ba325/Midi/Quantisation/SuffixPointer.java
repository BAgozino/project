package uk.ac.cam.ba325.Midi.Quantisation;

/**
 * Created by root on 07/03/16.
 */
public class SuffixPointer {

    private int index;
    private int length;

    public SuffixPointer(int index, int length){
        this.index = index;
        this.length = length;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
