package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CalcVisitor implements TokenVisitor {
    private LinkedList<Token> stack;

    public BigInteger calculate(List<Token> expr) {
        stack = new LinkedList<>();

        for (Token token : expr) {
            token.accept(this);
        }

        if (stack.size() != 1) {
            throw new IllegalStateException("Размер выражения слишком большой");
        }
        return popNumberFromStack();
    }

    @Override
    public void visit(NumberToken token) {
        stack.addLast(token);
    }

    @Override
    public void visit(Brace token) {
        throw new IllegalStateException("В ОПЗ не должно быть скобок");
    }

    @Override
    public void visit(Operation token) {
        BigInteger arg1 = popNumberFromStack();
        BigInteger arg2 = popNumberFromStack();
        switch (token) {
            case PLUS -> stack.addLast(new NumberToken(arg2.add(arg1)));
            case MINUS -> stack.addLast(new NumberToken(arg2.subtract(arg1)));
            case MULTIPLY -> stack.addLast(new NumberToken(arg2.multiply(arg1)));
            case DIVIDE -> stack.addLast(new NumberToken(arg2.divide(arg1)));
        }
    }

    private BigInteger popNumberFromStack() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Ожидается число");
        }
        Optional<BigInteger> num = stack.removeLast().getNumber();
        if (num.isEmpty()) {
            throw new IllegalStateException("Не число: " + this);
        }
        return num.get();
    }

}
