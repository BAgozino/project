package uk.ac.cam.ba325.Midi.Quantisation;

import uk.ac.cam.ba325.Midi.NoteDeltaSequence;
import uk.ac.cam.ba325.Midi.TickDelta;

import java.util.List;


/**
 * Created by root on 12/02/16.
 */
public class SuffixNode {


    private List<SuffixTree.SuffixArc> children = null;
    private int offset;

    public SuffixNode(int offset) {
        this.offset = offset;
    }

    public SuffixTree.SuffixArc getArcPrefixedBy(TickDelta suffix){
        for(SuffixTree.SuffixArc child:children){
            if(child.prefixedBy(suffix)){
                return child;
            }
        }
        return null;
    }




    public List<SuffixTree.SuffixArc> getChildren() {
        return children;
    }

    public void setChildren(List<SuffixTree.SuffixArc> children) {
        this.children = children;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
