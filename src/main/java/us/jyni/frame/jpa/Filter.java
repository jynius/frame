/*
 * 
 */
package us.jyni.frame.jpa;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author jynius
 * @Since 2020-10-29
 *
 * @param <E> Entity
 */
public interface Filter<E> {

	/**
	 * @param <E>
	 * @param map
	 * @return
	 */
	public static <E> Specification<E> createSpecification(Map<String, Object> map) {
		return (r, q, b) -> {
			List<Predicate> p = map.entrySet().stream()
				.map(e->b.equal(r.get(e.getKey()), e.getValue()))
				.collect(Collectors.toList());
			return b.and(p.toArray(new Predicate[p.size()]));
		};
	}
	
	/**
	 * @return
	 */
	default Sort getSort() {
		return Sort.unsorted();
	}
	
	/**
	 * @return
	 */
	public Specification<E> getSpecification();
}
