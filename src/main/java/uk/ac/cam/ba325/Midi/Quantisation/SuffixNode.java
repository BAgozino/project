package uk.ac.cam.ba325.Midi.Quantisation;

import java.util.List;

/**
 * Created by root on 12/02/16.
 */
public class SuffixNode {

    private List<SuffixArc> children = null;

    private int offset;


    public List<SuffixArc> getChildren() {
        return children;
    }

    public void setChildren(List<SuffixArc> children) {
        this.children = children;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
