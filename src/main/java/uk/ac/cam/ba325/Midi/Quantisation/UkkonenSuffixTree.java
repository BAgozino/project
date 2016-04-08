package uk.ac.cam.ba325.Midi.Quantisation;

import uk.ac.cam.ba325.Midi.NoteDeltaSequence;
import uk.ac.cam.ba325.Midi.Quantisation.Helpers.IntPointer;
import uk.ac.cam.ba325.Midi.TickDelta;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 04/04/16.
 */
public class UkkonenSuffixTree {

    public class SuffixTreeNode{
        private ArrayList<SuffixTreeNode> children = new ArrayList<>();

        private SuffixTreeNode suffixLink;

        public void addChild(SuffixTreeNode node, int iterationPoint){
//            if(m_activeNode == m_root){
//                node.setSuffixIndex(iterationPoint);
//            } else {
                node.setSuffixIndex(iterationPoint);
                m_activeNode.setSuffixIndex(-1);
//            }
            children.add(node);
        }

        public void addChildrenForSplit(int edgeStart){

            this.addChild(newNode(edgeStart,m_leafEnd), m_activeEdgeStart);

            //next.start += m_activeLength;
            //split.addChild(next.getStart(),next);
            this.addChild(newNode(end.getValue()+1,m_leafEnd),suffixIndex);
            this.setSuffixIndex(-1);
        }
        public SuffixTreeNode getChild(int edgeStart){
            TickDelta edge, query = m_sequence.get(edgeStart);

            for(SuffixTreeNode node:children){
                edge = m_sequence.get(node.getStart());
                if (edge.getdTickRounded() == query.getdTickRounded())
                    return node;
            }
            return null;
        }
        private int start;
        private IntPointer end = new IntPointer(-1);

        private int suffixIndex;


        public int getSuffixLength(){
            return 1+end.getValue()-start;
        }
        public List<SuffixTreeNode> getChildren() {
            return children;
        }

        public void setChildren(ArrayList<SuffixTreeNode> children) {
            this.children = children;
        }

        public SuffixTreeNode getSuffixLink() {
            return suffixLink;
        }

        public void setSuffixLink(SuffixTreeNode suffixLink) {
            this.suffixLink = suffixLink;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end.getValue();
        }

        public void setEnd(IntPointer end) {
            this.end = end;
        }

        public int getSuffixIndex() {
            return suffixIndex;
        }

        public void setSuffixIndex(int suffixIndex) {
            this.suffixIndex = suffixIndex;
        }
    }

    NoteDeltaSequence m_sequence;
    SuffixTreeNode m_root = null;

    SuffixTreeNode m_lastNewNode = null;
    SuffixTreeNode m_activeNode = null;

    int m_activeEdge = -1;
    int m_activeEdgeStart = -1;
    int m_activeLength = 0;



    int m_remainingSuffixCount = 0;
    IntPointer m_leafEnd = new IntPointer(-1);
    IntPointer m_rootEnd = null;
    IntPointer m_splitEnd = null;
    int m_size = -1;

    public SuffixTreeNode newNode(int pos, IntPointer end){
        SuffixTreeNode node = new SuffixTreeNode();

        node.setSuffixLink(m_root);
        node.setStart(pos);
        node.setEnd(end);

        node.setSuffixIndex(pos);
        return node;
    }

    public int edgeLength(SuffixTreeNode node){
        return node.getEnd() - node.getStart() +1;
    }

    public boolean walkDown(SuffixTreeNode currentNode){
        if(m_activeLength>=edgeLength(currentNode)){
            m_activeEdge += edgeLength(currentNode);
            m_activeLength -= edgeLength(currentNode);
            m_activeNode = currentNode;
            return true;
        }
        return false;
    }

    public void extendSuffixTree(int position){
        m_leafEnd.setValue(position);

        m_remainingSuffixCount++;

        m_lastNewNode = null;

        while(m_remainingSuffixCount > 0){
            m_activeEdgeStart = position-m_remainingSuffixCount+1;
            if (m_activeLength == 0)
                m_activeEdge = position; //as we are just sitting at the node


            SuffixTreeNode next = m_activeNode.getChild(m_activeEdge);
            if (next == null){
                //Rule 2
                SuffixTreeNode node = newNode(position,m_leafEnd);

                m_activeNode.addChild(newNode(position,m_leafEnd),position);

                if (m_lastNewNode != null){
                    m_lastNewNode.suffixLink = m_activeNode;
                    m_lastNewNode = null;
                }
            } else {
                if (walkDown(next)){ //if the edge is too long start again from the next node
                    continue;
                }
                if(m_sequence.get(next.getStart() +m_activeLength).getdTickRounded() ==
                        m_sequence.get(position).getdTickRounded()){ //if current character being processed is aldready on the edge
                    if((m_lastNewNode != null) && (m_activeNode != m_root)){
                        m_lastNewNode.suffixLink = m_activeNode;
                        m_lastNewNode = null;
                    }

                    m_activeLength++;
                    break;
                }

                m_splitEnd = new IntPointer(next.start +m_activeLength-1);


                next.setEnd(m_splitEnd);
                //SuffixTreeNode split = newNode(next.start, m_splitEnd);
                //m_activeNode.addChild(m_activeEdge,split);


                next.addChildrenForSplit(position);
                //next.addChild(newNode(position,m_leafEnd), position );

                //next.start += m_activeLength;
                //split.addChild(next.getStart(),next);
                //next.addChild(newNode(next.getEnd()+1,m_leafEnd),position );
                if (m_lastNewNode != null){
                    m_lastNewNode.suffixLink = next;
                }

                m_lastNewNode = next;

            }

            m_remainingSuffixCount--;
            if(m_activeNode == m_root && m_activeLength >0){
                m_activeLength--;
                m_activeEdge = position - m_remainingSuffixCount+1;
            }
            else if(m_activeNode != m_root){
                m_activeNode = m_activeNode.suffixLink;
            }

        }
    }

    public UkkonenSuffixTree(NoteDeltaSequence sequence){
        m_sequence = sequence;
        m_size = sequence.size();
        m_rootEnd = new IntPointer(-1);



        m_root = newNode(-1, m_rootEnd);

        m_activeNode = m_root;
        for(int i = 0; i<m_size; i++){
            this.extendSuffixTree(i);
        }

        indexLeafNodes(0,m_root);

    }

    public void indexLeafNodes(int count,SuffixTreeNode node){ //Todo
        if(node!=m_root) {
            count += node.getSuffixLength();
        }

        List<SuffixTreeNode> children = node.getChildren();
        if(children.isEmpty()){
            node.setSuffixIndex(node.getEnd()-count+1);
        }else{
            for(SuffixTreeNode child : children){
                indexLeafNodes(count,child);
            }
        }

    }



    public void print(){
        StringBuilder builder = new StringBuilder();

        addNodeToStringBuilder(m_root,builder);
        System.out.println(builder.toString());

    }

    int h_level=0;
    public void addNodeToStringBuilder(SuffixTreeNode node, StringBuilder stringBuilder){
         for(int i =0; i<h_level; i++){
             stringBuilder.append("|");
         }
        if(node == m_root){
            stringBuilder.append("(root");
        }else{
            stringBuilder.append("-");
            stringBuilder.append("\"");
            for(int i =node.getStart();i<=node.getEnd();i++){

                stringBuilder.append(m_sequence.get(i).getdTick());
                if(i!=node.getEnd()) {
                    stringBuilder.append(",");
                }
            }

            stringBuilder.append("\"");
            stringBuilder.append("-(");
            stringBuilder.append(node.getSuffixIndex());




        }
        if(node.getChildren().isEmpty()) {
            stringBuilder.append(")\n     ");

        }else {
            stringBuilder.append(")-\n     ");
            for (SuffixTreeNode currNode : node.getChildren()) {
                h_level++;
                addNodeToStringBuilder(currNode, stringBuilder);
                h_level--;


            }
        }
    }




    public static void main(String[] args){
        NoteDeltaSequence testSequence = new NoteDeltaSequence();
        long[] ticks = {1,2,4,7,8,10,14,15,17,20,25};//
        testSequence.add(new TickDelta(-1));
        for(long tick: ticks){
            testSequence.addTick(tick);
        }
        testSequence.add(new TickDelta(-2));

        UkkonenSuffixTree testTree = new UkkonenSuffixTree(testSequence);

        testTree.print();
    }
}
