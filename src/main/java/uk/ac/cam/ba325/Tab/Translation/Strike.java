package uk.ac.cam.ba325.Tab.Translation;


/**
 * Created by biko on 13/01/16.
 */
public class Strike {

    public static final Strike HIT = new Strike(1);
    public static final Strike REST = new Strike(0);
    private int value;

    public Strike(int value){
        this.value = value;
    }
    public Strike(Lexer.Token token) throws  Exception{
        if(token.type == Lexer.TokenType.BEAT){
            value = 1;
        }else if(token.type == Lexer.TokenType.REST){
            value = 0;
        }else{
            value = 0;
        }
    }


    public int getValue(){
        return value;
    }
    public void setValue(int value){
        this.value = value;
    }
    @Override
    public String toString(){
        return String.valueOf(value);
    }
}
