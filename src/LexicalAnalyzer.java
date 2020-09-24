import java.sql.SQLOutput;
import java.util.Scanner;

public class LexicalAnalyzer {
    private static String input;
    private static String lexeme = "";
    private static char nextChar;
    private static int charClass;
//    private static int lexLen;
//    private static int token;
    private static int nextToken;
    private static int count;

    // Character classes
    final int EOF = -1;
    final int LETTER = 0;
    final int DIGIT = 1;
    final int UNKNOWN = 99;

    //Token values
    final int INT_LIT = 10;
    final int IDENT = 11;
    final int SUB_OP = 22;
    final int MULT_OP = 23;
    final int DIV_OP = 24;
    final int LEFT_PAREN = 25;
    final int RIGHT_PAREN = 26;
    final int SEMICOL = 27;
    final int ADD_OP = 21;
//    final int ASSIGN_OP = 20;

    public static void main(String[] args) {
        LexicalAnalyzer lexi = new LexicalAnalyzer();
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter equation:");
        input = scan.nextLine();

        if(input.equals(""))
            System.out.println("Empty input");
        else {
            lexi.getChar();
            do {
              lexi.lex();
            } while(nextToken != lexi.EOF);
        }
    }

    // lookup - a function to lookup operators and parentheses
    // and return the token
    private int lookup(char ch) {
        switch(ch){
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;
            case ')':
                addChar();
                nextToken = RIGHT_PAREN;
                break;
            case ';':
                addChar();
                nextToken = SEMICOL;
                break;
            case '+':
                addChar();
                nextToken = ADD_OP;
                break;
            case '-':
                addChar();
                nextToken = SUB_OP;
                break;
            case '*':
                addChar();
                nextToken = MULT_OP;
                break;
            case '/':
                addChar();
                nextToken = DIV_OP;
                break;
            default:
                addChar();
                nextToken = EOF;
        }
        return nextToken;
    }

    // addChar - a function to add nextChar to lexeme
    private void addChar(){
        if(input.length() <= 98){
            lexeme += nextChar;
        }else{
            System.out.println("Error - Lexeme is too long");
        }
    }


    // getChar - a function to get the next character of
    // input and determine its character class
    private void getChar(){
        nextChar = input.charAt(count++);
        if(count != input.length()) {
            if (Character.isAlphabetic(nextChar))
                charClass = LETTER;
            else if (Character.isDigit(nextChar))
                charClass = DIGIT;
            else
                charClass = UNKNOWN;
        } else
            charClass = EOF;
    }

    //getNonBlank - a function to call getChar until it
    //returns a non-whitespace character
    private void getNonBlank() {
        while(nextChar == ' ')
            getChar();
    }

    // lex - a simple lexical analyzer for arithmetic
    // expressions.
    private int lex() {
        lexeme = "";
        getNonBlank();

        //Parse identifiers
        switch(charClass){
            case LETTER:
                addChar();
                getChar();
                while(charClass == LETTER || charClass == DIGIT){
                    addChar();
                    getChar();
                }
                nextToken = IDENT;
                break;
            //Parse integer literals
            case DIGIT:
                addChar();
                getChar();
                while (charClass == DIGIT){
                    addChar();
                    getChar();
                }
                nextToken = INT_LIT;
                break;
            // Parentheses and operators
            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;
            // EOF
            case EOF:
                nextToken = EOF;
                lexeme = "EOF";
        }
        System.out.println("Next token is: " + nextToken +
                " Next lexeme is " + lexeme);
        return nextToken;
    }
}
// (sum + 47) / total