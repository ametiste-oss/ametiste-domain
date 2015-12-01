package org.ametiste.domain;

/**
 * <p>
 *      Exception that indicates illegal domain event, badly constructed or wrong applied.
 * </p>
 *
 * <p>
 *      Usually will be thrown by {@link DomainEntity} or some kind of domain history log listener.
 *      When event can't have sane interpretation or applied in the current context.
 * </p>
 *
 * @since 0.1.0
 */
public class IllegalDomainEventException extends RuntimeException {

    public IllegalDomainEventException(String message) {
        super(message);
    }

    public IllegalDomainEventException(String message, Throwable cause) {
        super(message, cause);
    }

}