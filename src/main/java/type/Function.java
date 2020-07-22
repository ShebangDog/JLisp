package type;

import analyzer.Evaluator;

public abstract class Function extends Atom {
    private final String name;

    public Function(String name) {
        this.name = name;
    }

    public abstract T functionCall(List arguments) throws Exception;

    public static void registerSystemFunctions() {
        final var list = java.util.List.of(new Car(), new Cdr(), new Add(), new Defun());

        list.forEach(lambda -> {
            final var symbol = Symbol.symbol(lambda.name);
            symbol.function = lambda;
        });
    }

    @Override
    public String toString() {
        return "#<SYSTEM-FUNCTION " + this.getClass().getSimpleName() + ">";
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
            final var car = ((Cons)arguments).car;
            final var args = ((Cons)arguments).cdr;

            Symbol symbol = (Symbol)car;
            symbol.function = new Cons(Symbol.symbol("LAMBDA"), (List) args);

            return symbol;
        }
    }
}
