package org.ametiste.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * <p>
 *     Base abstract class to represent general domin entity.
 * </p>
 *
 * <p>
 *     Each domain entity has they owned identity and they life log history through
 *     {@link DomainEvent}s.
 * </p>
 *
 * @since 0.1.0
 */
public abstract class DomainEntity<T> implements DomainHistoryProducer {

    private final T entityId;

    private ArrayList<DomainEvent> entityEvents = new ArrayList<>();

    protected DomainEntity(T entityId) {
        this.entityId = entityId;
    }

    public T entityId() {
        return entityId;
    }

    /**
     * <p>
     *  Stores produced domain event within entity life log history.
     * </p>
     *
     * @param domainEvent raised event
     */
    protected void raiseEvent(DomainEvent domainEvent) {

        if (domainEvent == null) {
            throw new IllegalDomainEventException("Domain event can't be null.");
        }

        this.entityEvents.add(domainEvent);
    }

    public void produceHistory(Consumer<DomainEvent> eventConsumer) {
        entityEvents.forEach(eventConsumer::accept);
        entityEvents.clear();
    }

}
