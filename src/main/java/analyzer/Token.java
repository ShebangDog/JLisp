package analyzer;

public class Token {
    private final TokenKind kind;
    private final String value;
    private Token nextToken;

    public Token() {
        kind = TokenKind.None;
        value = null;
        nextToken = null;
    }

    public Token(TokenKind kind, String value, Token nextToken) {
        this.kind = kind;
        this.value = value;
        this.nextToken = nextToken;
    }

    public Token setNextToken(Token nextToken) {
        this.nextToken = nextToken;

        return nextToken;
    }

    public String getValue() {
        return value;
    }

    public Token getNextToken() {
        return nextToken;
    }

    public TokenKind getKind() {
        return kind;
    }
}

