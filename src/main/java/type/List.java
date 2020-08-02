package type;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class List extends T {
    public abstract T value();
    public abstract List next();

    public abstract Boolean hasNext();

    public void forEach(Consumer<T> consumer) {
        if (!hasNext()) return;

        consumer.accept(value());
        next().forEach(consumer);
    }

    public void forEachWithIndex(int index, int offset, BiConsumer<java.lang.Integer, T> biConsumer) {
        if (!hasNext()) return;

        biConsumer.accept(index + offset, value());
        next().forEachWithIndex(index + 1, offset, biConsumer);
    }

    public void forEachWithIndex(int index, BiConsumer<java.lang.Integer, T> biConsumer) {
        this.forEachWithIndex(index, 0, biConsumer);
    }

}
