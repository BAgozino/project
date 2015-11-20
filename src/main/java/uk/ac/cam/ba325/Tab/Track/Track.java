package uk.ac.cam.ba325.Tab.Track;

import uk.ac.cam.ba325.Tab.Instrument.Instrument;

import java.util.List;

/**
 * Created by biko on 19/11/15.
 */
public abstract class Track {

    private List<Instrument> beats;

    public List<Instrument> getBeats() {
        return beats;
    }

    public void setBeats(List<Instrument> beats) {
        this.beats = beats;
    }
}
