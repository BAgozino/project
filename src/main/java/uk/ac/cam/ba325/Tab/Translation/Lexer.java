package uk.ac.cam.ba325.Tab.Translation;

import uk.ac.cam.ba325.Tab.Translation.Exceptions.AlreadySetException;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Lexer {
    public static enum TokenType {
        // Token types cannot have underscores
        INSTRUMENT("HH|HF|H|CC|C1|C2|C|RD|R|SN|S|BD|B|T1|HT|T2|LT|FT|T"), TRACKDIVIDER("\\|"), BEAT("x|X|o|0|#|f|@"),
        REST("-|g"),NEWLINE("\n"), WHITESPACE("[ \t\f\r]+");

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


    public static ArrayList<Token> tokeniseFile(File file) throws FileNotFoundException{//TODO
        ArrayList<Token> tokens = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            tokens.addAll(lex(scanner.nextLine()+"\n"));
        }
        return tokens;
    }
    public static void main(String[] args) throws Exception {
        String input = "From: MYguitartabs.com\n" +
                "Bad Religion\n" +
                "I Love My Computer\n" +
                "New America\n" +
                "Bobby Schayer\n" +
                "Tabbed by: TheBioLogic\n" +
                "\n" +
                "c=cross-stick\n" +
                "\n" +
                "\n" +
                "Intro:\n" +
                "\n" +
                "  |-----------Repeat x4-------------|\n" +
                "c |x---------------|----------------|\n" +
                "h |--x-x-x-x-x-x-x-|x-x-x-x-x-x-x-x-|\n" +
                "s |----s-------s---|----s-------s---|\n" +
                "b |b-----b-b-b-----|b-b---b-b-b-----|\n" +
                "\n" +
                "Verse:\n" +
                "\n" +
                "c |----------------|----------------|----------------|----------------|\n" +
                "h |x-x-x-x-x-x-x-x-|x-x-x-x-x-x-x-x-|x-x-x-x-x-x-x-x-|x-x-x-x-x-------|\n" +
                "s |----c-------c---|----c-------c---|----c-------c---|----c-----------|\n" +
                "ft|----------------|----------------|----------------|----------o-o---|\n" +
                "b |b-------b-b-----|b-b-----b-b-----|b-b-----b-b-----|b-b-----b-------|\n" +
                "\n" +
                "  |-------------Repeat x2-----------|\n" +
                "c |x---------------|----------------|----------------|----------------|\n" +
                "h |--x-x-x-x-x-x-x-|x-x-x-x-x-x-x-x-|x-x-x-x-x-x-x-x-|x-x-x-----------|\n" +
                "s |----s-------s---|----s-------s---|----s-------s---|----s-----------|\n" +
                "b |b-------b-b-----|b-b-----b-b-----|b-b-----b-b-----|b-b-------------|\n" +
                "\n" +
                "\n" +
                "c |x---------------|----------------|\n" +
                "h |--x-x-x-x-x-x-x-|----------------|\n" +
                "s |----s-------s---|----------------|\n" +
                "ft|----------------|o-o-o-o-o-o-o-o-|\n" +
                "b |b-b-----b-b-----|b-b-b-b-b-b-b-b-|\n" +
                "\n" +
                "\n" +
                "\n" +
                "Chorus:\n" +
                "\n" +
                "  |-----------Repeat x3-------------|\n" +
                "c |x---------------|----------------|x---------------|----------------|\n" +
                "r |----x---x---x---|x---x---x---x---|----x---x---x---|----------------|\n" +
                "s |----s-------s---|----s-------s---|----s-------s---|----f-------f---|\n" +
                "b |b-------b-b-----|b-b-----b-b-----|b-------b-b-----|b-b-----b-b-----|\n" +
                "\n" +
                "\n" +
                "Interlude:\n" +
                "\n" +
                "  |-----------Repeat x2-------------|\n" +
                "c |x---------------|----------------|\n" +
                "h |--x-x-x-x-x-x-x-|x-x-x-x-x-x-x-x-|\n" +
                "s |----s-------s---|----s-------s---|\n" +
                "b |b-----b-b-b-----|b-b---b-b-b-----|\n" +
                "\n" +
                "\n" +
                "\n" +
                "Verse:\n" +
                "  |-----------Repeat x4-------------|\n" +
                "c |x---------------|----------------|x---------------|----------------|\n" +
                "h |--x-x-x-x-x-x-x-|x-x-x-x-x-x-x-x-|--x-x-x-x-x-x-x-|x-x-x-----------|\n" +
                "s |----s-------s---|----s-------s---|----s-------s---|----s-----------|\n" +
                "b |b-------b-b-----|b-b-----b-b-----|b-b-----b-b-----|b-b-------------|\n" +
                "\n" +
                "\n" +
                "c |x---------------|----------------|\n" +
                "h |--x-x-x-x-x-x-x-|----------------|\n" +
                "s |----s-------s---|----------------|\n" +
                "ft|----------------|o-o-o-o-o-o-o-o-|\n" +
                "b |b-b-----b-b-----|b-b-b-b-b-b-b-b-|\n" +
                "\n" +
                "\n" +
                "\n" +
                "Repeat Chorus\n" +
                "\n" +
                "\n" +
                "Interlude:\n" +
                "\n" +
                "  |-----------Repeat x2-------------|\n" +
                "c |x---------------|----------------|\n" +
                "h |--x-x-x-x-x-x-x-|x-x-x-x-x-x-x-x-|\n" +
                "s |----s-------s---|----s-------s---|\n" +
                "b |b-----b-b-b-----|b-b---b-b-b-----|\n" +
                "\n" +
                "Bridge:\n" +
                "\n" +
                "\t\t           |---Repeat x3----|\n" +
                "c |x---------------|----------------|----------------|\n" +
                "r |----x---x---x---|x---x---x---x---|----------------|\n" +
                "s |----s---s---s---|s---s---s---s---|----------------|\n" +
                "ft|----------------|----------------|o-o-o-o-o-o-o-o-|\n" +
                "b |b-b----b--b----b|--b----b--b----b|b-b-b-b-b-b-b-b-|\n" +
                "\n" +
                "\n" +
                "Solo:\n" +
                "\n" +
                "c |x---------------|----------------|x---------------|----------------|\n" +
                "r |----x---x---x---|x---x---x---x---|----x---x---x---|x---x---x---x---|\n" +
                "s |----s-------s---|----s-------s---|----s-------s---|----s-------s---|\n" +
                "b |b-------b-b-----|b-b-----b-b-----|b-------b-b-----|b-b-----b-b-----|\n" +
                "\n" +
                "c |x---------------|----------------|x---------------|----------------|\n" +
                "r |----x---x---x---|x---x---x---x---|----x---x---x---|----------------|\n" +
                "s |----s-------s---|----s-------s---|----s-------s---|--------ssssssss|\n" +
                "ft|----------------|----------------|----------------|o-o-o-o---------|\n" +
                "b |b-------b-b-----|b-b-----b-b-----|b-------b-b-----|b-b-b-b---------|\n" +
                "\n" +
                "\n" +
                "\n" +
                "Chorus:\n" +
                "\n" +
                "\t\t\t\t   |-----------Repeat x2-------------|\n" +
                "c |x---------------|----------------|x---------------|----------------|\n" +
                "r |----x---x---x---|x---x---x---x---|----x---x---x---|x---x---x---x---|\n" +
                "s |----s-------s---|----s-------s---|----s-------s---|----s-------s---|\n" +
                "b |b-b-----b-b-----|b-b-----b-b-----|b-------b-b-----|b-b-----b-b-----|\n" +
                "\n" +
                "\n" +
                "c |x---------------|----------------|\n" +
                "r |----x---x---x---|----------------|\n" +
                "s |----s-------s---|----f-------f---|\n" +
                "b |b-------b-b-----|b-b-----b-b-----|\n" +
                "\n" +
                "Outro:\n" +
                "(Repeat till end)\n" +
                "\n" +
                "c |x---------------|----------------|\n" +
                "r |--x-x-x-x-x-x-x-|x-x-x-x-x-x-x-x-|\n" +
                "s |----s-------s---|----s-------s---|\n" +
                "b |b-------b-b-----|b-b-----b-b-----|\n" +
                "\n" +
                "End of Tab";

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
//        for (Token token : tokens)
//            System.out.println(token);
    }
}