package uk.ac.cam.ba325.Midi;

import javax.sound.midi.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 07/02/16.
 */
public class CsvCreator {

    private Sequence m_sequence;

    private boolean loadMidiFile(String path){
        File file = new File(path);
        boolean result = true;
        try{
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            m_sequence = MidiSystem.getSequence(is);
        } catch (InvalidMidiDataException|IOException fnfe){
            result = false;
        }
        return result;

    }

    private LinkedList<CsvMidiEvent> onEventsToCsvMidiEvent(){
        Track recTrack = m_sequence.getTracks()[1];
        LinkedList<CsvMidiEvent> csvs = new LinkedList<>();
        for(int i=0; i<recTrack.size(); i++){
            MidiEvent event = recTrack.get(i);

            if(event.getMessage() instanceof ShortMessage){
                ShortMessage message = (ShortMessage) event.getMessage();
                if (message.getCommand() == ShortMessage.NOTE_ON) {
                    System.out.println(message.getData1()+"    "+message.getData2());
                    csvs.add(new CsvMidiEvent(event.getTick(),message.getData1()));
                }

            } else {
                //skip
            }




        }
        return csvs;
    }


    private boolean printCsv(String path, List<CsvMidiEvent> csvs){
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path)));

            for (CsvMidiEvent csv : csvs) {
                writer.write(csv.toCsv());
            }
            writer.flush();
        } catch (IOException ioe){
            return false;
        }
        return true;
    }
    public static void main(String[] args){
        CsvCreator csvCreator = new CsvCreator();

        csvCreator.loadMidiFile("../StudyData/Jared-9-2-0.mid");
        csvCreator.printCsv("../StudyData/CSVTEST.txt",csvCreator.onEventsToCsvMidiEvent());

        for(Track track : csvCreator.getSequence().getTracks()){
            System.out.println(track);
        }

    }

    public Sequence getSequence() {
        return m_sequence;
    }
}
