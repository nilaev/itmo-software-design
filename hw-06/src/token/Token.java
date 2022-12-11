package token;

import visitor.TokenVisitor;

import java.math.BigInteger;
import java.util.Optional;

public interface Token {
    void accept(TokenVisitor visitor);
    boolean isOpenBrace();
    boolean isOperation();
    int getOperationPriority();
    Optional<BigInteger> getNumber();

}
