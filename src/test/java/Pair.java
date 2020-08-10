public class Pair<F, S> {
    public final F first;
    public final S second;

    public static <F, S> Pair<F, S> of(F f, S s) {
        return new Pair<>(f, s);
    }

    private Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}
