package uk.ac.cam.ba325.Midi;

/**
 * Created by root on 08/02/16.
 */
public class CsvMidiEvent {

    private long tick;
    private int noteNo;

    public CsvMidiEvent(long tick, int noteNo){
        this.tick = tick;
        this.noteNo = noteNo;
    }

    public String toCsv(){
        return tick+","+noteNo+"\n";
    }

    public long getTick() {
        return tick;
    }

    public void setTick(long tick) {
        this.tick = tick;
    }

    public int getNoteNo() {
        return noteNo;
    }

    public void setNoteNo(int noteNo) {
        this.noteNo = noteNo;
    }
}
