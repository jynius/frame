/*
 * 
 */
package us.jyni.frame.jpa;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jynius
 *
 * @param <E> Entity
 */
public interface FormEntity<E> {

	/**
	 * @param <E>
	 * @param <D>
	 * @param views
	 * @return
	 */
	public static <E, D extends FormEntity<E>> List<E> of(List<D> views) {
		return views==null || views.isEmpty()? Collections.emptyList(): views.stream()
				.filter(FormEntity::valid)
				.map(FormEntity::getEntity)
				.collect(Collectors.toList());
	}

	/**
	 * @return
	 */
	default boolean valid() {
		return true;
	}

	/**
	 * @return
	 */
	public E getEntity();
}
