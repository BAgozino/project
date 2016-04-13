package uk.ac.cam.ba325.Midi;

import uk.ac.cam.ba325.Midi.Quantisation.DrumNoteDeltaSuffixTree;
import uk.ac.cam.ba325.Tab.Translation.*;

import javax.sound.midi.*;
import javax.sound.midi.Sequence;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 07/02/16.
 */
public class MidiLoader {

    private Sequence m_sequence;

    public boolean loadMidiFile(String path){
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



    public DrumNoteDeltaSequences buildDrumNoteDeltaSequences(){
        DrumNoteDeltaSequences drumNoteDeltaSequences = new DrumNoteDeltaSequences(m_sequence.getTickLength());
        Track recTrack = m_sequence.getTracks()[1];

        for(int i=0; i<recTrack.size(); i++){
            MidiEvent event = recTrack.get(i);

            if(event.getMessage() instanceof ShortMessage){
                ShortMessage message = (ShortMessage) event.getMessage();

                if (message.getCommand() == ShortMessage.NOTE_ON){
                    switch (message.getData1()){
                        case 42:
                            //highhat
                            drumNoteDeltaSequences.getHighHat().addTick(event.getTick());
                            break;
                        case 44:
                            //highhat
                            drumNoteDeltaSequences.getHighHat().addTick(event.getTick());

                            break;
                        case 46:
                            //highhat
                            drumNoteDeltaSequences.getHighHat().addTick(event.getTick());

                            break;
                        case 36:
                            //bass
                            drumNoteDeltaSequences.getBassDrum().addTick(event.getTick());

                            break;
                        case 49:
                            //crash
                            drumNoteDeltaSequences.getCrashCymbal().addTick(event.getTick());

                            break;
                        case 48:
                            //hightom
                            drumNoteDeltaSequences.getHighTom().addTick(event.getTick());

                            break;
                        case 43:
                            //floortom
                            drumNoteDeltaSequences.getLowTom().addTick(event.getTick());

                            break;
                        case 45:
                            //lowtom
                            drumNoteDeltaSequences.getLowTom().addTick(event.getTick());

                            break;
                        case 51:
                            //ride
                            drumNoteDeltaSequences.getRideCymbal().addTick(event.getTick());

                            break;
                        case 38:
                            //snare
                            drumNoteDeltaSequences.getSnareDrum().addTick(event.getTick());

                            break;
                    }
                }
            }
        }
        return drumNoteDeltaSequences;
    }

    public LinkedList<CsvMidiEvent> onEventsToCsvMidiEvent(){
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


    public boolean printCsv(String path, List<CsvMidiEvent> csvs){
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
        MidiLoader midiLoader = new MidiLoader();

        midiLoader.loadMidiFile("../StudyData/Jared-9-2-0.mid");
        midiLoader.printCsv("../StudyData/test.txt", midiLoader.onEventsToCsvMidiEvent());
        DrumNoteDeltaSequences d = midiLoader.buildDrumNoteDeltaSequences();
        DrumNoteDeltaSuffixTree st = new DrumNoteDeltaSuffixTree(d);

        uk.ac.cam.ba325.Tab.Translation.Sequence query = st.getBestSequence();


        for(Track track : midiLoader.getSequence().getTracks()){
            System.out.println(track);
        }

    }

    public Sequence getSequence() {
        return m_sequence;
    }
}
