package type;

public class Integer extends Number {
    public Integer(int value) {
        super(value);
    }

    public T add(Integer right) {
        return new Integer((int) (this.value + right.value));
    }

    @Override
    public String toString() {
        return ((int) this.value) + "";
    }
}
