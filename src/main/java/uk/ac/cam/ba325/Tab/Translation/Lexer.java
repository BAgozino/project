package uk.ac.cam.ba325.Tab.Translation;

import uk.ac.cam.ba325.Tab.Translation.Exceptions.AlreadySetException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Lexer {
    public static enum TokenType {
        // Token types cannot have underscores
        INSTRUMENT("HH|HF|H|CC|C|RD|R|SN|S|BD|B|T1|HT|T2|LT|FT|T"), TRACKDIVIDER("\\|"), BEAT("x|X|o|#|s|c|b|g|f|b|B|@"),
        REST("-"),NEWLINE("\n"), WHITESPACE("[ \t\f\r]+");

        public final String pattern;

        private TokenType(String pattern) {
            this.pattern = pattern;
        }
    }

    public static class Token {
        public TokenType type;
        public String data;

        public Token(TokenType type, String data) {
            this.type = type;
            this.data = data;
        }

        @Override
        public String toString() {
            return String.format("(%s %s)", type.name(), data);
        }
    }

    public static ArrayList<Token> lex(String input) {
        // The tokens to return
        ArrayList<Token> tokens = new ArrayList<Token>();

        // Lexer logic begins here
        StringBuilder tokenPatternsBuffer = new StringBuilder();
        for (TokenType tokenType : TokenType.values())
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)),Pattern.CASE_INSENSITIVE);

        // Begin matching tokens
        Matcher matcher = tokenPatterns.matcher(input);
        while (matcher.find()) {
            if (matcher.group(TokenType.INSTRUMENT.name()) != null) {
                tokens.add(new Token(TokenType.INSTRUMENT, matcher.group(TokenType.INSTRUMENT.name())));
                continue;
            } else if (matcher.group(TokenType.TRACKDIVIDER.name()) != null) {
                tokens.add(new Token(TokenType.TRACKDIVIDER, matcher.group(TokenType.TRACKDIVIDER.name())));
                continue;
            }else if(matcher.group(TokenType.BEAT.name()) != null) {
                tokens.add(new Token(TokenType.BEAT, matcher.group(TokenType.BEAT.name())));
                continue;
            } else if(matcher.group(TokenType.REST.name()) != null) {
                tokens.add(new Token(TokenType.REST, matcher.group(TokenType.REST.name())));
                continue;
            } else if(matcher.group(TokenType.NEWLINE.name()) != null){
                tokens.add(new Token(TokenType.NEWLINE, matcher.group(TokenType.NEWLINE.name())));
                continue;
            } else if (matcher.group(TokenType.WHITESPACE.name()) != null)
                continue;
        }

        return tokens;
    }

    public static void main(String[] args) {
        String input = "from- myguitartabs.com\n" +
                "band=Eminem\n" +
                "song=The Real Slim Shady\n" +
                "tabtype=drum\n" +
                "Tabbed By:Jeff Sawinski\n" +
                "E-mail:sawinsk@bolt.com \n" +
                "\n" +
                "H|x---x-x-x---x-x-|o---o---oo--o---|\n" +
                "S|o---o-------o---|o---o---oo--o---|\n" +
                "B|o---o---oo--o---|o---o---oo--o---|\n" +
                "Testing this sceneario\n" +
                "\n" +
                "Highhat Test\n" +
                "S|o---o---oo--o---|-xx-| what if comments are here xoxo?\n" +
                "\n" +
                "S o---o---oo--o---|";

        // Create tokens and print them
        ArrayList<Token> tokens = lex(input);
        Parser parser = new Parser(new File(""),"src/main/resources/Dataset/test");
        try {
            ArrayList<ArrayList<Token>> preParsed = parser.preParse(tokens);
            parser.parseToFile(parser.createSequences(parser.preParse(tokens)));
        }catch (ParseException pe){
            //// TODO: 06/02/16
            pe.printStackTrace();
        }catch (AlreadySetException ase){
            //// TODO: 06/02/16
            ase.printStackTrace();
            
        }catch (IOException ioe){
            //// TODO: 06/02/16

            ioe.printStackTrace();
        }
        ArrayList<ArrayList<Token>> groupedTokens = PreParser.groupTracks(tokens);
        for (Token token : tokens)
            System.out.println(token);
    }
}