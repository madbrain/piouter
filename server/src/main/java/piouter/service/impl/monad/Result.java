package piouter.service.impl.monad;

import java.util.function.Function;

public class Result<T> implements ResultMonad<T> {

    private final T value;

    public Result(T value) {
        this.value = value;
    }

    @Override
    public <U> ResultMonad<U> combine(Function<T, ResultMonad<U>> func) {
        return func.apply(value);
    }

    @Override
    public T result() {
        return value;
    }
}
