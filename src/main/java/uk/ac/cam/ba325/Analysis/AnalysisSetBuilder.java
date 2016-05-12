package uk.ac.cam.ba325.Analysis;

import uk.ac.cam.ba325.Matcher.DistanceMetric;
import uk.ac.cam.ba325.Matcher.Helpers.DistanceConstants;
import uk.ac.cam.ba325.Matcher.Helpers.InvalidDistanceMetric;
import uk.ac.cam.ba325.Matcher.Helpers.ResultListArray;
import uk.ac.cam.ba325.Midi.DrumNoteDeltaSequences;
import uk.ac.cam.ba325.Midi.MidiLoader;
import uk.ac.cam.ba325.Midi.Quantisation.DrumNoteDeltaSuffixTree;
import uk.ac.cam.ba325.Midi.TickDelta;
import uk.ac.cam.ba325.Tab.Translation.Sequence;

import java.io.*;
import java.util.*;

/**
 * Created by root on 09/05/16.
 */
public class AnalysisSetBuilder {

    private Sequence[] matchAgainst;

    public AnalysisSetBuilder() throws IOException{

        matchAgainst = new Sequence[10];
        matchAgainst[0] = new Sequence("src/main/resources/Database/Nirvana/SmellsLikeTeamSpiritTRIAL.txt/0.txt",16);
        matchAgainst[1] = new Sequence("src/main/resources/Database/Metallica/MasterOfPuppetsTRIAL.txt/0.txt",16);
        matchAgainst[2] = new Sequence("src/main/resources/Database/rageagainstthemachine/KillingInTheNameOfTRIAL.txt/0.txt",16);
        matchAgainst[3] = new Sequence("src/main/resources/Database/Aerosmith/IDontWantToMissAThingTRIAL.txt/0.txt",16);
        matchAgainst[4] = new Sequence("src/main/resources/Database/pinkfloyd/AnotherBrickInAWallTRIAL.txt/0.txt",16);
        matchAgainst[5] = new Sequence("src/main/resources/Database/eminem/TheRealSlimShadyTRIAL.txt/0.txt",16);
        matchAgainst[6] = new Sequence("src/main/resources/Database/Radiohead/KarmaPoliceTRIAL.txt/0.txt",16);
        matchAgainst[7] = new Sequence("src/main/resources/Database/linkinpark/CrawlingTRIAL.txt/0.txt",16);
        matchAgainst[8] = new Sequence("src/main/resources/Database/pearljam/JeremyTRIAL.txt/0.txt",16);
        matchAgainst[9] = new Sequence("src/main/resources/Database/Beatles/SheLovesYouTRIAL.txt/0.txt",16);
    }

    LinkedList<CSVDistanceEval> one = new LinkedList<>();
    LinkedList<CSVDistanceEval> two = new LinkedList<>();
    LinkedList<CSVDistanceEval> three = new LinkedList<>();

    public void buildCSV(String participant) throws InvalidDistanceMetric{
        System.out.println("Starting buildCSV");
        //for all String file name children in participant
            //if matches the *.mid regex
                //create RecordingFile
                //switch on dataset
                    //0 - nil
                    //1 - skip creating the suffix tree and match directly
                    //2 - create suffix tree and match
                    //3 - create suffix tree and match

        File directory = new File(participant);
        MidiLoader midiLoader = new MidiLoader();

        Sequence query;
        DrumNoteDeltaSuffixTree st;
        RecordingFile record ;
        Sequence test;
        String path;
        for(File file : directory.listFiles()){
            path = file.getAbsolutePath();
            if(path.endsWith(".mid")){
                System.out.println("\t Starting for file:" +path);
                try {
                    record = new RecordingFile(path);
                    test = matchAgainst[record.getTabNumber()];
                    midiLoader.loadMidiFile(path);
                    DrumNoteDeltaSequences d = midiLoader.buildDrumNoteDeltaSequences();

                    switch (record.getDatasetNumber()) {
                        case 0:
                            //nil
                            System.out.print("\t done file: "+path);
                            break;
                        case 1:
                            query = d.getSequence();
                            one.add(populateCSV(query,test,record.getTabNumber()));
                            System.out.print("\t done file: "+path);
                            break;
                        case 2:
                            st = new DrumNoteDeltaSuffixTree(d);
                            query = st.getBestSequence();
                            two.add(populateCSV(query,test,record.getTabNumber()));
                            System.out.print("\t done file: "+path);
                            break;
                        case 3:
                            st = new DrumNoteDeltaSuffixTree(d);
                            query = st.getBestSequence();
                            three.add(populateCSV(query,test,record.getTabNumber()));
                            System.out.print("\t done file: "+path);
                            break;


                    }


                } catch (Exception ioe){
                    System.out.print("The following file was skipped: "+path);
                    ioe.printStackTrace();
                }
            }
        }

    }

    public boolean printCsv(String path, List csvs){
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path)));

            for (Object csv : csvs) {
                writer.write(csv.toString());
            }
            writer.flush();
        } catch (IOException ioe){
            return false;
        }
        return true;
    }

    private CSVDistanceEval populateCSV(Sequence query,Sequence against,int tab)throws IOException, InvalidDistanceMetric{
        System.out.println("\t \t Starting populateCSV");


        CSVDistanceEval retval = new CSVDistanceEval();


        retval.setTab(tab);

        retval.setHamming(getMetricValue(DistanceMetric.twoDimensionalHammingDistance(query,against,0),
                DistanceMetric.topMatches(0,DistanceConstants.STRAIGHT_HAMMING,query)));
        System.out.println("\t \t done hamming");

        retval.setEdit(getMetricValue(DistanceMetric.twoDimensionalEditDistance(query,against,0),
                DistanceMetric.topMatches(0,DistanceConstants.STRAIGHT_HAMMING,query)));
        System.out.println("\t \t done edit");


        retval.setcEdit(getMetricValue(DistanceMetric.twoDimensionalCyclicEditDistance(query,against),
                DistanceMetric.topMatches(0,DistanceConstants.CYCLIC_EDIT,query)));
        System.out.println("\t\t done cEdit");

        retval.setcHamming(getMetricValue(DistanceMetric.twoDimensionalCyclicHammingDistance(query,against),
                DistanceMetric.topMatches(0,DistanceConstants.CYCLIC_HAMMING,query)));
        System.out.println("\t\t done cHamming");
        return retval;

    }



    private MetricValues getMetricValue(int queryD, ResultListArray topmatches){
        System.out.println("\t \t \tStarting getMetricValue");
        int i=0;






//        Multiset.Entry<DistanceMetricResult> result = topmatches.pollFirstEntry();
//        while(result != null){
//            if(result.getElement().getDistance() == queryD){
//                first = i;
//                last += result.getCount();
//                break;
//            }
//            i+=result.getCount();
//        }
        MetricValues ret = topmatches.getRank(queryD);
        System.out.println("\t\t\t\t returning with: "+ret);
        return ret;
    }

    public int numberOfDuplicatesInDatabase(String metricType){
        File database = new File("src/main/resources/Database");
        int numberRemoved = 0;
        Sequence first;
        Sequence second;
        for (File band : database.listFiles()) {
            for (File song : band.listFiles()) {
                ArrayList<File> bars = new ArrayList<File>(Arrays.asList(song.listFiles()));

                for (int i=0; i<bars.size(); i++) {
                    try {
                        first = new Sequence(bars.get(i), 16);
                        for (int j = i + 1; j < bars.size(); j++) {
                            second = new Sequence(bars.get(j), 16);
                            switch (metricType) {
                                case DistanceConstants.STRAIGHT_HAMMING:
                                    if (DistanceMetric.twoDimensionalHammingDistance(first, second, 0) == 0) {
                                        bars.remove(j);
                                        numberRemoved++;
                                    }
                                    break;
                                case DistanceConstants.CYCLIC_HAMMING:
                                    if (DistanceMetric.twoDimensionalCyclicHammingDistance(first, second) == 0) {
                                        bars.remove(j);
                                        numberRemoved++;
                                    }
                                    break;
                            }
                        }
                    }catch (IOException e){
                        //nil
                    }
                }
            }
        }
        return numberRemoved;
    }



    public LinkedList<CSVRoundValue> roundValueDatasetBuild(String outputPath,String participant){
        File directory = new File(participant);
        MidiLoader midiLoader = new MidiLoader();
        LinkedList<CSVRoundValue> roundValues = new LinkedList<>();
        Sequence query;
        DrumNoteDeltaSuffixTree st;
        RecordingFile record ;
        Sequence test;
        String path;
        for(double i=1.0; i<50; i++) {
            TickDelta.ROUNDVALUE = i;
            for (File file : directory.listFiles()) {
                path = file.getAbsolutePath();
                if (path.endsWith(".mid")) {
                    System.out.println("\t Starting for file:" + path);
                    try {
                        record = new RecordingFile(path);
                        test = matchAgainst[record.getTabNumber()];
                        midiLoader.loadMidiFile(path);

                        DrumNoteDeltaSequences d = midiLoader.buildDrumNoteDeltaSequences();

                        switch (record.getDatasetNumber()) {
                            case 0:
                                //nil
                                System.out.print("\t done file: " + path);
                                break;
                            case 1:
                                //nil

                                System.out.print("\t done file: " + path);
                                break;
                            case 2:
                                st = new DrumNoteDeltaSuffixTree(d);
                                query = st.getBestSequence();
                                roundValues.add(new CSVRoundValue(
                                        DistanceMetric.twoDimensionalHammingDistance(test,query,0),
                                        DistanceMetric.twoDimensionalEditDistance(test,query,0),
                                        DistanceMetric.twoDimensionalCyclicHammingDistance(test,query),
                                        DistanceMetric.twoDimensionalCyclicEditDistance(test,query),
                                        i
                                ));
                                System.out.print("\t done file: " + path);
                                break;
                            case 3:
                                //nil
                                System.out.print("\t done file: " + path);
                                break;


                        }
                    } catch (Exception e){
                        //nil
                    }
                }
            }


        }

        //printCsv(outputPath,roundValues);
        return roundValues;
    }



    public static void main(String[] args)throws IOException, InvalidDistanceMetric{
        String path ="/home/biko/Projects/StudyData/Sean";

        AnalysisSetBuilder analysisSetBuilder = new AnalysisSetBuilder();

        int dupsCyclic = analysisSetBuilder.numberOfDuplicatesInDatabase(DistanceConstants.CYCLIC_HAMMING);
        int dups = analysisSetBuilder.numberOfDuplicatesInDatabase(DistanceConstants.STRAIGHT_HAMMING);
        System.out.println("testing");
        try {
            analysisSetBuilder.buildCSV(path);
        } catch (Exception e){
            System.out.print("This is just for testing");
        }
        analysisSetBuilder.printCsv("/home/biko/Projects/StudyData/results/Sean1.txt",analysisSetBuilder.one);
        analysisSetBuilder.printCsv("/home/biko/Projects/StudyData/results/Sean2.txt",analysisSetBuilder.two);
        analysisSetBuilder.printCsv("/home/biko/Projects/StudyData/results/Sean3.txt",analysisSetBuilder.three);


        path = "/home/biko/Projects/StudyData/Sean";
        try {
            analysisSetBuilder.buildCSV(path);
        } catch (Exception e){
            System.out.print("This is just for testing");
        }
        analysisSetBuilder.printCsv("/home/biko/Projects/StudyData/results/Jared1.txt",analysisSetBuilder.one);
        analysisSetBuilder.printCsv("/home/biko/Projects/StudyData/results/Jared2.txt",analysisSetBuilder.two);
        analysisSetBuilder.printCsv("/home/biko/Projects/StudyData/results/Jared3.txt",analysisSetBuilder.three);

        LinkedList<CSVRoundValue> values = analysisSetBuilder.roundValueDatasetBuild("/home/biko/Projects/StudyData/results/RoundingFactorAnalysis/Jared.txt",
                path);
        values.addAll(analysisSetBuilder.roundValueDatasetBuild("/home/biko/Projects/StudyData/results/RoundingFactorAnalysis/Sean.txt",
                "/home/biko/Projects/StudyData/Sean"));

    }
}
