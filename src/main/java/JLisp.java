import analyzer.Evaluator;
import analyzer.Lexer;
import analyzer.Parser;
import type.Function;
import type.Symbol;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class JLisp {
    public static void main(String[] args) {
        final var bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Function.registerSystemFunctions();

        System.out.println("Welcome to SDLisp! (2017-4-4)");
        System.out.println("> Copyright (C) GOMI Hiroshi 2017.");
        System.out.println("> Type quit and hit Enter for leaving SDLisp.");

        while (true) {
            try {
                System.out.print("> ");
                final var input = bufferedReader.readLine();
                final var token = Lexer.lexer.tokenize(input);
                final var symbolExpr = Parser.parser.parse(token);

                if (symbolExpr == Symbol.symbolQuit) break;

                final var ret = Evaluator.evaluator.eval(symbolExpr);

                System.out.println(ret);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}