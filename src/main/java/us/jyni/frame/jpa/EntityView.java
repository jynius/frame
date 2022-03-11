/*
 * 
 */
package us.jyni.frame.jpa;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * @author jynius
 *
 * @param <E>
 */
public interface EntityView<E> {

	public static final Logger LOG = LoggerFactory.getLogger(EntityView.class);

	/**
	 * @param <E>
	 * @param <D>
	 * @return
	 */
	public static <E, D extends EntityView<E>> D nullClass() {
		return null;
	}
	
	/**
	 * @param <E>
	 * @param <T>
	 * @param page
	 * @param function
	 * @return
	 */
	public static <E, T> Page<T> of(Page<E> page, Function<E, T> function) {
		return new PageImpl<T>(of(page.getContent(), function), page.getPageable(), page.getTotalElements());
	}

	/**
	 * @param <E>
	 * @param <D>
	 * @param page
	 * @param clazz
	 * @return
	 * 
	 * @see {@link PageImpl#PageImpl(List, Pageable, long)}
	 * @see {@link Page}
	 */
	public static <E, D extends EntityView<E>> Page<D> of(Page<E> page, Class<D> clazz) {
		return clazz==null? Page.empty(): new PageImpl<D>(of(page.getContent(), clazz), page.getPageable(), page.getTotalElements());
	}
	
	/**
	 * @param <E>
	 * @param <T>
	 * @param entities
	 * @param function
	 * @return
	 */
	public static <E, T> List<T> of(List<E> entities, Function<E, T> function) {
		return entities==null || entities.isEmpty()? Collections.emptyList(): entities.stream()
				.map(function)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
	
	/**
	 * @param <E>
	 * @param <D>
	 * @param entities
	 * @param clazz
	 * @return
	 */
	public static <E, D extends EntityView<E>> List<D> of(List<E> entities, Class<D> clazz) {
		return clazz==null || entities==null || entities.isEmpty()? Collections.emptyList(): entities.stream()
				.map(e->of(e, clazz))
				.collect(Collectors.toList());
	}

	/**
	 * @param <E>
	 * @param <T>
	 * @param optional
	 * @param function
	 * @return
	 */
	public static <E, T> Optional<T> of(Optional<E> optional, Function<E, T> function) {
		return !optional.isPresent()? Optional.empty(): Optional.ofNullable(function.apply(optional.get()));
	}
	
	/**
	 * @param <E>
	 * @param <D>
	 * @param optional
	 * @param clazz
	 * @return
	 */
	public static <E, D extends EntityView<E>> Optional<D> of(Optional<E> optional, Class<D> clazz) {
		return clazz==null || !optional.isPresent()? Optional.empty(): Optional.of(of(optional.get(), clazz));
	}
	
	/**
	 * @param <E>
	 * @param <D>
	 * @param entity must not be null
	 * @param clazz
	 * @return must not be null
	 */
	public static <E, D extends EntityView<E>> D of(E entity, Class<D> clazz) {
		
		try {
			D data = clazz.getDeclaredConstructor().newInstance();
			data.setEntity(entity);
			return data;
		}
		catch (InstantiationException | IllegalArgumentException | NoSuchMethodException e) {
			throw new ModelCreationException();
		}
		catch (InvocationTargetException | IllegalAccessException | SecurityException e) {
			throw new ModelCreationException();
		}
	}
	
	/**
	 * @param entity
	 * 
	 * @see {@link BeanUtils#copyProperties(Object, Object)}
	 */
	default void setEntity(E entity) {
		BeanUtils.copyProperties(entity, this);
	}
}
