package uk.ac.cam.ba325.Midi.Quantisation;

import uk.ac.cam.ba325.Midi.NoteDeltaSequence;
import uk.ac.cam.ba325.Midi.TickDelta;

import java.util.LinkedList;

/**
 * Created by root on 07/03/16.
 */
public class SuffixTree {

    private NoteDeltaSequence sequence;

    private ActivePoint activePoint;
    private int remainder;

    public SuffixTree(NoteDeltaSequence sequence){
        this.sequence = sequence;

    }

    public static SuffixTree create(NoteDeltaSequence sequence){
        SuffixTree tree = new SuffixTree(sequence);

        tree.constructTree();
        return tree;
    }


    private void constructTree(){
        SuffixNode root = new SuffixNode(-1);
        activePoint = new ActivePoint(root,null,0);
        remainder = 0;
        for(int i =0; i<sequence.size(); i++){
            remainder++;
            while(remainder>0) {//Todo I think this while is a mistake
                this.insertSuffix(i-remainder+1,i+1);
                remainder--;
            }
        }


    }

    /**
     *
     * @param start inclusive
     * @param end exclusive
     */
    private void insertSuffix(int start, int end){
        NoteDeltaSequence suffix = (NoteDeltaSequence) sequence.subList(start,end);
        SuffixArc destination = activePoint.getActiveNode().getArcPrefixedBy(suffix.get(0));

        if (destination == null){
            this.newNode(start);

        } else{
            activePoint.setActiveEdgeStart(sequence.get(start));
            activePoint.incrementActiveLength();
            remainder++;
        }
    }

    private void newNode(int start){
        SuffixNode toNode = new SuffixNode(start);
        addChild(activePoint.getActiveNode(),toNode,start);
    }


    public NoteDeltaSequence getSuffix(SuffixPointer pointer) {
        NoteDeltaSequence retVal = new NoteDeltaSequence();
        for (int i = pointer.getStart(); i < pointer.getStart() + pointer.getEnd(); i++){
            retVal.add(sequence.get(i));
        }
        return retVal;
    }

    public void addChild(SuffixNode fromNode, SuffixNode toNode, int start){
        fromNode.getChildren().add(new SuffixTree.SuffixArc(fromNode,toNode,new SuffixPointer(start)));
    }
    public class SuffixArc {
        SuffixPointer suffix;

        SuffixNode from;
        SuffixNode to;

        public boolean prefixedBy(TickDelta prefix){
            return prefix.getdTickRounded() == sequence.get(suffix.getStart()).getdTickRounded();
        }

        public SuffixArc(SuffixNode from, SuffixNode to, SuffixPointer suffix){
            this.from = from;
            this.to = to;
            this.suffix = suffix;
        }

    }

}
