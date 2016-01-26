package uk.ac.cam.ba325.Tab.Translation.FSM;

import uk.ac.cam.ba325.Tab.Translation.Lexer;

import java.util.List;

/**
 * Created by biko on 15/01/16.
 */
public class SequenceParsingFSM {

    private State currentState;

    private List<Lexer.Token> tokenStream;

    public SequenceParsingFSM(List<Lexer.Token> tokens){
        tokenStream = tokens;
    }



}
