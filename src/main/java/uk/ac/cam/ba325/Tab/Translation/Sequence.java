package uk.ac.cam.ba325.Tab.Translation;

/**
 * Created by biko on 13/01/16.
 */



import uk.ac.cam.ba325.Tab.Instrument.HighHat;
import uk.ac.cam.ba325.Tab.Translation.Exceptions.AlreadySetException;

import java.io.File;
import java.io.PrintWriter;
import java.text.CharacterIterator;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Helper class for printing the tracks to files
 */
public class Sequence {

    private int resolution;
    private List<Strike> emptyLine = new LinkedList<Strike>();


    private List<Strike> highHats = new LinkedList<>();
    private List<Strike> bassDrums = new LinkedList<>();
    private List<Strike> snareDrums = new LinkedList<>();
    private List<Strike> floorToms = new LinkedList<>();
    private List<Strike> lowToms = new LinkedList<>();
    private List<Strike> highToms = new LinkedList<>();
    private List<Strike> crashCymbals = new LinkedList<>();
    private List<Strike> rideCymbals = new LinkedList<>();
    private ArrayList<List<Strike>> lines = new ArrayList<>(8);

    public Sequence(String filePath,int resolution) throws IOException{

        lines.add(0,highHats);
        lines.add(1,bassDrums);
        lines.add(2,snareDrums);
        lines.add(3,floorToms);
        lines.add(4,lowToms);
        lines.add(5,highToms);
        lines.add(6,crashCymbals);
        lines.add(7,rideCymbals);

        this.resolution = resolution;
        File sequenceFile = new File(filePath);


        Scanner scanner = new Scanner(sequenceFile);

        char[] line;
        Strike[] strikes = new Strike[resolution];
        int lineNumber =0;
        while(scanner.hasNextLine()){

            line = scanner.nextLine().toCharArray();
            for(int i = 0; i<line.length; i++){
                strikes[i] = new Strike(Character.getNumericValue(line[i]));
            }
            lines.get(lineNumber).addAll(Arrays.asList(strikes));
            lineNumber++;
        }


    }

    public Sequence(File song,int resolution) throws IOException{
        lines.add(0,highHats);
        lines.add(1,bassDrums);
        lines.add(2,snareDrums);
        lines.add(3,floorToms);
        lines.add(4,lowToms);
        lines.add(5,highToms);
        lines.add(6,crashCymbals);
        lines.add(7,rideCymbals);

        this.resolution = resolution;
        Scanner scanner = new Scanner(song);

        char[] line;
        Strike[] strikes = new Strike[resolution];
        int lineNumber =0;
        while(scanner.hasNextLine()){

            line = scanner.nextLine().toCharArray();
            for(int i = 0; i<line.length; i++){
                strikes[i] = new Strike(Character.getNumericValue(line[i]));
            }
            lines.get(lineNumber).addAll( Arrays.asList(strikes));
            lineNumber++;
        }
    }

    public Sequence(int resolution){
        lines.add(0,highHats);
        lines.add(1,bassDrums);
        lines.add(2,snareDrums);
        lines.add(3,floorToms);
        lines.add(4,lowToms);
        lines.add(5,highToms);
        lines.add(6,crashCymbals);
        lines.add(7,rideCymbals);

        this.resolution = resolution;
        for(int i=0; i<resolution; i++){
            emptyLine.add(new Strike(0));
        }
    }

    public void setLine(int i, Strike[] strikes){
        switch (i){
            case 0://highhat
                highHats.addAll(Arrays.asList(strikes));
                break;
            case 1://bass
                bassDrums.addAll(Arrays.asList(strikes));
                break;
            case 2://snare
                snareDrums.addAll(Arrays.asList(strikes));
                break;
            case 3://floor
                floorToms.addAll(Arrays.asList(strikes));
                break;
            case 4://lowtoms;
                lowToms.addAll(Arrays.asList(strikes));
                break;
            case 5://hightoms;
                highToms.addAll(Arrays.asList(strikes));
                break;
            case 6: //crash;
                crashCymbals.addAll(Arrays.asList(strikes));
                break;
            case 7://ride
                rideCymbals.addAll(Arrays.asList(strikes));
                break;
        }
    }

    private static enum InstrumentType{
        HIGHHAT("HH|HF|H"), CRASHCYMBAL("CC|C1|C2|C"), RIDECYMBAL("RD|R"), SNAREDRUM("SN|S"), BASSDRUM("BD|B"),
        HIGHTOM("T1|HT"), LOWTOM("T2|LT"), FLOORTOM("FT|T");

        public final String pattern;

        private InstrumentType(String pattern){
            this.pattern = pattern;
        }
    }

    private InstrumentType matchInstrument(String instrumentData) throws ParseException {
        InstrumentType matchedType;

        StringBuilder instrumentPatternsBuilder = new StringBuilder();
        for (InstrumentType instrumentType : InstrumentType.values()){
            instrumentPatternsBuilder.append(String.format("|(?<%s>%s)",instrumentType.name(),instrumentType.pattern));
        }
        Pattern instrumentPattern = Pattern.compile(instrumentPatternsBuilder.substring(1),Pattern.CASE_INSENSITIVE);


        Matcher matcher = instrumentPattern.matcher(instrumentData);
        if(matcher.find()){
            if (matcher.group(InstrumentType.HIGHHAT.name()) != null){
                matchedType = InstrumentType.HIGHHAT;
            } else if (matcher.group(InstrumentType.BASSDRUM.name()) != null){
                matchedType = InstrumentType.BASSDRUM;
            }else if (matcher.group(InstrumentType.CRASHCYMBAL.name()) != null){
                matchedType = InstrumentType.CRASHCYMBAL;
            }else if (matcher.group(InstrumentType.RIDECYMBAL.name()) != null){
                matchedType = InstrumentType.RIDECYMBAL;
            }else if (matcher.group(InstrumentType.SNAREDRUM.name()) != null){
                matchedType = InstrumentType.SNAREDRUM;
            }else if (matcher.group(InstrumentType.HIGHTOM.name()) != null){
                matchedType = InstrumentType.HIGHTOM;
            }else if (matcher.group(InstrumentType.LOWTOM.name()) != null){
                matchedType = InstrumentType.LOWTOM;
            }else if (matcher.group(InstrumentType.FLOORTOM.name()) != null){
                matchedType = InstrumentType.FLOORTOM;
            }else{
                throw new ParseException(instrumentData,0);
            }
        } else{
            throw new ParseException(instrumentData,0);
        }

        return matchedType;
    }

    public void fillLine(String instrumentData, List<Lexer.Token> beats)throws Exception, ParseException, AlreadySetException{
        InstrumentType instrument = matchInstrument(instrumentData);

        switch (instrument){
            case HIGHHAT:
                if (!highHats.isEmpty()){
                    throw new AlreadySetException("highats already set");
                }
                for(Lexer.Token token: beats) {
                    highHats.add(new Strike(token));
                }
                if (highHats.size() != resolution){
                    throw new ParseException("Resolution is off",0);
                }
                break;
            case BASSDRUM:
                if (!bassDrums.isEmpty()){
                    throw new AlreadySetException("bassDrums already set");
                }
                for(Lexer.Token token: beats) {
                    bassDrums.add(new Strike(token));
                }
                if(bassDrums.size() != resolution)
                    throw new ParseException("Resolution is off",0);
                break;
            case CRASHCYMBAL:
                if (!crashCymbals.isEmpty()){
                    throw new AlreadySetException("crashCymbals already set");
                }
                for(Lexer.Token token: beats) {
                    crashCymbals.add(new Strike(token));
                }
                if(crashCymbals.size() != resolution)
                    throw new ParseException("Resolution is off",0);
                break;
            case FLOORTOM:
                if (!floorToms.isEmpty()){
                    throw new AlreadySetException("floorToms already set");
                }
                for(Lexer.Token token: beats) {
                    floorToms.add(new Strike(token));
                }
                if (floorToms.size() != resolution){
                    throw new ParseException("Resolution is off",0);
                }
                break;
            case HIGHTOM:
                if (!highToms.isEmpty()){
                    throw new AlreadySetException("highToms already set");
                }
                for(Lexer.Token token: beats) {
                    highToms.add(new Strike(token));
                }
                if (highToms.size() != resolution){
                    throw new ParseException("Resolution is off",0);
                }
                break;
            case LOWTOM:
                if (!lowToms.isEmpty()){
                    throw new AlreadySetException("lowToms already set");
                }
                for(Lexer.Token token: beats) {
                    lowToms.add(new Strike(token));
                }
                if (lowToms.size() != resolution){
                    throw new ParseException("Resolution is off",0);
                }
                break;
            case RIDECYMBAL:
                if (!rideCymbals.isEmpty()){
                    throw new AlreadySetException("rideCymbals already set");
                }
                for(Lexer.Token token: beats) {
                    rideCymbals.add(new Strike(token));
                }
                if (rideCymbals.size() != resolution){
                    throw new ParseException("Resolution is off",0);
                }
                break;
            case SNAREDRUM:
                if (!snareDrums.isEmpty()){
                    throw new AlreadySetException("snareDrums already set");
                }
                for(Lexer.Token token: beats) {
                    snareDrums.add(new Strike(token));
                }
                if (snareDrums.size() != resolution){
                    throw new ParseException("Resolution is off",0);
                }
                break;
        }



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
    public void writeToFile(PrintWriter writer) throws IOException{

        for(Strike strike : getHighHats()){
            writer.append(strike.toString());
        }
        writer.write("\n");

        for(Strike strike : getBassDrums()){
            writer.append(strike.toString());
        }
        writer.write("\n");

        for(Strike strike : getSnareDrums()){
            writer.append(strike.toString());
        }
        writer.write("\n");

        for(Strike strike : getFloorToms()){
            writer.append(strike.toString());
        }
        writer.write("\n");

        for(Strike strike : getLowToms()){
            writer.append(strike.toString());
        }
        writer.write("\n");

        for(Strike strike : getHighToms()){
            writer.append(strike.toString());
        }
        writer.write("\n");

        for(Strike strike : getCrashCymbals()){
            writer.append(strike.toString());
        }
        writer.write("\n");

        for(Strike strike : getRideCymbals()){
            writer.append(strike.toString());
        }

        writer.flush();

    }

    public List<Strike> getHighHats() {
        if (highHats.isEmpty()){
            return emptyLine;
        }else{
            return highHats;
        }
    }

    public void setHighHats(List<Strike> highHats) {
        this.highHats = highHats;
    }

    public List<Strike> getBassDrums() {
        if (bassDrums.isEmpty()){
            return emptyLine;
        }else {
            return bassDrums;
        }
    }

    public void setBassDrums(List<Strike> bassDrums) {
        this.bassDrums = bassDrums;
    }

    public List<Strike> getSnareDrums() {
        if (snareDrums.isEmpty()){
            return emptyLine;
        }else {
            return snareDrums;
        }
    }

    public void setSnareDrums(List<Strike> snareDrums) {
        this.snareDrums = snareDrums;
    }

    public List<Strike> getFloorToms() {
        if (floorToms.isEmpty()){
            return emptyLine;
        }else {
            return floorToms;
        }
    }

    public void setFloorToms(List<Strike> floorToms) {
        this.floorToms = floorToms;
    }

    public List<Strike> getLowToms() {

        if (lowToms.isEmpty()){
            return emptyLine;
        }else {
            return lowToms;
        }
    }

    public void setLowToms(List<Strike> lowToms) {
        this.lowToms = lowToms;
    }

    public List<Strike> getHighToms() {

        if (highToms.isEmpty()){
            return emptyLine;
        }else {
            return highToms;
        }
    }

    public void setHighToms(List<Strike> highToms) {
        this.highToms = highToms;
    }

    public List<Strike> getCrashCymbals() {

        if (crashCymbals.isEmpty()){
            return emptyLine;
        }else {
            return crashCymbals;
        }
    }

    public void setCrashCymbals(List<Strike> crashCymbals) {
        this.crashCymbals = crashCymbals;
    }

    public List<Strike> getRideCymbals() {

        if (rideCymbals.isEmpty()){
            return emptyLine;
        }else {
            return rideCymbals;
        }
    }

    public void setRideCymbals(List<Strike> rideCymbals) {
        this.rideCymbals = rideCymbals;
    }

    public int getResolution() {
        return resolution;
    }
}
