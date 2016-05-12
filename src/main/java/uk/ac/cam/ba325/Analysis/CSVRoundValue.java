package uk.ac.cam.ba325.Analysis;

/**
 * Created by root on 11/05/16.
 */
public class CSVRoundValue {
    int hamming;
    int edit;
    int cHamming;
    int cEdit;
    double roundValue;

    public String toString(){
        return hamming+","+edit+","+cHamming+","+cEdit+","+roundValue+"\n";
    }

    public CSVRoundValue(int hamming, int edit, int cHamming, int cEdit,double roundValue) {
        this.hamming = hamming;
        this.edit = edit;
        this.cHamming = cHamming;
        this.cEdit = cEdit;
        this.roundValue = roundValue;
    }

    public double getRoundValue() {
        return roundValue;
    }

    public void setRoundValue(int roundValue) {
        this.roundValue = roundValue;
    }

    public int getHamming() {
        return hamming;
    }

    public void setHamming(int hamming) {
        this.hamming = hamming;
    }

    public int getEdit() {
        return edit;
    }

    public void setEdit(int edit) {
        this.edit = edit;
    }

    public int getcHamming() {
        return cHamming;
    }

    public void setcHamming(int cHamming) {
        this.cHamming = cHamming;
    }

    public int getcEdit() {
        return cEdit;
    }

    public void setcEdit(int cEdit) {
        this.cEdit = cEdit;
    }
}
