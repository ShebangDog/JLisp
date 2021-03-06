package analyzer;

import type.Float;
import type.Integer;
import type.Number;
import type.*;

import java.util.Objects;

public class Parser {

    public static final Parser parser = new Parser();

    private Parser() {
    }

    private Token token;

    public T parse(Token token) throws Exception {
        this.token = token;
        return parse();
    }

    public T parse() throws Exception {
        if (consume("(")) return list();
        if (consume("'")) return quote();
        if (token.getKind() == TokenKind.Number) return singedNumber();
        if (token.getKind() == TokenKind.Symbol) return symbol();

        throw new Exception("parser");
    }

    private T list() throws Exception {
        if (token == null) throw new InCompleteFormException();
        if (consume(")")) return Nil.nil;

        final var topCons = new Cons();
        var cons = topCons;

        while (true) {
            cons.car = parse();

            if (consume(")")) return topCons;
            if (consume(".")) {
                cons.cdr = parse();
                return topCons;
            }

            cons.cdr = new Cons();
            cons = (Cons) cons.cdr;
        }
    }

    private T quote() throws Exception {
        final var top = new Cons();
        var cons = top;

        cons.car = Symbol.symbol("'");
        cons.cdr = new Cons();

        cons = (Cons) cons.cdr;
        cons.car = parse();

        return top;
    }

    private T singedNumber() {
        final var tokenValue = this.token.getValue();
        Number result;

        try {
            final var number = java.lang.Integer.parseInt(tokenValue);
            result = new Integer(number);
        } catch (NumberFormatException e) {
            final var number = java.lang.Float.parseFloat(tokenValue);
            result = new Float(number);
        }

        this.token = this.token.getNextToken();

        return result;
    }

    private T symbol() {
        final var symbolName = this.token.getValue();
        final var result = Objects.equals(symbolName, "NIL") ? Nil.nil : Symbol.symbol(symbolName);

        this.token = this.token.getNextToken();

        return result;
    }

    private boolean consume(String sign) throws InCompleteFormException{
        if (this.token.getValue() == null) throw new InCompleteFormException();

        if (!Objects.equals(sign, this.token.getValue())) return false;

        this.token = this.token.getNextToken();
        return true;
    }

}
