package type;

public class Cons extends List {
    public T car;
    public T cdr;

    public Cons() {
        this(Nil.nil, Nil.nil);
    }

    public Cons(T car, List cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    @Override
    public T value() {
        return this.car;
    }

    @Override
    public List next() {
        return (List) this.cdr;
    }

    @Override
    public Boolean hasNext() {
        return next() != null;
    }

    @Override
    public String toString() {
        return makeString(this, "(");
    }

    private String makeString(T t, String string) {
        if (t == Nil.nil) return string + ")";
        else {
            final var cons = (Cons) t;
            final var carString = cons.car.toString();

            final var postFix = (cons.cdr != Nil.nil ? " " : "");
            return makeString(cons.cdr, string + carString + postFix);
        }
    }
}
