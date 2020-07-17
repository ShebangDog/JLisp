public class Integer extends Number {
    public final int value;

    public Integer(int value) {
        this.value = value;
    }

    public T add(Integer right) {
        return new Integer(this.value + right.value);
    }

    @Override
    public String toString() {
        return "" + this.value;
    }
}
