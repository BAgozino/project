package uk.ac.cam.ba325.Matcher.Helpers;

import uk.ac.cam.ba325.Analysis.MetricValues;

import java.util.LinkedList;

/**
 * Created by root on 11/05/16.
 */
public class ResultListArray {

    private LinkedList<String>[] resultArray = new LinkedList[8*16];
    private int size=0;

    public void add(DistanceMetricResult adding){
        if(resultArray[adding.getDistance()] == null){
            resultArray[adding.getDistance()] = new LinkedList<>();
        }
        resultArray[adding.getDistance()].add(adding.getMatchInfo());
        size++;
    }

    public MetricValues getRank(int distance){
        int first = 0;
        int last = 0;
        for(int i =0; i<distance;i++){
            if(resultArray[i] != null) {
                first += resultArray[i].size();
            }
        }
        if(resultArray[distance] !=null) {
            last = first + resultArray[distance].size();
        }

        return new MetricValues(distance,first,last);
    }

    public int getSize() {
        return size;
    }
}
