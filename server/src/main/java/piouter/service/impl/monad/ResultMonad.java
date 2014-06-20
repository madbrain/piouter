package piouter.service.impl.monad;

import java.util.function.Function;

public interface ResultMonad<T> {

    <U> ResultMonad<U> combine(Function<T, ResultMonad<U>> func);

    T result();

    static <U> ResultMonad<U> make(U value) {
        if (value == null) {
            return new NullResult<>();
        }
        return new Result<>(value);
    }

}
