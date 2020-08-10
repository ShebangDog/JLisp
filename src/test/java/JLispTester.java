import analyzer.Evaluator;
import analyzer.Lexer;
import analyzer.Parser;
import type.Function;

import java.util.List;
import java.util.Objects;

public class JLispTester {
    public static void main(String[] args) {
        Function.registerFunctions();

        System.out.println("Welcome to JLisp(based on SDLisp)! (2020-8-9)");
        System.out.println("> Copyright (C) GOMI Hiroshi 2017.");
        System.out.println("> Type quit and hit Enter for leaving JLisp.");

        final var list = List.of(
                Pair.of("(defun foo (x y) (bar x))", "foo"),
                Pair.of("(defun bar (x) (baz))", "bar"),
                Pair.of("(defun baz () y)", "baz"),
                Pair.of("(foo 1 2)", "2"),

                Pair.of("(cons 1 nil)", "(1)"),

                Pair.of("(+ 1 1.0)", "2.0"),
                Pair.of("(- 1 1.0)", "0.0"),
                Pair.of("(* 1 1.0)", "1.0"),
                Pair.of("(/ 1 1.0)", "1.0"),

                Pair.of("(equal 'a 'a)", "t"),
                Pair.of("(equal 1 1)", "t"),
                Pair.of("(equal (cons 1 nil) (cons 1 nil))", "t"),
                Pair.of("(equal (cons 1 nil) '(1))", "t"),

                Pair.of("(= 1 1)", "t"),
                Pair.of("(= (+ 1 1) 2)", "t"),
                Pair.of("(= 'a 'a)", "nil"),

                Pair.of("'()", "nil"),
                Pair.of("'(1 . nil)", "(1)"),

                Pair.of("0.0", "0.0"),
                Pair.of("'(0.0)", "(0.0)"),

                Pair.of("nil", "nil")
        );

        list.forEach(pair -> {
            final var input = pair.first.toUpperCase();
            final var expectedValue = pair.second.toUpperCase();

            try {
                final var token = Lexer.lexer.tokenize(input);
                final var symbolExpr = Parser.parser.parse(token);
                final var eval = Evaluator.evaluator.eval(symbolExpr);

                final var equals = Objects.equals(eval.toString(), expectedValue);
                assert equals : input + " : expected " + expectedValue + " but got " + eval;

                System.out.println("success" + " : " + eval)    ;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
