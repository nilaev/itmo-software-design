package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ParserVisitor implements TokenVisitor {
    private List<Token> out;
    private LinkedList<Token> stack;

    public List<Token> parse(List<Token> tokens) {
        out = new ArrayList<>();
        stack = new LinkedList<>();

        for (Token token : tokens) {
            token.accept(this);
        }

        while (!stack.isEmpty()) {
            Token curToken = stack.removeLast();
            if (!curToken.isOperation()) {
                throw new IllegalStateException("Не совпадет банс количества скобок");
            }
            out.add(curToken);
        }

        return out;
    }

    @Override
    public void visit(NumberToken token) {
        out.add(token);
    }

    @Override
    public void visit(Brace token) {
        switch (token) {
            case OPEN -> stack.addLast(token);
            case CLOSE -> {
                Token curToken;
                while (!stack.isEmpty() && !(curToken = stack.removeLast()).isOpenBrace()) {
                    out.add(curToken);
                    if (stack.isEmpty()) {
                        throw new IllegalStateException("Закрытых скобок больше, чем открытых");
                    }
                }
            }
        }
    }

    @Override
    public void visit(Operation token) {
        while (!stack.isEmpty() && stack.getLast().isOperation()
                && stack.getLast().getOperationPriority() >= token.getOperationPriority()) {
            out.add(stack.removeLast());
        }
        stack.addLast(token);
    }

}
