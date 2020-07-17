public class Cons extends List {
    public T car;
    public T cdr;

    public Cons() { this(Nil.nil, Nil.nil); }
    public Cons(T car, T cdr) {
        this.car = car;
        this.cdr = cdr;
    }
}
