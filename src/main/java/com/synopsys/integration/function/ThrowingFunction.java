package com.synopsys.integration.function;

public interface ThrowingFunction<T, R, E extends Throwable> {
    /**
     * Applies this function, which may throw an exception, to the given argument.
     * @param t the function argument
     * @return the function result
     */
    R apply(T t) throws E;

}
