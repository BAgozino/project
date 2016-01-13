package uk.ac.cam.ba325.Tab.Translation;


/**
 * Created by biko on 13/01/16.
 */
public class Strike {

    public static final Strike HIT = new Strike(1);
    public static final Strike REST = new Strike(0);
    private final int value;

    public Strike(int value){
        this.value = value;
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }
}
