package uk.ac.cam.ba325.Tab;

import uk.ac.cam.ba325.Tab.Track.Track;

import java.util.List;

/**
 * Created by biko on 18/11/15.
 */
public class Sequence {

    private String filename;

    private List<Track> tracks;

    private int length;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
