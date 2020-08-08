package type;

public class Nil extends List {
    public static Nil nil = new Nil();

    @Override
    public T value() {
        return nil;
    }

    @Override
    public List next() {
        return null;
    }

    @Override
    public Boolean hasNext() {
        return false;
    }

    @Override
    public String toString() {
        return "nil".toUpperCase();
    }
}
