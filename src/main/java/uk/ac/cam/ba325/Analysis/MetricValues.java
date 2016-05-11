package uk.ac.cam.ba325.Analysis;

/**
 * Created by root on 09/05/16.
 */
public class MetricValues {



      private int distance;
      private  int rankStart;
      private  int rankEnd;


    public String toString(){
        return distance+","+rankStart+","+rankEnd;
    }

    public MetricValues(int distance, int rankStart, int rankEnd) {
        this.distance = distance;
        this.rankStart = rankStart;
        this.rankEnd = rankEnd;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getRankStart() {
        return rankStart;
    }

    public void setRankStart(int rankStart) {
        this.rankStart = rankStart;
    }

    public int getRankEnd() {
        return rankEnd;
    }

    public void setRankEnd(int rankEnd) {
        this.rankEnd = rankEnd;
    }
}
