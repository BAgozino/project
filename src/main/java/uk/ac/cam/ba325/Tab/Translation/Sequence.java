package uk.ac.cam.ba325.Tab.Translation;

/**
 * Created by biko on 13/01/16.
 */



import uk.ac.cam.ba325.Tab.Instrument.Instrument;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for printing the tracks to files
 */
public class Sequence {


    List<Strike> highHats;
    List<Strike> bassDrums;
    List<Strike> snareDrums;
    List<Strike> floorToms;
    List<Strike> lowToms;
    List<Strike> highToms;
    List<Strike> crashCymbals;
    List<Strike> rideCymbals;

    private static enum InstrumentType{
        HIGHHAT
    }


    public void fillLine(String instrumentData, List<Lexer.Token> beats){



    }









    /**]
     *  writes the sequence to file in the order of
     *  HighHats:
     *  BassDrums:
     *  SnareDrums:
     *  FloorToms:
     *  LowToms:
     *  HighToms:
     *  CrashCymbals:
     *  RideCymbals:
     *
     *  beats are represented by a 1 and rests are represented by a 0.
     *
     *  Resolution is variable depending on the song. This should be stored in the database for the song.
     * @param writer to write to file - does not close it here!
     */
    public void writeToFile(BufferedWriter writer) throws IOException{

        for(Strike strike : highHats){
            writer.append(strike.toString());
        }
        writer.write("\n");

        for(Strike strike : bassDrums){
            writer.append(strike.toString());
        }
        writer.write("\n");

        for(Strike strike : snareDrums){
            writer.append(strike.toString());
        }
        writer.write("\n");

        for(Strike strike : floorToms){
            writer.append(strike.toString());
        }
        writer.write("\n");

        for(Strike strike : lowToms){
            writer.append(strike.toString());
        }
        writer.write("\n");

        for(Strike strike : highToms){
            writer.append(strike.toString());
        }
        writer.write("\n");

        for(Strike strike :crashCymbals){
            writer.append(strike.toString());
        }
        writer.write("\n");

        for(Strike strike : rideCymbals){
            writer.append(strike.toString());
        }

    }

    public List<Strike> getHighHats() {
        return highHats;
    }

    public void setHighHats(List<Strike> highHats) {
        this.highHats = highHats;
    }

    public List<Strike> getBassDrums() {
        return bassDrums;
    }

    public void setBassDrums(List<Strike> bassDrums) {
        this.bassDrums = bassDrums;
    }

    public List<Strike> getSnareDrums() {
        return snareDrums;
    }

    public void setSnareDrums(List<Strike> snareDrums) {
        this.snareDrums = snareDrums;
    }

    public List<Strike> getFloorToms() {
        return floorToms;
    }

    public void setFloorToms(List<Strike> floorToms) {
        this.floorToms = floorToms;
    }

    public List<Strike> getLowToms() {
        return lowToms;
    }

    public void setLowToms(List<Strike> lowToms) {
        this.lowToms = lowToms;
    }

    public List<Strike> getHighToms() {
        return highToms;
    }

    public void setHighToms(List<Strike> highToms) {
        this.highToms = highToms;
    }

    public List<Strike> getCrashCymbals() {
        return crashCymbals;
    }

    public void setCrashCymbals(List<Strike> crashCymbals) {
        this.crashCymbals = crashCymbals;
    }

    public List<Strike> getRideCymbals() {
        return rideCymbals;
    }

    public void setRideCymbals(List<Strike> rideCymbals) {
        this.rideCymbals = rideCymbals;
    }
}
