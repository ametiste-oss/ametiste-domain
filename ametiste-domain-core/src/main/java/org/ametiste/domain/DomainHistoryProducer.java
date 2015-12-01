package org.ametiste.domain;

import java.util.function.Consumer;

/**
 * <p>
 *     Object which implemnts this interface, usually {@link DomainEntity}, may somehow affect the history
 *     of the domain.
 * </p>
 *
 * <p>
 *     This interface defines operations to fetch history log of this effects.
 * </p>
 *
 * <p>
 *     Consumed events may be used by object's clients to construct their own representation or
 *     interpretation of events within the domain.
 * </p>
 *
 * @since 0.1.0
 */
public interface DomainHistoryProducer {

    void produceHistory(Consumer<DomainEvent> eventConsumer);

}
