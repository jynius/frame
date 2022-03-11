/*
 * 
 */
package us.jyni.frame.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author jynius
 *
 * @param <E> Entity
 * @param <I> Identity
 */
public interface GenericRepository<E, I> extends JpaSpecificationExecutor<E>, JpaRepository<E, I> {

}