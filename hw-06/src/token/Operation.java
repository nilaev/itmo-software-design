package token;

import visitor.TokenVisitor;

import java.math.BigInteger;
import java.util.Optional;

public enum Operation implements Token {
    PLUS, MINUS, MULTIPLY, DIVIDE;
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
    @Override
    public boolean isOpenBrace() {
        return false;
    }
    @Override
    public boolean isOperation() {
        return true;
    }
    @Override
    public int getOperationPriority() {
        return switch (this) {
            case PLUS, MINUS -> 1;
            case MULTIPLY, DIVIDE -> 2;
        };
    }

    @Override
    public Optional<BigInteger> getNumber() {
        return Optional.empty();
    }

}
