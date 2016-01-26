package uk.ac.cam.ba325.Tab.Translation;



import java.util.ArrayList;

/**
 * Created by biko on 19/01/16.
 *
 * Holds and instrument and the beats for it
 */
public class InstrumentTrack {

    private Lexer.Token instrument;

    private ArrayList<Lexer.Token> beats;


    /**
     *
     * @param tokens the track seperators are essentially removed and thats it.
     */
    public InstrumentTrack(ArrayList<Lexer.Token> tokens){
        instrument = tokens.get(0);
        for(Lexer.Token token:tokens){
            if((token.type == Lexer.TokenType.REST )||
                    (token.type == Lexer.TokenType.BEAT)){
                beats.add(token);
            }
        }
    }
}
