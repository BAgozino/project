package uk.ac.cam.ba325.Matcher;

import uk.ac.cam.ba325.Matcher.Helpers.*;
import uk.ac.cam.ba325.Midi.DrumNoteDeltaSequences;
import uk.ac.cam.ba325.Midi.MidiLoader;
import uk.ac.cam.ba325.Midi.Quantisation.DrumNoteDeltaSuffixTree;
import uk.ac.cam.ba325.Tab.Translation.Sequence;
import uk.ac.cam.ba325.Tab.Translation.Strike;

import java.io.*;
import java.util.List;

/**
 * Created by root on 09/04/16.
 */
public class DistanceMetric {

    /**
     * computes the edit distance using the Wagner-Fischer algorithm
     * @param sequence1
     * @param sequence2
     * @return
     */
    public static int twoDimensionalEditDistance(Sequence sequence1, Sequence sequence2, int offset){

        int returnValue = 0;
        returnValue += editDistance(sequence1.getBassDrums(),sequence2.getBassDrums(),offset);
        returnValue += editDistance(sequence1.getCrashCymbals(),sequence2.getCrashCymbals(),offset);
        returnValue += editDistance(sequence1.getFloorToms(),sequence2.getFloorToms(),offset);
        returnValue += editDistance(sequence1.getHighHats(),sequence2.getHighHats(),offset);
        returnValue += editDistance(sequence1.getHighToms(),sequence2.getHighToms(),offset);
        returnValue += editDistance(sequence1.getLowToms(),sequence2.getLowToms(),offset);
        returnValue += editDistance(sequence1.getRideCymbals(),sequence2.getRideCymbals(),offset);
        returnValue += editDistance(sequence1.getSnareDrums(),sequence2.getSnareDrums(),offset);
        System.out.println("\t \t \t \t returns with:"+ returnValue);
        return returnValue;
    }

    public static int editDistance(List<Strike> sequence1,List<Strike> sequence2, int offset){
        int m = sequence1.size();
        int n = sequence2.size();
        int[][] distances = new int[m][n];

        for(int i=0; i<m; i++){
            distances[i][0] = i;
        }
        for(int i=0; i<n; i++){
            distances[0][i] = i;
        }

        for(int j=1; j<n; j++){
            for(int i=1; i<m; i++){
                if(sequence1.get((i+offset) %16).getValue() == sequence2.get(j).getValue()){
                    distances[i][j] = distances[i-1][j-1];
                }else{
                    distances[i][j] = min(distances[i-1][j] +1,distances[i][j-1] +1,distances[i-1][j-1] +1);

                }
            }
        }

        return distances[m-1][n-1];
    }

    public static int twoDimensionalCyclicEditDistance(Sequence sequence1, Sequence sequence2){
        int bestMatch = Integer.MAX_VALUE;
        int currentDistance =0;
        for(int i=0; i<sequence1.getResolution(); i++){
            currentDistance = twoDimensionalEditDistance(sequence1,sequence2,i);
            if(currentDistance<bestMatch){
                bestMatch = currentDistance;
            }
        }
        return bestMatch;
    }

    public static int twoDimensionalCyclicHammingDistance(Sequence sequence1, Sequence sequence2){
        int bestMatch =Integer.MAX_VALUE;
        int currentDistance = 0;
        for(int i=0; i<sequence1.getResolution(); i++){
            currentDistance = twoDimensionalHammingDistance(sequence1,sequence2,i);
            if(currentDistance< bestMatch){
                bestMatch = currentDistance;
            }
        }
        return bestMatch;
    }

    public static int twoDimensionalHammingDistance(Sequence sequence1, Sequence sequence2,int offset){

        int returnValue =0;
        returnValue += hammingDistance(sequence1.getBassDrums(),sequence2.getBassDrums(),offset);
        returnValue += hammingDistance(sequence1.getCrashCymbals(),sequence2.getCrashCymbals(),offset);
        returnValue += hammingDistance(sequence1.getFloorToms(),sequence2.getFloorToms(),offset);
        returnValue += hammingDistance(sequence1.getHighHats(),sequence2.getHighHats(),offset);
        returnValue += hammingDistance(sequence1.getHighToms(),sequence2.getHighToms(),offset);
        returnValue += hammingDistance(sequence1.getLowToms(),sequence2.getLowToms(),offset);
        returnValue += hammingDistance(sequence1.getRideCymbals(),sequence2.getRideCymbals(),offset);
        returnValue += hammingDistance(sequence1.getSnareDrums(),sequence2.getSnareDrums(),offset);

        return returnValue;
    }

    public static int hammingDistance(List<Strike> sequence1, List<Strike> sequence2,int offset){
        int shorter = Math.min(sequence1.size(),sequence2.size());
        int longer = Math.max(sequence1.size(),sequence2.size());

        int returnValue = 0;


        int pointInList;
        for(int i=0; i<shorter; i++){
            pointInList = (i+offset) % 16;
            if(sequence1.get(pointInList).getValue() != sequence2.get(i).getValue()){
                returnValue++;
            }
        }

        returnValue += longer-shorter;

        return returnValue;
    }

    private static int min(int deletion, int insertion, int substitution){
        return Math.min(deletion,Math.min(insertion,substitution));

    }


    public static ResultListArray topMatches(int returnNumber,
                                             String metricType,
                                             Sequence query) throws InvalidDistanceMetric, IOException{
        System.out.println("\t \t \tStarting topMatches");
        ResultListArray results = new ResultListArray();

        File database = new File("src/main/resources/Database");

        Sequence datapoint;
        boolean full = false; //true if full and so we need to start checking seal
        boolean seal = false; //false if broken and so we need to update largest
        int largest = 0;
        String name;


        for (File band : database.listFiles()) {
            for (File song : band.listFiles()) {
                for (File bar : song.listFiles()) {
                    name = band.getName() + "/" + song.getName() + "/" + bar.getName();
                    datapoint = new Sequence(bar,16);
                    DistanceMetricResult currentResult;
                    int distance;
                    switch (metricType) {
                        case DistanceConstants.CYCLIC_EDIT:
                            distance = twoDimensionalCyclicEditDistance(query, datapoint);
                            break;
                        case DistanceConstants.CYCLIC_HAMMING:
                            distance = twoDimensionalCyclicHammingDistance(query, datapoint);
                            break;
                        case DistanceConstants.STRAIGHT_EDIT:
                            distance = twoDimensionalEditDistance(query, datapoint, 0);
                            break;
                        case DistanceConstants.STRAIGHT_HAMMING:
                            distance = twoDimensionalHammingDistance(query, datapoint, 0);
                            break;
                        default:
                            throw new InvalidDistanceMetric(metricType + " is not a valid Distance Metric");
                    }




                    results.add(new DistanceMetricResult(metricType,distance,name));
                    /*if(!full){
                        results[largest] = new DistanceMetricResult(metricType,distance, name);
                        if(++largest==results.length){
                            full = true;
                            largest = 0;
                        }
                    } else {
                        if(!seal){
                            for(int i=0; i<results.length; i++){
                                if (results[largest].getDistance()<results[i].getDistance()){
                                    largest = i;
                                }
                            }
                            if(results[largest].getDistance()>distance){
                                results[largest] = new DistanceMetricResult(metricType,distance,name);
                            } else {
                                seal = true;
                            }
                        } else {
                            if(results[largest].getDistance()>distance) {
                                results[largest] = new DistanceMetricResult(metricType, distance, name);
                                seal = false;
                            }
                        }
                    }*/

                }
            }
        }



        System.out.println("\t \t \t\t returning topmatches");
        return results;
    }

    public static void main(String[] args) throws IOException, InvalidDistanceMetric{
        MidiLoader midiLoader = new MidiLoader();

        midiLoader.loadMidiFile("../StudyData/Demo/Demo1.mid");
        midiLoader.printCsv("../StudyData/Demo/Demo.txt", midiLoader.onEventsToCsvMidiEvent());
        DrumNoteDeltaSequences d = midiLoader.buildDrumNoteDeltaSequences();
        DrumNoteDeltaSuffixTree st = new DrumNoteDeltaSuffixTree(d);

        Sequence test = new Sequence("src/main/resources/Database/Beatles/SheLovesYouTRIAL.txt/0.txt",16);




        Sequence query = st.getBestSequence();
        query.writeToFile(new PrintWriter(new BufferedWriter(
                new FileWriter("../StudyData/Demo/DemoInferredSequence.txt"))));
        ResultListArray hamming = topMatches(5,DistanceConstants.STRAIGHT_HAMMING,query);
        ResultListArray edit = topMatches(5,DistanceConstants.STRAIGHT_EDIT,query);
        ResultListArray cHamming = topMatches(5, DistanceConstants.CYCLIC_HAMMING,query);
        ResultListArray cEdit = topMatches(5, DistanceConstants.CYCLIC_EDIT,query);

        hamming.toCSV("../StudyData/Demo/DemoHammingDistances.txt");
        edit.toCSV("../StudyData/Demo/DemoEditDistances.txt");
        cHamming.toCSV("../StudyData/Demo/DemoCyclicHammingDistances.txt");
        cEdit.toCSV("../StudyData/Demo/DemoCycylicHammingDistances.txt");
        int hammingD = twoDimensionalHammingDistance(test,query,0);
        int editD = twoDimensionalEditDistance(test,query,0);
        int cHammingD = twoDimensionalCyclicHammingDistance(test,query);
        int cEditD = twoDimensionalCyclicEditDistance(test,query);

        System.out.print("End");



    }
}
