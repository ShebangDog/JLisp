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
    public String toString() {
        return makeString(this, "(");
    }

    private String makeString(T t, String string) {
        if (t == Nil.nil) return string + ")";
        else {
            final var cons = (Cons) t;
            final var carString = cons.car.toString();

            if (cons.car instanceof Atom) return makeString(cons.cdr, string + carString + " . ");
            else return makeString(cons.cdr, string + " ");
        }
    }
}
