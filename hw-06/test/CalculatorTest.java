import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import token.Token;
import tokenizer.ExpressionTokenizer;
import visitor.CalcVisitor;
import visitor.ParserVisitor;
import visitor.PrintVisitor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

public class CalculatorTest {

    @Test
    public void spacesTest() throws IOException {
        baseTest(
                "    \t     2     +    2  *  \t 3      ",
                "NUMBER(2) PLUS NUMBER(2) MULTIPLY NUMBER(3) ",
                "NUMBER(2) NUMBER(2) NUMBER(3) MULTIPLY PLUS ",
                new BigInteger("8")
        );
    }

    @Test
    public void leftAssociationTest() throws IOException {
        baseTest(
                "10 - 1 - 1",
                "NUMBER(10) MINUS NUMBER(1) MINUS NUMBER(1) ",
                "NUMBER(10) NUMBER(1) MINUS NUMBER(1) MINUS ",
                new BigInteger("8")
        );
    }

    @Test
    public void normalTest1() throws IOException {
        baseTest(
                "13 * 8 + (22 - 11) / 11 * 7",
                "NUMBER(13) MULTIPLY NUMBER(8) PLUS OPEN NUMBER(22) MINUS NUMBER(11) CLOSE DIVIDE NUMBER(11) MULTIPLY NUMBER(7) ",
                "NUMBER(13) NUMBER(8) MULTIPLY NUMBER(22) NUMBER(11) MINUS NUMBER(11) DIVIDE NUMBER(7) MULTIPLY PLUS ",
                new BigInteger("111")
        );
    }
    
    @Test
    public void normalTest2() throws IOException {
        baseTest(
                "((22 + 44) / (65 - 76)) * 100",
                "OPEN OPEN NUMBER(22) PLUS NUMBER(44) CLOSE DIVIDE OPEN NUMBER(65) MINUS NUMBER(76) CLOSE CLOSE MULTIPLY NUMBER(100) ",
                "NUMBER(22) NUMBER(44) PLUS NUMBER(65) NUMBER(76) MINUS DIVIDE NUMBER(100) MULTIPLY ",
                new BigInteger("-600")
        );
    }

    @Test
    public void normalTest3() throws IOException {
        baseTest(
                "100 + 10000 * 10000 / 10 * (10000 - 1 + 2) * 1000000",
                "NUMBER(100) PLUS NUMBER(10000) MULTIPLY NUMBER(10000) DIVIDE NUMBER(10) MULTIPLY OPEN NUMBER(10000) MINUS NUMBER(1) PLUS NUMBER(2) CLOSE MULTIPLY NUMBER(1000000) ",
                "NUMBER(100) NUMBER(10000) NUMBER(10000) MULTIPLY NUMBER(10) DIVIDE NUMBER(10000) NUMBER(1) MINUS NUMBER(2) PLUS MULTIPLY NUMBER(1000000) MULTIPLY PLUS ",
                new BigInteger("100010000000000100")
        );
    }

    private static void baseTest(
            String expr,
            String expectedTokens,
            String expectedPolishForm,
            BigInteger expectedResult
    ) throws IOException {

        InputStream bytes = new ByteArrayInputStream(expr.getBytes());
        List<Token> tokens = new ExpressionTokenizer().tokenize(bytes);

        ByteArrayOutputStream inputStream = new ByteArrayOutputStream();
        new PrintVisitor(inputStream).printExpr(tokens);

        String tokensStr = inputStream.toString();
        Assertions.assertEquals(tokensStr, expectedTokens);

        List<Token> reversePolishNotation = new ParserVisitor().parse(tokens);
        inputStream = new ByteArrayOutputStream();
        new PrintVisitor(inputStream).printExpr(reversePolishNotation);

        String parsedStr = inputStream.toString();
        Assertions.assertEquals(parsedStr, expectedPolishForm);

        BigInteger result = new CalcVisitor().calculate(reversePolishNotation);
        Assertions.assertEquals(result, expectedResult);
    }

}
