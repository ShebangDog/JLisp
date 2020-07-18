package type;

public class Nil extends List {
    public static Nil nil = new Nil();

    @Override
    public String toString() {
        return "type.Nil";
    }
}
