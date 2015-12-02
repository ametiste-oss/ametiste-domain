package org.ametiste.domain;

/**
 *
 * <p>
 *  This interface defines the general protocol to reflect domain state to a generic representation.
 * <p>
 *
 * <p>
 *  The protocol defines are two participiants: producer of reflection, the domain entity which would provide
 *  the state reflection and the recepient, any object that would create the representation of the state.
 * </p>
 *
 * <p>
 *  The clients of the interface must provide the concrete implementation of the
 *  {@link DomainReflection} interface as the recepient of reflection.
 * </p>
 * @since
 */
public interface DomainStateReflector<T extends DomainReflection> {

    void reflectAs(T domainReflection);

}