package type;

public abstract class Number implements Atom {
    public final double value;

    protected Number(double value) {
        this.value = value;
    }

    public boolean equalValue(Number that) {
        return that.value == this.value;
    }
}
