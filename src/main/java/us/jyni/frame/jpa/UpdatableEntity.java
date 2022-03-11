/*
 * 
 */
package us.jyni.frame.jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * @author jynius
 * @Since 2020-10-29
 */
public interface UpdatableEntity<E> {

	public static final Logger LOG = LoggerFactory.getLogger(UpdatableEntity.class);

	/**
	 * @param <E>
	 * @param <F>
	 * @param found
	 * @param form
	 * @return
	 */
	public static <E, F extends UpdatableEntity<E>> List<E> of(List<E> found, F form) {
		return found==null? null: found.stream()
			.peek(e->form.update(e))
			.collect(Collectors.toList());
	}

	/**
	 * @param <E>
	 * @param <F>
	 * @param entity
	 * @param form
	 * @return
	 */
	public static <E, F extends UpdatableEntity<E>> E of(E entity, F form) {
		form.update(entity);
		return entity;
	}
	
	/**
	 * @param entity
	 */
	default void update(E entity) {
		BeanUtils.copyProperties(this, entity);
	}
}
