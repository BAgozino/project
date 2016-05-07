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
                    try{
                        if (tokens.get(i+1).type != Lexer.TokenType.TRACKDIVIDER){
                            tokens.remove(i);
                            i--;
                            lineHasInstrument = false;

                        }

                    }catch (IndexOutOfBoundsException e){
                        tokens.remove(i);
                        i--;
                        lineHasInstrument = false;

                    }

                    break;
                }
                case TRACKDIVIDER:{
                    if (!lineHasInstrument){
                        tokens.remove(i);
                        i--;
                    }
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
        ArrayList<Lexer.Token> currentList = new ArrayList<>();
        for(Lexer.Token token :tokens){

            if (token.type == Lexer.TokenType.NEWLINE){
                if(!currentList.isEmpty()) {
                    instrumentTracks.add(new ArrayList<Lexer.Token>(currentList));
                }
                currentList = new ArrayList<>();
            }else{
                currentList.add(token);
            }
        }

        for(int i=0; i<instrumentTracks.size()-1; i++){
            if(instrumentTracks.get(i).size()!=instrumentTracks.get(i+1).size()){
                throw new ParseException("lengths are off, skipping the whole line",0);
            }
        }
        if(!currentList.isEmpty()) {
            instrumentTracks.add(currentList);
        }
        return instrumentTracks;


    }

    public static ArrayList<ArrayList<Lexer.Token>> splitLineIntoSequences(ArrayList<Lexer.Token> tokens){
        ArrayList<ArrayList<Lexer.Token>> instrumentTracks = new ArrayList<>();
        ArrayList<Lexer.Token> currentList = new ArrayList<>();
        for(Lexer.Token token :tokens){

            if (token.type == Lexer.TokenType.TRACKDIVIDER){
                instrumentTracks.add(new ArrayList<Lexer.Token>(currentList));
                currentList = new ArrayList<>();
            }else{
                currentList.add(token);
            }
        }

        return instrumentTracks;

    }




}
