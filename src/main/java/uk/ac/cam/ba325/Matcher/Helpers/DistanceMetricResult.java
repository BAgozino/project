package uk.ac.cam.ba325.Matcher.Helpers;

/**
 * Created by root on 13/04/16.
 */
public class DistanceMetricResult {

    public final String distanceType;
    private int distance;
    private String matchInfo;

    public DistanceMetricResult(String distanceType, int distance, String matchInfo) {
        this.distanceType = distanceType;
        this.distance = distance;
        this.matchInfo = matchInfo;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(String matchInfo) {
        this.matchInfo = matchInfo;
    }
}
