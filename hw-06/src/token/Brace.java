package token;

import visitor.TokenVisitor;

import java.math.BigInteger;
import java.util.Optional;

public enum Brace implements Token {
    OPEN, CLOSE;
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
    @Override
    public boolean isOperation() {
        return false;
    }
    @Override
    public int getOperationPriority() {
        throw new IllegalStateException("Не операция: " + this);
    }
    @Override
    public boolean isOpenBrace() {
        return this == OPEN;
    }
    @Override
    public Optional<BigInteger> getNumber() {
        return Optional.empty();
    }

}
