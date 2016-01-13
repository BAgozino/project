package uk.ac.cam.ba325.Tab.Song;

import javax.persistence.*;

/**
 * Created by biko on 13/01/16.
 */

@Entity
@Table(name = "pattern")
public class Pattern {

    @Id
    @GeneratedValue(generator = "uuid")
    @Column(name = "patternId", unique = true, nullable = false)
    private String patternId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "songId",nullable = false)
    private Song song;

    @Column(name="patternFileName")
    private String patternFileName;

    public String getPatternId() {
        return patternId;
    }

    public void setPatternId(String patternId) {
        this.patternId = patternId;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getPatternFileName() {
        return patternFileName;
    }

    public void setPatternFileName(String patternFileName) {
        this.patternFileName = patternFileName;
    }
}
