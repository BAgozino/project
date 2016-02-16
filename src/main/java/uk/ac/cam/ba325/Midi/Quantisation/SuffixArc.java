package uk.ac.cam.ba325.Midi.Quantisation;

import uk.ac.cam.ba325.Midi.NoteDeltaSequence;

/**
 * Created by root on 12/02/16.
 */
public class SuffixArc {
    NoteDeltaSequence suffix;

    SuffixNode from;
    SuffixNode to;

    public SuffixArc(SuffixNode from, SuffixNode to, NoteDeltaSequence suffix){
        this.from = from;
        this.to = to;
        this.suffix = suffix;
    }

}
