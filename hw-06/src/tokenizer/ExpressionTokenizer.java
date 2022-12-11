package tokenizer;

import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExpressionTokenizer implements Tokenizer {
    private static final int EOF = -1;
    private ExprTokenizerState state;
    private List<Token> tokens;
    private StringBuilder numberBuffer;

    private void nextChar(int c) {
        switch (state) {
            case READ_NUMBER:
                if (isDigit(c)) {
                    numberBuffer.append((char) c);
                    break;
                } else {
                    state = ExprTokenizerState.READ_OTHER;
                    tokens.add(new NumberToken(numberBuffer.toString()));
                    numberBuffer = new StringBuilder();
                }

            case READ_OTHER:
                if (isDigit(c)) {
                    state = ExprTokenizerState.READ_NUMBER;
                    numberBuffer.append((char) c);
                } else if (!Character.isWhitespace(c)) {
                    switch (c) {
                        case '+' -> tokens.add(Operation.PLUS);
                        case '-' -> tokens.add(Operation.MINUS);
                        case '*' -> tokens.add(Operation.MULTIPLY);
                        case '/' -> tokens.add(Operation.DIVIDE);
                        case '(' -> tokens.add(Brace.OPEN);
                        case ')' -> tokens.add(Brace.CLOSE);
                        default -> throw new IllegalArgumentException(String
                                .format("Неизвестный элемент (index = %d): %s", c, (char) c)
                        );
                    }
                }
        }
    }

    private static boolean isDigit(int c) {
        return c >= '0' && c <= '9';
    }

    @Override
    public List<Token> tokenize(InputStream is) throws IOException {
        state = ExprTokenizerState.READ_OTHER;
        tokens = new ArrayList<>();
        numberBuffer = new StringBuilder();

        int c;
        while ((c = is.read()) != EOF) {
            nextChar(c);
        }

        if (numberBuffer.length() != 0) {
            tokens.add(new NumberToken(numberBuffer.toString()));
        }

        return tokens;
    }


    private enum ExprTokenizerState {
        READ_NUMBER, READ_OTHER
    }

}
