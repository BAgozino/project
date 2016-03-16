package uk.ac.cam.ba325.Midi.Quantisation;

import uk.ac.cam.ba325.Midi.NoteDeltaSequence;
import uk.ac.cam.ba325.Midi.TickDelta;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by root on 07/03/16.
 */
public class SuffixTree {

    private NoteDeltaSequence sequence;

    private SuffixNode root;

    public SuffixTree(NoteDeltaSequence sequence){
        this.sequence = sequence;
        root = new SuffixNode();
        root.setChildren(new LinkedList<SuffixArc>());
    }


    private void constructTree(NoteDeltaSequence sequence){
        sequence.add(new TickDelta(-1));
        this.sequence = sequence;

        root = new SuffixNode();
        root.setChildren(new LinkedList<SuffixArc>());
        root.getChildren().add(new SuffixArc(root,new SuffixNode(),new SuffixPointer(0,sequence.size())));

        for(int i=1; i<sequence.size(); i++){
            SuffixNode cur = this.root;
            int j = i;
            while (j<sequence.size()){
                if sequence.get(j)
            }
        }

        for(int i=0; i<sequence.size(); i++){
            for(int j = i; j<sequence.size(); j++){

            }
        }
    }

    public NoteDeltaSequence getSuffix(SuffixPointer pointer) {
        NoteDeltaSequence retVal = new NoteDeltaSequence();
        for (int i = pointer.getIndex(); i < pointer.getIndex() + pointer.getLength(); i++){
            retVal.add(sequence.get(i));
        }
        return retVal;
    }

    private

}
