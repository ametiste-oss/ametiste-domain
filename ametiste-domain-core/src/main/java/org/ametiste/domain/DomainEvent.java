package org.ametiste.domain;

/**
 *
 * <p>
 *     Base abstract class to represent regular domain event.
 * </p>
 *
 * @param <T> type of the identifier of item which was rise the event
 */
public abstract class DomainEvent<T> {

    private final T sourceEntityId;

    protected DomainEvent(T sourceEntityId) {
        this.sourceEntityId = sourceEntityId;
    }

    public T sourceEntityId() {
        return sourceEntityId;
    }

}
