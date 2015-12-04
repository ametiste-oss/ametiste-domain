package org.ametiste.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <p>
 *     Defines domain invariant of aggreagtes.
 * </p>
 *
 * @since 0.2.0
 */
public class AggregateInstant<I, A> {

    private final I lookupId;
    private final Function<I, A> aggregateFinder;
    private final Consumer<A> aggregateFinalizer;
    private final List<Consumer<A>> actions = new ArrayList<>();

    private final List<Runnable> postData = new ArrayList<>();

    @FunctionalInterface
    public interface ThreeConsumer<F, S, T> {
        void apply(F first, S second, T third);
    }

    public static <I, A> AggregateInstant<I, A> create(I lookupId, Function<I, A> aggregateFinder, Consumer<A> aggregateFinalizer) {
        return new AggregateInstant<>(lookupId, aggregateFinder, aggregateFinalizer);
    }

    public AggregateInstant(I lookupId, Function<I, A> aggregateFinder, Consumer<A> aggregateFinalizer) {
        this.lookupId = lookupId;
        this.aggregateFinder = aggregateFinder;
        this.aggregateFinalizer = aggregateFinalizer;
    }

    public AggregateInstant<I,A> lookupId(Consumer<I> lookupIdConsumer) {
        lookupIdConsumer.accept(lookupId);
        return this;
    }

    public AggregateInstant<I,A> action(Consumer<A> aggregateConsumer) {
        actions.add(aggregateConsumer);
        return this;
    }

    public <T> AggregateInstant<I,A> action(BiConsumer<A, T> aggregateConsumer, T argument) {
        actions.add(a -> aggregateConsumer.accept(a, argument));
        return this;
    }

    public <T,C> AggregateInstant<I, A> action(ThreeConsumer<A, T, C> consumer, T t1, C t2) {
        actions.add(a -> consumer.apply(a, t1, t2));
        return this;
    }

    public <T> AccumulationInstant<I, A, T> action(Function<A, T> consumer) {
        final AccumulationInstant<I, A, T> accumulationInstant = new AccumulationInstant<>(consumer, this);
        actions.add(accumulationInstant::accumulateData);
        postData.add(accumulationInstant::riseData);
        return accumulationInstant;
    }

    public void done() {
        final A aggregate = this.aggregateFinder.apply(lookupId);

        actions.forEach(
            c -> c.accept(aggregate)
        );

        this.aggregateFinalizer.accept(aggregate);

        postData.forEach(Runnable::run);
    }

}
