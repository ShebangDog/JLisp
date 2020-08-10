package type;

public class Float extends Number {
    public Float(double value) {
        super(value);
    }

    @Override
    public String toString() {
        return this.value + "";
    }
}
