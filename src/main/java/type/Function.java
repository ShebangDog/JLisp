package type;

import analyzer.Evaluator;

import java.util.Objects;

public abstract class Function extends Atom {
    private final String name;

    public Function(String name) {
        this.name = name;
    }

    public abstract T functionCall(List arguments) throws Exception;

    public static void registerSystemFunctions() {
        final var list = java.util.List.of(
                new Car(), new Cdr(), new Add(),
                new Defun(), new If(), new Equal(),
                new Not(), new ConsFunction());

        list.forEach(lambda -> {
            final var symbol = Symbol.symbol(lambda.name);
            symbol.function = lambda;
        });
    }

    @Override
    public String toString() {
        return "#<SYSTEM-FUNCTION " + this.getClass().getSimpleName() + ">";
    }

    static class ConsFunction extends Function {
        public ConsFunction() {
            super("cons".toUpperCase());
        }

        @Override
        public T functionCall(List arguments) throws Exception {
            final var left = Evaluator.evaluator.eval(arguments.value());
            final var right = Evaluator.evaluator.eval(arguments.next().value());

            return new Cons(left, ((List) right));
        }
    }

    static class Not extends Function {
        public Not() {
            super("not".toUpperCase());
        }

        @Override
        public T functionCall(List arguments) throws Exception {
            final var cons = ((Cons) arguments);
            final var predicate = Evaluator.evaluator.eval(cons.car);

            return Objects.equals(predicate, Symbol.symbolT) ? Nil.nil : Symbol.symbolT;
        }
    }

    static class Car extends Function {
        public Car() {
            super("car".toUpperCase());
        }

        @Override
        public T functionCall(List arguments) throws Exception {
            if (!(arguments instanceof Cons)) throw new Exception("function cons");
            else {
                final var cons = (Cons) arguments;
                final var car = Evaluator.evaluator.eval(cons.car);

                return car == Nil.nil ? Nil.nil : ((Cons) car).car;
            }
        }
    }

    static class Cdr extends Function {
        public Cdr() {
            super("cdr".toUpperCase());
        }

        @Override
        public T functionCall(List arguments) throws Exception {
            if (!(arguments instanceof Cons)) throw new Exception("function cons2");
            else {
                final var cons = (Cons) arguments;
                final var car = Evaluator.evaluator.eval(cons.car);

                return car == Nil.nil ? Nil.nil : ((Cons) car).cdr;
            }
        }
    }

    static class Equal extends Function {
        public Equal() {
            super("equal".toUpperCase());
        }

        @Override
        public T functionCall(List arguments) throws Exception {
            final var cons = ((Cons) arguments);
            final var left = Evaluator.evaluator.eval(cons.car);
            final var right = Evaluator.evaluator.eval(((Cons) cons.cdr).car);

            return Objects.equals(left, right) ? Symbol.symbolT : Nil.nil;
        }
    }

    static class If extends Function {
        public If() {
            super("if".toUpperCase());
        }

        @Override
        public T functionCall(List arguments) throws Exception {
            final var evaluator = Evaluator.evaluator;
            final var cons = ((Cons) arguments);
            final var predicate = cons.car;

            final var forms = ((Cons) cons.cdr);
            final var form1 = forms.car;
            final var form2 = ((Cons) forms.cdr).car;

            return evaluator.eval((evaluator.eval(predicate) == Symbol.symbolT) ? form1 : form2);
        }
    }

    static class Add extends Function {
        public Add() {
            super("+".toUpperCase());
        }

        @Override
        public T functionCall(List arguments) throws Exception {
            if (!(arguments instanceof Cons)) throw new Exception("function cons3");
            else {
                final var cons = (Cons) arguments;
                final var first = (Integer) Evaluator.evaluator.eval(cons.car);
                final var second = (Integer) Evaluator.evaluator.eval(((Cons) cons.cdr).car);

                return first.add(second);
            }
        }
    }

    static class Defun extends Function {
        public Defun() {
            super("defun".toUpperCase());
        }

        @Override
        public T functionCall(List arguments) {
            final var car = ((Cons) arguments).car;
            final var args = ((Cons) arguments).cdr;

            Symbol symbol = (Symbol) car;
            symbol.function = new Cons(Symbol.symbol("LAMBDA"), (List) args);

            return symbol;
        }
    }
}
