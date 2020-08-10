package type;

public abstract class Number implements Atom {
    public final double value;

    protected Number(double value) {
        this.value = value;
    }

    public boolean equalValue(Number that) {
        return that.value == this.value;
    }

    public T plus(Number right) {
        final var left = this;

        final var result = left.value + right.value;
        if (left instanceof Integer && right instanceof Integer) return new Integer(((int) result));

        return new Float(result);
    }

    public T minus(Number right) {
        final var left = this;

        final var result = left.value - right.value;
        if (left instanceof Integer && right instanceof Integer) return new Integer(((int) result));

        return new Float(result);
    }

    public T multiple(Number right) {
        final var left = this;

        final var result = left.value * right.value;
        if (left instanceof Integer && right instanceof Integer) return new Integer(((int) result));

        return new Float(result);
    }

    public T divide(Number right) throws ArithmeticException {
        final var left = this;

        if (right.value == 0) throw new ArithmeticException("divide by zero");

        final var result = left.value / right.value;
        if (left instanceof Integer && right instanceof Integer) return new Integer(((int) result));

        return new Float(result);
    }

}
