package token;

import visitor.TokenVisitor;

import java.math.BigInteger;
import java.util.Optional;

public class NumberToken implements Token {
    private final BigInteger value;
    public NumberToken(String value) {
        this.value = new BigInteger(value);
    }
    public NumberToken(BigInteger value) {
        this.value = value;
    }
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
        return false;
    }
    @Override
    public int getOperationPriority() {
        throw new IllegalStateException("Не операция: " + this);
    }
    @Override
    public Optional<BigInteger> getNumber() {
        return Optional.of(value);
    }
    @Override
    public String toString() {
        return "NUMBER(" + value + ")";
    }

}
