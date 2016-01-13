package uk.ac.cam.ba325.Tab.Song;



import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by biko on 13/01/16.
 */
@Entity
@Table(name = "Song")
public class Song {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "songId", unique = true)
    private String songId;

    @Column(name = "songName")
    private String songName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Pattern")
    @Column(name = "patterns")
    private Set<Pattern> patterns;

    @Column(name = "resolution")
    private int resolution;


    public String getSongId() {
        return songId;
    }

    public void setSongId(String id) {
        this.songId = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Set<Pattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(Set<Pattern> patterns) {
        this.patterns = patterns;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }
}
