package machine;

import type.T;

import java.util.Vector;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class StackMachine {
    public static StackMachine stackMachine = new StackMachine();

    private final Vector<T> stack;

    private StackMachine() {
        this.stack = new Vector<>();
    }

    public T push(T item) {
        this.stack.addElement(item);

        return item;
    }

    public T pop() {
        int len = this.stack.size();
        final var item = this.stack.elementAt(len - 1);

        this.stack.removeElementAt(len - 1);

        return item;
    }

    private T popUntil(Predicate<T> predicate, T result) {
        if (predicate.test(result)) return result;

        return popUntil(predicate, pop());
    }

    public T popUntil(Predicate<T> predicate) {
        return popUntil(predicate, pop());
    }

    private T popUntil(BiPredicate<java.lang.Integer, T> biPredicate, T result) {
        if (biPredicate.test(this.size(), result)) return result;

        return popUntil(biPredicate, pop());
    }

    public T popUntil(BiPredicate<java.lang.Integer, T> biPredicate) {
        return popUntil(biPredicate, pop());
    }

    public T set(T item, int index) {
        return this.stack.set(index, item);
    }

    public T elementAt(int index) {
        return this.stack.elementAt(index);
    }

    public int size() {
        return this.stack.size();
    }
}
