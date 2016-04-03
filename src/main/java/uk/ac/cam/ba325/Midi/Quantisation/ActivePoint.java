package uk.ac.cam.ba325.Midi.Quantisation;

import uk.ac.cam.ba325.Midi.TickDelta;

/**
 * Created by root on 31/03/16.
 */
public class ActivePoint {

    private SuffixNode activeNode;
    private TickDelta activeEdgeStart;
    private int activeLength;

    public ActivePoint(SuffixNode activeNode, TickDelta activeEdgeStart, int activeLength) {
        this.activeNode = activeNode;
        this.activeEdgeStart = activeEdgeStart;
        this.activeLength = activeLength;
    }

    public SuffixNode getActiveNode() {
        return activeNode;
    }

    public void incrementActiveLength(){
        activeLength++;
    }

    public void setActiveNode(SuffixNode activeNode) {
        this.activeNode = activeNode;
    }

    public TickDelta getActiveEdgeStart() {
        return activeEdgeStart;
    }

    public void setActiveEdgeStart(TickDelta activeEdgeStart) {
        this.activeEdgeStart = activeEdgeStart;
    }

    public int getActiveLength() {
        return activeLength;
    }

    public void setActiveLength(int activeLength) {
        this.activeLength = activeLength;
    }
}
