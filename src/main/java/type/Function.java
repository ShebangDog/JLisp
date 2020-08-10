package type;

import analyzer.Evaluator;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public enum Function implements FunctionalInterface {
    Quote("'") {
        @Override
        public T functionCall(List arguments) {
            return ((Cons) arguments).car;
        }
    },

    Cons("cons") {
        @Override
        public T functionCall(List arguments) throws Exception {
            final var left = Evaluator.evaluator.eval(arguments.value());
            final var right = Evaluator.evaluator.eval(arguments.next().value());

            return new Cons(left, ((List) right));
        }
    },
    Car("car") {
        @Override
        public T functionCall(List arguments) throws Exception {
            if (!(arguments instanceof Cons)) throw new Exception("function cons");
            else {
                final var cons = (Cons) arguments;
                final var car = Evaluator.evaluator.eval(cons.car);

                return car == Nil.nil ? Nil.nil : ((Cons) car).car;
            }
        }
    },
    Cdr("cdr") {
        @Override
        public T functionCall(List arguments) throws Exception {
            if (!(arguments instanceof Cons)) throw new Exception("function cons2");
            else {
                final var cons = (Cons) arguments;
                final var car = Evaluator.evaluator.eval(cons.car);

                return car == Nil.nil ? Nil.nil : ((Cons) car).cdr;
            }
        }
    },

    Not("not") {
        @Override
        public T functionCall(List arguments) throws Exception {
            final var cons = ((Cons) arguments);
            final var predicate = Evaluator.evaluator.eval(cons.car);

            return Objects.equals(predicate, Symbol.symbolT) ? Nil.nil : Symbol.symbolT;
        }
    },
    Equal("equal") {
        @Override
        public T functionCall(List arguments) throws Exception {
            final var cons = ((Cons) arguments);
            final var left = Evaluator.evaluator.eval(cons.car);
            final var right = Evaluator.evaluator.eval(((Cons) cons.cdr).car);

            return Objects.equals(left.toString(), right.toString()) ? Symbol.symbolT : Nil.nil;
        }
    },
    ValueEqual("=") {
        @Override
        public T functionCall(List arguments) throws Exception {
            final var cons = ((Cons) arguments);
            final var left = Evaluator.evaluator.eval(cons.car);
            final var right = Evaluator.evaluator.eval(((Cons) cons.cdr).car);

            final var isNotNumbers = !java.util.List.of(left, right).stream().allMatch(elem -> elem instanceof Number);

            if (isNotNumbers) return Nil.nil;

            return ((Number) left).equalValue(((Number) right)) ? Symbol.symbolT : Nil.nil;
        }
    },

    If("if") {
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
    },

    Plus("+") {
        @Override
        public T functionCall(List arguments) throws Exception {
            if (!(arguments instanceof Cons)) throw new Exception("function cons3");
            else {
                final var cons = (Cons) arguments;
                final var first = (Number) Evaluator.evaluator.eval(cons.car);
                final var second = (Number) Evaluator.evaluator.eval(((Cons) cons.cdr).car);

                return first.plus(second);
            }
        }
    },
    Minus("-") {
        @Override
        public T functionCall(List arguments) throws Exception {
            if (!(arguments instanceof Cons)) throw new Exception("function cons4");
            else {
                final var cons = (Cons) arguments;
                final var first = (Number) Evaluator.evaluator.eval(cons.car);
                final var second = (Number) Evaluator.evaluator.eval(((Cons) cons.cdr).car);

                return first.minus(second);
            }
        }
    },
    Multiple("*") {
        @Override
        public T functionCall(List arguments) throws Exception {
            if (!(arguments instanceof Cons)) throw new Exception("function cons3");
            else {
                final var cons = (Cons) arguments;
                final var first = (Number) Evaluator.evaluator.eval(cons.car);
                final var second = (Number) Evaluator.evaluator.eval(((Cons) cons.cdr).car);

                return first.multiple(second);
            }
        }
    },
    Divide("/") {
        @Override
        public T functionCall(List arguments) throws Exception {
            if (!(arguments instanceof Cons)) throw new Exception("function cons3");
            else {
                final var cons = (Cons) arguments;
                final var first = (Number) Evaluator.evaluator.eval(cons.car);
                final var second = (Number) Evaluator.evaluator.eval(((Cons) cons.cdr).car);

                return first.divide(second);
            }
        }
    },

    Defun("defun") {
        @Override
        public T functionCall(List arguments) {
            final var car = ((Cons) arguments).car;
            final var args = ((Cons) arguments).cdr;

            Symbol symbol = (Symbol) car;
            symbol.function = new Cons(Symbol.symbol("LAMBDA"), (List) args);

            return symbol;
        }
    };

    Function(String name) {
        this.name = name.toUpperCase();
    }

    public final String name;

    @Override
    public String toString() {
        return "#<SYSTEM-FUNCTION " + this.getClass().getSimpleName() + ">";
    }

    public static Set<Function> functionSet = Arrays.stream(values()).collect(Collectors.toSet());

    public static void registerFunctions() {
        functionSet.forEach(function -> {
            final var symbol = Symbol.symbol(function.name);

            symbol.function = function;
        });
    }

}
