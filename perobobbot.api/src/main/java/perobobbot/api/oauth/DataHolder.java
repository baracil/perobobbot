package perobobbot.api.oauth;

import fpc.tools.fp.Nil;
import fpc.tools.fp.Try0;
import fpc.tools.fp.TryConsumer0;
import lombok.NonNull;

public interface DataHolder<T> {

    boolean isEmpty();

    void push(@NonNull T data);

    void pop();


    default <R,E extends Throwable> @NonNull R callWith(@NonNull T data, @NonNull Try0<R,E> action) throws E {
        push(data);
        try {
            return action.apply();
        } finally {
            pop();
        }
    }

    default <E extends Throwable> void acceptWith(@NonNull T data, @NonNull TryConsumer0<E> action) throws E {
        callWith(data,() -> {action.accept();return Nil.NULL;});
    }
}
