package uk.ac.cam.ba325.Matcher.Helpers;

import java.util.Comparator;

/**
 * Created by root on 14/04/16.
 */
public class DistanceMetricComparator implements Comparator<DistanceMetricResult> {

    public int compare(DistanceMetricResult d1,DistanceMetricResult d2){
        int retVal=0;
        if(d1.getDistance()>d2.getDistance()){
            retVal = 1;
        }else if(d1.getDistance()<d2.getDistance()){
            retVal = -1;
        }
        return retVal;
    }

}
