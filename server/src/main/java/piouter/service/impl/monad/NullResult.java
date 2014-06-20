package piouter.service.impl.monad;

import java.util.function.Function;

public class NullResult<T> implements ResultMonad<T> {
    @Override
    public <U> ResultMonad<U> combine(Function<T, ResultMonad<U>> func) {
        return new NullResult<U>();
    }

    @Override
    public T result() {
        return null;
    }
}
