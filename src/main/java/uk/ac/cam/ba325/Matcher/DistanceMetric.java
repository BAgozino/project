package uk.ac.cam.ba325.Matcher;

import uk.ac.cam.ba325.Tab.Translation.Sequence;
import uk.ac.cam.ba325.Tab.Translation.Strike;

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
    public static int twoDimensionalEditDistance(Sequence sequence1, Sequence sequence2,int offset){
        int returnValue = 0;
        returnValue += editDistance(sequence1.getBassDrums(),sequence2.getBassDrums());
        returnValue += editDistance(sequence1.getCrashCymbals(),sequence2.getCrashCymbals());
        returnValue += editDistance(sequence1.getFloorToms(),sequence2.getFloorToms());
        returnValue += editDistance(sequence1.getHighHats(),sequence2.getHighHats());
        returnValue += editDistance(sequence1.getHighToms(),sequence2.getHighToms());
        returnValue += editDistance(sequence1.getLowToms(),sequence2.getLowToms());
        returnValue += editDistance(sequence1.getRideCymbals(),sequence2.getRideCymbals());
        returnValue += editDistance(sequence1.getSnareDrums(),sequence2.getSnareDrums());

        return returnValue;
    }

    public static int editDistance(List<Strike> sequence1,List<Strike> sequence2){
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
                if(sequence1.get(i).getValue() == sequence2.get(j).getValue()){
                    distances[i][j] = distances[i-1][j-1];
                }else{
                    distances[i][j] = min(distances[i-1][j] +1,distances[i][j-1] +1,distances[i-1][j-1] +1);

                }
            }
        }

        return distances[m][n];
    }

    public static int twoDimensionalHammingDistance(Sequence sequence1, Sequence sequence2){
        int returnValue =0;
        returnValue += hammingDistance(sequence1.getBassDrums(),sequence2.getBassDrums());
        returnValue += hammingDistance(sequence1.getCrashCymbals(),sequence2.getCrashCymbals());
        returnValue += hammingDistance(sequence1.getFloorToms(),sequence2.getFloorToms());
        returnValue += hammingDistance(sequence1.getHighHats(),sequence2.getHighHats());
        returnValue += hammingDistance(sequence1.getHighToms(),sequence2.getHighToms());
        returnValue += hammingDistance(sequence1.getLowToms(),sequence2.getLowToms());
        returnValue += hammingDistance(sequence1.getRideCymbals(),sequence2.getRideCymbals());
        returnValue += hammingDistance(sequence1.getSnareDrums(),sequence2.getSnareDrums());

        return returnValue;
    }

    public static int hammingDistance(List<Strike> sequence1, List<Strike> sequence2 ){
        int shorter = Math.min(sequence1.size(),sequence2.size());
        int longer = Math.max(sequence1.size(),sequence2.size());

        int returnValue = 0;

        for(int i=0; i<shorter; i++){
            if(sequence1.get(i) != sequence2.get(i)){
                returnValue++;
            }
        }

        returnValue += longer-shorter;

        return returnValue;
    }

    private static int min(int deletion, int insertion, int substitution){
        return Math.min(deletion,Math.min(insertion,substitution));

    }
}
