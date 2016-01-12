package uk.ac.cam.ba325.Tab.Translation;

import java.io.*;
import java.nio.file.FileSystemException;
import java.util.ArrayList;

/**
 * Created by biko on 20/11/15.
 */
public class Parser {

    private File m_inputFile;
    private String m_outputDirectory;


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

    public void parseToFile(ArrayList<ArrayList<Lexer.Token>> tokenTracks) throws FileSystemException{
        int trackNumber = 0;
        File directory = new File(m_outputDirectory);
        if(!directory.mkdirs()){
            throw new FileSystemException(m_outputDirectory,"none","Failed to mkdirs");
        }

        String trackPathName;

        //TODO add record song table

        for(ArrayList<Lexer.Token> track : tokenTracks){
            trackPathName = m_outputDirectory+"/"+String.valueOf(trackNumber)+".txt";
            //todo translate track to string
            //todo save string to trackPathName file
            //todo record in track database
        }
    }

    public void writeToFile(ArrayList<Lexer.Token> track){

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
