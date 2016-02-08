package uk.ac.cam.ba325.Tab.Translation;

import java.io.*;
import java.nio.file.FileSystemException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import uk.ac.cam.ba325.Tab.Translation.Exceptions.AlreadySetException;
import uk.ac.cam.ba325.Tab.Translation.Sequence;

/**
 * Created by biko on 20/11/15.
 */
public class Parser {

    private File m_inputFile;
    private String m_outputDirectory;
    private static final int RESOLUTION = 16;

    public Parser(File inputFile, String outputDirectory){
        m_inputFile = inputFile;
        m_outputDirectory = outputDirectory;
    }

    public ArrayList<Lexer.Token> lex(){
        ArrayList<Lexer.Token> tokens = new ArrayList<Lexer.Token>();

        try ( BufferedReader bufferedReader =
                      new BufferedReader(new FileReader(m_inputFile));){
            for(String line; (line = bufferedReader.readLine()) != null; ){
                tokens.addAll(Lexer.lex(line));
            }
        } catch (FileNotFoundException fileNotFoundException) {
            //TODO

        } catch (IOException ioe) {
            //TODO

        }
        return tokens;

    }

    public ArrayList<ArrayList<Lexer.Token>> preParse(ArrayList<Lexer.Token> lexedTokens){
        PreParser.removeComments(lexedTokens);
        return PreParser.groupTracks(lexedTokens);
    }

    public List<Sequence> createSequences(ArrayList<ArrayList<Lexer.Token>> groupedTracks)
            throws ParseException, AlreadySetException{
        ArrayList<ArrayList<ArrayList<Lexer.Token>>> splitInstruments = new ArrayList<>();
        for(ArrayList<Lexer.Token> track : groupedTracks){
            try{
                splitInstruments.add(PreParser.splitInstruments(track));
            }catch(ParseException pe){
                //skip
                pe.printStackTrace();
            }

        }

        ArrayList<ArrayList<ArrayList<ArrayList<Lexer.Token>>>> readyToCreateSequence = new ArrayList<>();
        for(ArrayList<ArrayList<Lexer.Token>> tracks : splitInstruments){
            ArrayList<ArrayList<ArrayList<Lexer.Token>>> splitLines = new ArrayList<>();
            for(ArrayList<Lexer.Token> line : tracks){
                splitLines.add(PreParser.splitLineIntoSequences(line));
            }
            readyToCreateSequence.add(splitLines);
        }


        ArrayList<Sequence> sequences = new ArrayList<>();
        String instrument;
        boolean[] sequenceAlreadyCreated;
        //TODO: loop over readyToCreateSequences and make the sequences which you can add to sequences.
        for(ArrayList<ArrayList<ArrayList<Lexer.Token>>> track : readyToCreateSequence){
            sequenceAlreadyCreated = new boolean[track.get(0).size()-1];
            List<Sequence> currentTrack = new LinkedList<>();
            for(ArrayList<ArrayList<Lexer.Token>> line : track){
                instrument = line.get(0).get(0).data;

                for(int i = 1; i<line.size(); i++){
                    ArrayList<Lexer.Token> sequenceComponent = line.get(i);
                    if (sequenceComponent.size() == RESOLUTION) {
                        if (sequenceAlreadyCreated[i - 1]) {
                            currentTrack.get(i - 1).fillLine(instrument, sequenceComponent);
                        } else {
                            currentTrack.add(new Sequence(RESOLUTION));
                            sequenceAlreadyCreated[i - 1] = true;
                            currentTrack.get(i - 1).fillLine(instrument, sequenceComponent);
                        }
                    } else {
                        //nil
                    }

                }
            }
            sequences.addAll(currentTrack);
        }
        return sequences;
    }

    public void parseToFile(List<Sequence> sequences) throws FileSystemException, IOException{
        int trackNumber = 0;
        File directory = new File(m_outputDirectory);
        if(!directory.mkdirs()){
            throw new FileSystemException(m_outputDirectory,"none","Failed to mkdirs");
        }

        String trackPathName;

        //TODO add record song table

        for(Sequence sequence : sequences){
            trackPathName = m_outputDirectory+"/"+String.valueOf(trackNumber)+".txt";
            trackNumber++;
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(trackPathName)));
            sequence.writeToFile(writer);
            writer.flush();
            //todo 1. translate track to string
            //todo 2. save string to trackPathName file
            //todo 3. record in track database
        }
    }



    public File getM_inputFile() {
        return m_inputFile;
    }

    public void setM_inputFile(File m_inputFile) {
        this.m_inputFile = m_inputFile;
    }

    public String getM_outputDirectory() {
        return m_outputDirectory;
    }

    public void setM_outputDirectory(String m_outputDirectory) {
        this.m_outputDirectory = m_outputDirectory;
    }
}
