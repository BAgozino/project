package uk.ac.cam.ba325.Midi.Quantisation;

/**
 * Created by root on 07/03/16.
 */
public class SuffixPointer {

    private int start;
    //exclusive
    private int end;

    public SuffixPointer(int start) {
        this.start = start;
    }

    public SuffixPointer(int start, int end){
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
