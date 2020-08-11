import analyzer.*;
import type.Function;
import type.Symbol;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

public class JLisp {
    public static void main(String[] args) {
        final var bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Function.registerFunctions();

        System.out.println("Welcome to JLisp(based on SDLisp)! (2020-8-9)");
        System.out.println("> Copyright (C) GOMI Hiroshi 2017.");
        System.out.println("> Type quit and hit Enter for leaving JLisp.");

        StringBuilder input = new StringBuilder();
        while (true) {
            String line;
            try {
                if (input.length() == 0) System.out.print("> ");
                line = bufferedReader.readLine();

                final var token = Lexer.lexer.tokenize(input + line);
                if (!isBalanced(token)) {
                    input.append(line).append(" ");
                    continue;
                }

                final var symbolExpr = Parser.parser.parse(token);

                if (symbolExpr == Symbol.symbolQuit) break;

                final var ret = Evaluator.evaluator.eval(symbolExpr);

                System.out.println(ret);

                input.delete(0, input.length());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isBalanced(Token token, int diff) throws Exception {
        if (diff < 0) throw new Exception();
        if (token == null) return diff == 0;

        final var nextToken = token.getNextToken();

        if (token.getKind() == TokenKind.Keyword) {
            final var value = token.getValue();

            if (Objects.equals(value, "(")) return isBalanced(nextToken, diff + 1);
            if (Objects.equals(value, ")")) return isBalanced(nextToken, diff - 1);
        }

        return isBalanced(nextToken, diff);
    }

    private static boolean isBalanced(Token token) throws Exception {
        return isBalanced(token, 0);
    }
}
