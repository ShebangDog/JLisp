package analyzer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Lexer {
    private final java.util.List<Character> keywordList = List.of('(', ')', '\'', '\"', '.');

    public static final Lexer lexer = new Lexer();

    private Lexer() {
    }

    public Token tokenize(String line) {
        Token token = new Token();

        tokenize(line, token);

        return token;
    }

    private Token tokenize(String line, Token currentToken) {
        if (line.length() == 0) return currentToken;

        char ch = line.charAt(0);

        if (Character.isSpaceChar(ch)) return tokenize(line.substring(1), currentToken);

        if (Character.isDigit(ch) || ch == '-') {
            Token token = generateNumber(line.substring(1), Character.toString(ch));

            return tokenize(line.substring(token.getValue().length()), currentToken.setNextToken(token));
        }

        if (keywordList.contains(ch)) {
            Token token = new Token(TokenKind.Keyword, Character.toString(ch), null);

            return tokenize(line.substring(1), currentToken.setNextToken(token));
        }

        Token token = generateSymbol(line);
        return tokenize(line.substring(token.getValue().length()), currentToken.setNextToken(token));
    }

    private Token generateNumber(String line, String number) {
        Token terminalToken = new Token(TokenKind.Number, number, null);

        if (0 < number.length() && number.charAt(0) == '0') return terminalToken;
        if (0 < number.length() && number.startsWith("-0")) return terminalToken;

        if (line.length() == 0) return terminalToken;

        char ch = line.charAt(0);
        if (!Character.isDigit(ch)) return terminalToken;
        else return generateNumber(line.substring(1), number + ch);
    }

    private Token generateSymbol(String line) {
        final var stream = Arrays.stream(line.split(""));

        final var untilSpaceString = stream.takeWhile((str) -> {
            char ch = str.charAt(0);
            return !Character.isWhitespace(ch) && !keywordList.contains(ch);
        }).collect(Collectors.joining());

        return new Token(TokenKind.Symbol, untilSpaceString.toUpperCase(), null);
    }
}
