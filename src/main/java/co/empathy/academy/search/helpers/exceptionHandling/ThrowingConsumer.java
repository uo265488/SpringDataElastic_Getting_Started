package co.empathy.academy.search.helpers.exceptionHandling;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {
    void accept(T t) throws E;
}
