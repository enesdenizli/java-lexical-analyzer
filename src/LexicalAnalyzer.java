import java.util.Scanner;

public class LexicalAnalyzer {
    private static String input;
    private static int inputLength;
    private static String lexeme = "";
    private static char nextChar;
    private static int charValue;
//    private static int lexLen;
//    private static int token;
    private static int tokenValue;
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
        inputLength = input.length();

        if(input.equals(""))
            System.out.println("Empty input");
        else {
            lexi.getChar();
            do {
              lexi.lex();
            } while(tokenValue != lexi.EOF);
        }
    }

    // lookup - a function to lookup operators and parentheses
    // and return the token
    private void lookup(char ch) {
        switch(ch){
            case '(':
                addChar();
                tokenValue = LEFT_PAREN;
                break;
            case ')':
                addChar();
                tokenValue = RIGHT_PAREN;
                break;
            case ';':
                addChar();
                tokenValue = SEMICOL;
                break;
            case '+':
                addChar();
                tokenValue = ADD_OP;
                break;
            case '-':
                addChar();
                tokenValue = SUB_OP;
                break;
            case '*':
                addChar();
                tokenValue = MULT_OP;
                break;
            case '/':
                addChar();
                tokenValue = DIV_OP;
                break;
            default:
                addChar();
                tokenValue = EOF;
        }
//        return nextToken;
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
        try {
            if(count < inputLength){
                nextChar = input.charAt(count);
                if (Character.isAlphabetic(nextChar))
                    charValue = LETTER;
                else if (Character.isDigit(nextChar))
                    charValue = DIGIT;
                else
                    charValue = UNKNOWN;
            } else
                charValue = EOF;
            count++;
        } catch (IndexOutOfBoundsException exception){
            System.out.println("Error");
            System.exit(0);
        }
    }

    //getNonBlank - a function to call getChar until it
    //returns a non-whitespace character
    private void getNonBlank() {
        while(nextChar == ' ')
            getChar();
    }

    // lex - a simple lexical analyzer for arithmetic
    // expressions.
    private void lex() {
        lexeme = "";
        getNonBlank();

        //Parse identifiers
        switch(charValue){
            case LETTER:
                addChar();
                getChar();
                // adds letter while nextCharValue is letter
                while(charValue == LETTER || charValue == DIGIT){
                    addChar();
                    getChar();
                }
                tokenValue = IDENT;
                break;
            //Parse integer literals
            case DIGIT:
                addChar();
                getChar();
                // adds digit while nextCharValue is digit
                while (charValue == DIGIT){
                    addChar();
                    getChar();
                }
                tokenValue = INT_LIT;
                break;
            // Parentheses and operators
            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;
            // EOF
            case EOF:
                tokenValue = EOF;
                lexeme = "EOF";
        }
        System.out.println("Next token is: " + tokenValue +
                " Next lexeme is " + lexeme);
//        return nextToken;
    }
}
// (sum + 47) / total