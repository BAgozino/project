package uk.ac.cam.ba325.Midi.Quantisation.Helpers;

/**
 * Created by root on 04/04/16.
 */
public class IntPointer {
    private int value;
    public IntPointer(int value) {
        this.value = value;
    }

    public void increment(){
        value++;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
