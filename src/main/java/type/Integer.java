package type;

public class Integer extends Number {
    public Integer(int value) {
        super(value);
    }

    @Override
    public String toString() {
        return ((int) this.value) + "";
    }
}
