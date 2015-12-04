package org.ametiste.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @since
 */
public class AccumulationInstant<I, A, T> {

    private final List<Consumer<T>> consumers = new ArrayList<>();
    private final Function<A, T> consumer;
    private T data;
    private final AggregateInstant<I,A> aggregateInstant;

    public AccumulationInstant(Function<A, T> consumer, AggregateInstant<I,A> aggregateInstant) {
        this.consumer = consumer;
        this.aggregateInstant = aggregateInstant;
    }

    public AccumulationInstant<I, A, T> consume(Consumer<T> valueConsumer) {
        consumers.add(valueConsumer);
        return this;
    }

    public void done() {
        aggregateInstant.done();
    }

    void accumulateData(A aggregate) {
        this.data = consumer.apply(aggregate);
    }

    void riseData() {
        consumers.forEach(c -> c.accept(this.data));
    }

    public AggregateInstant<I,A> action(Consumer<A> aggregateConsumer) {;
        return aggregateInstant.action(aggregateConsumer);
    }

    public <T> AggregateInstant<I,A> action(BiConsumer<A, T> aggregateConsumer, T argument) {
        return aggregateInstant.action(aggregateConsumer, argument);
    }

    public <T,C> AggregateInstant<I, A> action(AggregateInstant.ThreeConsumer<A, T, C> consumer, T t1, C t2) {
        return aggregateInstant.action(consumer, t1, t2);
    }

}
