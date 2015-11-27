package uk.ac.cam.ba325.Tab.Translation;

import java.util.ArrayList;

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

        return groupedTracks;
    }


}
