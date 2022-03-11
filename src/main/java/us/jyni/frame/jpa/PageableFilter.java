/*
 * 
 */
package us.jyni.frame.jpa;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author jynius
 * @Since 2020-10-29
 *
 * @param <E>
 */
public interface PageableFilter<E> extends Filter<E>, Pageable {

	/**
	 * <p>The default method {@link #getSort()} inherited from {@link Filter<E>#getSort()}
	 *  conflicts with another method inherited from {@link Pageable#getSort()}</p>
	 */
	@Override
	default Sort getSort() {
		return Filter.super.getSort();
	}

	/**
	 * @return
	 */
	default Pageable getPageable() {
		return Pageable.unpaged();
	}
}
