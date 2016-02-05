package uk.ac.cam.ba325.Tab.Translation;

import uk.ac.cam.ba325.Tab.Instrument.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by biko on 27/11/15.
 */
public class PreParser {


    public static void removeComments(ArrayList<Lexer.Token> tokens){
        boolean lineHasInstrument = false;


        for(int i=0; i<tokens.size(); i++){
            Lexer.Token token = tokens.get(i);

            SWITCH:
            switch (token.type){
                case INSTRUMENT:{
                    lineHasInstrument = true;
                    if (tokens.get(i+1).type != Lexer.TokenType.TRACKDIVIDER){
                        tokens.remove(i);
                        i--;
                        lineHasInstrument = false;

                    }

                    break;
                }
                case TRACKDIVIDER:{
                    //nil
                    break;
                }
                case BEAT:{
                    if (!lineHasInstrument){
                        tokens.remove(i);
                        i--;

                    }
                    break;
                }
                case REST:{
                    if (!lineHasInstrument){
                        tokens.remove(i);
                        i--;

                    }
                    break;
                }
                case NEWLINE:{
                    lineHasInstrument=false;
                    break;
                }
                case WHITESPACE:{
                    break;
                }
            }


        }
    }


    public static ArrayList<ArrayList<Lexer.Token>> groupTracks(ArrayList<Lexer.Token> tokens){
        boolean buildingTrack = false;
        int newLineCount = 0;
        ArrayList<ArrayList<Lexer.Token>> groupedTracks = new ArrayList<ArrayList<Lexer.Token>>();
        ArrayList<Lexer.Token> currentTokens = new ArrayList<Lexer.Token>();

        for(Lexer.Token token:tokens){
            if (token.type == Lexer.TokenType.NEWLINE){
                newLineCount++;
                if ((newLineCount >1)&&(buildingTrack)){
                    groupedTracks.add(currentTokens);
                    currentTokens = new ArrayList<Lexer.Token>();
                    newLineCount = 0;
                    buildingTrack = false;

                }else if (buildingTrack){
                    currentTokens.add(token);
                    newLineCount = 0;

                }
            } else {
                newLineCount=0;
                currentTokens.add(token);
                buildingTrack = true;
            }
        }
        if (!currentTokens.isEmpty()){
            groupedTracks.add(currentTokens);
        }


        for(int i = 0; i<groupedTracks.size(); i++ ){
            currentTokens = groupedTracks.get(i);

        }

        return groupedTracks;
    }


    public static ArrayList<ArrayList<Lexer.Token>> splitInstruments(ArrayList<Lexer.Token> tokens) throws ParseException{
        ArrayList<ArrayList<Lexer.Token>> instrumentTracks = new ArrayList<>();
        ArrayList<Lexer.Token> singleInstrument = new ArrayList<>();

        for(Lexer.Token token:tokens){
            if(token.type == Lexer.TokenType.NEWLINE) {
                instrumentTracks.add(new ArrayList<Lexer.Token>(singleInstrument));
                singleInstrument = new ArrayList<>();
            } else {
                singleInstrument.add(token);
            }
        }

        for (int i=0; i<instrumentTracks.size()-1; i++){
            if (instrumentTracks.get(i).size() != instrumentTracks.get(i+1).size()){
                throw new ParseException("Instrument Tracks are of uneven length",0);
            }
        }

        return instrumentTracks;


    }

    /**
     *
     * @assert lists within tokens are of even length
     * @param tokens a list of one block of lines
     * @return
     */
    public static ArrayList<Sequence> sequences(ArrayList<ArrayList<Lexer.Token>> tokens){
        ArrayList<Sequence> sequences = new ArrayList<>();

        Instrument[] instruments = new Instrument[tokens.size()];
        ArrayList<

        for (int i = 0; i< tokens.size(); i++){
            Lexer.Token token = tokens.get(i).get(0);

            if((token.data.equals("HH"))||(token.data.equals("H"))){
                instruments[i] = new HighHat();
            } else if(token.data.equals("CC")){
                instruments[i] = new CrashCymbal();
            } else if(token.data.equals("Rd")){
                instruments[i] = new RideCymbal();
            } else if((token.data.equals("SN"))||(token.data.equals("S"))){
                instruments[i] = new SnareDrum();
            } else if(token.data.equals("B")){
                instruments[i] = new BassDrum();
            } else if(token.data.equals("T1")){
                instruments[i] = new HighTom();
            } else if(token.data.equals("T2")){
                instruments[i] = new LowTom();
            } else if(token.data.equals("FT")){
                instruments[i] = new FloorTom();
            }

        }

        for (int i=0; i<tokens.get(0).size(); i++){

        }

    }


    private static ArrayList<Sequence> createSequence(Instrument[] instrument,
                                                       ArrayList<ArrayList<Lexer.Token>> tokens){
        for
    }
}
