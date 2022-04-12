package jakwagne.missingpermissions;

import java.util.function.Consumer;

public interface PublishingConsumer<T, E> extends Consumer<T> {
    <K extends Consumer<? super E>> void addListener(K consumer);
}
