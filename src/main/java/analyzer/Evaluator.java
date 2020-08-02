package analyzer;

import machine.StackMachine;
import type.*;

public class Evaluator {

    private Evaluator() {
    }

    public static Evaluator evaluator = new Evaluator();

    private final StackMachine stackMachine = StackMachine.stackMachine;

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
        final var previousIndex = this.stackMachine.size();

        arguments.forEach(argument -> {
            try {
                final T hold = eval(argument);
                this.stackMachine.push(hold);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        parameter.forEachWithIndex(previousIndex, (index, param) -> {
            final var symbol = (Symbol) param;

            final var hold = symbol.value;

            symbol.value = this.stackMachine.elementAt(index);
            this.stackMachine.set(hold, index);
        });

        T ret = evalBody(body);
        parameter.forEachWithIndex(previousIndex, (index, param) -> {
            final var symbol = ((Symbol) param);
            symbol.value = this.stackMachine.elementAt(index);
        });

        this.stackMachine.popUntil((size, t) -> size == previousIndex);

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
