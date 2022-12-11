package visitor;

import token.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class PrintVisitor implements TokenVisitor {
    private final OutputStream os;
    public PrintVisitor(OutputStream os) {
        this.os = os;
    }
    public void printExpr(List<Token> tokens) {
        for (Token token : tokens) {
            token.accept(this);
        }
    }
    @Override
    public void visit(NumberToken token) {
        writeIgnoringIOE(token + " ");
    }
    @Override
    public void visit(Brace token) {
        writeIgnoringIOE(token + " ");
    }
    @Override
    public void visit(Operation token) {
        writeIgnoringIOE(token + " ");
    }

    private void writeIgnoringIOE(String s) {
        try {
            os.write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
