package uk.ac.cam.ba325.Analysis;

import uk.ac.cam.ba325.Midi.Quantisation.Helpers.IntPointer;

/**
 * Created by root on 09/05/16.
 */
public class CSVDistanceEval {
    private MetricValues hamming;


    private MetricValues edit;


    private MetricValues cHamming;

    public String toString(){
        return toCsv();
    }
    public String toCsv(){
        return hamming.toString()+","+edit.toString()+","+cHamming.toString()+","+cEdit.toString()+","+tab+"\n";
    }

    private MetricValues cEdit;
    private int tab;
    public MetricValues getHamming() {
        return hamming;
    }

    public void setHamming(MetricValues hamming) {
        this.hamming = hamming;
    }

    public MetricValues getEdit() {
        return edit;
    }

    public void setEdit(MetricValues edit) {
        this.edit = edit;
    }

    public MetricValues getcHamming() {
        return cHamming;
    }

    public void setcHamming(MetricValues cHamming) {
        this.cHamming = cHamming;
    }

    public MetricValues getcEdit() {
        return cEdit;
    }

    public void setcEdit(MetricValues cEdit) {
        this.cEdit = cEdit;
    }

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }
}