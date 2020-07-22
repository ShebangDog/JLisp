package analyzer;

import type.*;

public class Evaluator {
    private static final int maxStackSize = 65536;
    private static T[] stack = new T[maxStackSize];
    private static int stackP = 0;

    private Evaluator() {
    }

    public static Evaluator evaluator = new Evaluator();

    public T eval(T symbolExpression) throws Exception {
        if (symbolExpression instanceof Symbol) {
            if (((Symbol) symbolExpression).value == null) throw new Exception("eval symbol");
            return ((Symbol) symbolExpression).value;
        }

        if (symbolExpression instanceof Nil) return symbolExpression;
        if (symbolExpression instanceof Atom) return symbolExpression;

        if (symbolExpression instanceof List) {
            final var car = ((Cons) symbolExpression).car;
            final var arguments = ((Cons) symbolExpression).cdr;

            if (car instanceof Symbol) {
                final var symbol = ((Symbol) car);
                final var function = symbol.function;

                if (function == null) {
                    System.out.println(symbol.name);
                    throw new Exception("eval function");
                }

                if (function instanceof Function) {
                    final var argumentList = ((Cons) symbolExpression).cdr;

                    return ((Function) function).functionCall((List) argumentList);
                }

                if (function instanceof Cons) {
                    final var cons = ((Cons) function);

                    final var lambda = ((Cons) cons.cdr);

                    final var parameter = lambda.car;
                    final var body = ((Cons) lambda.cdr);

                    if (parameter == Nil.nil) return evalBody(body);

                    return bindEvalBody((Cons) parameter, body, (Cons) arguments);
                }

                throw new Exception("eval unknown 2");
            }
        }

        throw new Exception("eval unknown 1");
    }

    private T bindEvalBody(Cons parameter, Cons body, Cons arguments) throws Exception {
        int OldStackP = stackP;
        while (true) {
            T ret = eval(arguments.car);
            stack[stackP++] = ret;
            if (arguments.cdr == Nil.nil) break;
            arguments = (Cons) arguments.cdr;
        }

        Cons argList = parameter;
        int sp = OldStackP;
        while (true) {
            Symbol sym = (Symbol) argList.car;
            T swap = sym.value;
            sym.value = stack[sp];
            stack[sp++] = swap;
            if (argList.cdr == Nil.nil) break;
            argList = (Cons) argList.cdr;
        }

        T ret = evalBody(body);

        argList = parameter;
        stackP = OldStackP;
        while (true) {
            Symbol sym = (Symbol) argList.car;
            sym.value = stack[OldStackP++];
            if (argList.cdr == Nil.nil) break;
            argList = (Cons) argList.cdr;
        }

        return ret;
    }

    private T evalBody(Cons body, T result) throws Exception {
        if (body.cdr == Nil.nil) return result;
        return evalBody((Cons) body.cdr, eval(body.car));
    }

    private T evalBody(Cons body) throws Exception {
        return evalBody(body, eval(body.car));
    }

}
