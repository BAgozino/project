package uk.ac.cam.ba325.Tab.Translation;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Lexer {
    public static enum TokenType {
        // Token types cannot have underscores
        INSTRUMENT("H|S|B"), TRACKDIVIDER("\\|"), BEAT("x|o"), REST("-"),NEWLINE("\n"), WHITESPACE("[ \t\f\r]+");

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
        StringBuffer tokenPatternsBuffer = new StringBuffer();
        for (TokenType tokenType : TokenType.values())
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

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
                "\n" +
                "H|x---x-x-x---x-x-|\n" +
                "S|o---o-------o---|\n" +
                "B|o---o---oo--o---|";

        // Create tokens and print them
        ArrayList<Token> tokens = lex(input);
        PreParser.removeComments(tokens);
        ArrayList<ArrayList<Token>> groupedTokens = PreParser.groupTracks(tokens);
        for (Token token : tokens)
            System.out.println(token);
    }
}