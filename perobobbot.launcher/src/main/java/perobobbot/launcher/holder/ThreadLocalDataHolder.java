package perobobbot.launcher.holder;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perobobbot.api.oauth.DataHolder;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public abstract class ThreadLocalDataHolder<H extends DataHolder<T>, T> implements DataHolder<T> {

    private final ThreadLocal<H> dataHolders;


    protected abstract @NonNull H createInitial();

    @Override
    public boolean isEmpty() {
        final var holder = dataHolders.get();
        return holder == null || holder.isEmpty();
    }

    @Override
    public void push(@NonNull T data) {
        System.out.println(">>> PUSH on Thread        "+Thread.currentThread().getName());
        getOrCreateHolder().push(data);
    }

    @Override
    public void pop() {
        final var holder = dataHolders.get();
        if (holder == null) {
            return;
        }
        holder.pop();
        if (holder.isEmpty()) {
            dataHolders.remove();
        }
    }

    protected @NonNull Optional<H> getHolder() {
        return Optional.ofNullable(dataHolders.get());
    }

    private @NonNull H getOrCreateHolder() {
        final var holder = dataHolders.get();
        if (holder != null) {
            return holder;
        }
        final var newHolder = createInitial();
        dataHolders.set(newHolder);
        return newHolder;
    }
}
