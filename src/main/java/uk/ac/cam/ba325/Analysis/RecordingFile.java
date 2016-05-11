package uk.ac.cam.ba325.Analysis;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 09/05/16.
 *
 * for extracting what the file name means
 */
public class RecordingFile extends File {

    private int datasetNumber;
    private int tabNumber;
    private int count;

    public String toString(){
        return tabNumber+"-"+datasetNumber+"-"+count;
    }

    public RecordingFile(String path) throws IOException{

        super(path);
        LinkedList<String> numbers = new LinkedList<>();
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(path);
        while (m.find()){
            numbers.add(m.group());
        }
        if(numbers.size()!=3){
            throw new IOException("The file is of incorrect naming conventions");
        }

        datasetNumber = Integer.valueOf(numbers.get(1));
        tabNumber = Integer.valueOf(numbers.get(0));
        count = Integer.valueOf(numbers.get(2));


    }

    public int getDatasetNumber() {
        return datasetNumber;
    }

    public void setDatasetNumber(int datasetNumber) {
        this.datasetNumber = datasetNumber;
    }

    public int getTabNumber() {
        return tabNumber;
    }

    public void setTabNumber(int tabNumber) {
        this.tabNumber = tabNumber;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
