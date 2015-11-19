package testmidi;


import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by biko on 05/11/15.
 */
public class MidiOpener{
    private static final Logger logger = Logger.getLogger(MidiOpener.class.getName());


    public void sequenceMidi() throws Exception{
        Sequencer sequencer;

        URL url = MidiOpener.class.getResource("/midi/test.mid");
        File midiFile = new File(url.getPath());
        //try{
            sequencer = MidiSystem.getSequencer();
            sequencer.setSequence(MidiSystem.getSequence(midiFile));
            sequencer.open();
            sequencer.start();

            while(true){
                if(sequencer.isRunning()){
                    try{
                        logger.info(String.valueOf(sequencer.getTempoInBPM()));
                        Thread.sleep(1000);
                    }catch(InterruptedException ignore){
                        break;
                    }
                } else {
                    break;
                }
            }

            sequencer.stop();
            sequencer.close();




    }

    public static void main(String[] args) throws Exception{
        MidiOpener midiOpener = new MidiOpener();

        midiOpener.sequenceMidi();

    }

}
