/*
 * 
 */
package us.jyni.frame.jpa;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

/**
 * @author jynius
 * @Since 2020-10-29
 *
 * @param <E> Entity
 * @param <I> Identity
 * @param <R> Repository
 */
public interface GenericService<E, I, R extends GenericRepository<E, I>> {

	/**
	 * <h3>{@link GenericRepository}를 상속한 Repository Interface</h3>
	 * <p></p>
	 * 
	 * @return
	 */
	public R getRepository();
	
	/**
	 * <p>Returns the number of instances that the given {@link Specification} will return.</p>
	 * 
	 * @param filter spec the Specification to count instances for. Can be null.
	 * @return the number of instances.
	 * 
	 * @see {@link JpaSpecificationExecutor#count(Specification)}
	 */
	default long count(@Nullable Filter<E> filter) {
		return getRepository().count(filter==null? null: filter.getSpecification());
	}

	/**
	 * <p>Returns a {@link Page} of entities matching the given {@link Specification}.</p>
	 * 
	 * @param <T>
	 * @param filter
	 * @param function
	 * @return never null.
	 * 
	 * @see {@link JpaSpecificationExecutor#findAll(Specification, Pageable)}
	 * @see {@link Pageable}
	 */
	default <T> Page<T> findPage(@Nullable PageableFilter<E> filter, Function<E, T> function) {
		Page<E> page = getRepository().findAll(filter==null? null: filter.getSpecification(), filter==null? Pageable.unpaged(): filter.getPageable());
		return EntityView.of(page, function);
	}

	/**
	 * <p>Returns a {@link Page} of entities matching the given {@link Specification}.</p>
	 * 
	 * @param <D>
	 * @param filter
	 * @param clazz
	 * @return never null.
	 * 
	 * @see {@link JpaSpecificationExecutor#findAll(Specification, Pageable)}
	 * @see {@link Pageable}
	 */
	default <D extends EntityView<E>> Page<D> findPage(@Nullable PageableFilter<E> filter, Class<D> clazz) {
		Page<E> page = getRepository().findAll(filter==null? null: filter.getSpecification(), filter==null? Pageable.unpaged(): filter.getPageable());
		return EntityView.of(page, clazz);
	}

	/**
	 * <p>Returns all entities matching the given {@link Specification} and {@link Sort}.</p>
	 * 
	 * @param <T>
	 * @param filter
	 * @param function
	 * @return never null.
	 * 
	 * @see {@link JpaSpecificationExecutor#findAll(Specification, Sort)}
	 */
	default <T> List<T> findAll(@Nullable Filter<E> filter, Function<E, T> function) {
		List<E> list = getRepository().findAll(filter==null? null: filter.getSpecification(), filter==null? Sort.unsorted(): filter.getSort());
		return EntityView.of(list, function);
	}

	/**
	 * <p>Returns all entities matching the given {@link Specification} and {@link Sort}.</p>
	 * 
	 * @param <D>
	 * @param filter
	 * @param clazz
	 * @return never null.
	 * 
	 * @see {@link JpaSpecificationExecutor#findAll(Specification, Sort)}
	 */
	default <D extends EntityView<E>> List<D> findAll(@Nullable Filter<E> filter, Class<D> clazz) {
		List<E> list = getRepository().findAll(filter==null? null: filter.getSpecification(), filter==null? Sort.unsorted(): filter.getSort());
		return EntityView.of(list, clazz);
	}

	/**
	 * <p>Returns all instances of the type T with the given IDs.
	 *  If some or all ids are not found, no entities are returned for these IDs.
	 *  Note that the order of elements in the result is not guaranteed.</p>
	 * 
	 * @param <T>
	 * @param ids must not be null nor contain any null values.
	 * @param function
	 * @return guaranteed to be not null. The size can be equal or less than the number of given ids.
	 * 
	 * @see {@link JpaRepository#findAllById(Iterable)}
	 */
	default <T> List<T> findAllByIds(List<I> ids, Function<E, T> function) {
		List<E> list = getRepository().findAllById(ids);
		return EntityView.of(list, function);
	}

	/**
	 * <p>Returns all instances of the type T with the given IDs.
	 *  If some or all ids are not found, no entities are returned for these IDs.
	 *  Note that the order of elements in the result is not guaranteed.</p>
	 * 
	 * @param <D>
	 * @param ids must not be null nor contain any null values.
	 * @param clazz
	 * @return guaranteed to be not null. The size can be equal or less than the number of given ids.
	 * 
	 * @see {@link JpaRepository#findAllById(Iterable)}
	 */
	default <D extends EntityView<E>> List<D> findAllByIds(List<I> ids, Class<D> clazz) {
		List<E> list = getRepository().findAllById(ids);
		return EntityView.of(list, clazz);
	}

	/**
	 * @param <T>
	 * @param filter
	 * @param function
	 * @return
	 * 
	 * @see {@link JpaSpecificationExecutor#findAll(Specification, Pageable)}
	 * @see {@link Pageable}
	 */
	default <T> Optional<T> findFirst(@Nullable PageableFilter<E> filter, Function<E, T> function) {
		Page<E> page = getRepository().findAll(filter==null? null: filter.getSpecification(), filter==null? Pageable.unpaged(): filter.getPageable());
		Optional<E> entity = page.stream().findFirst();
		return EntityView.of(entity, function);
	}

	/**
	 * @param <D>
	 * @param filter
	 * @param clazz
	 * @return
	 * 
	 * @see {@link JpaSpecificationExecutor#findAll(Specification, Pageable)}
	 * @see {@link Pageable}
	 */
	default <D extends EntityView<E>> Optional<D> findFirst(@Nullable PageableFilter<E> filter, Class<D> clazz) {
		Page<E> page = getRepository().findAll(filter==null? null: filter.getSpecification(), filter==null? Pageable.unpaged(): filter.getPageable());
		Optional<E> entity = page.stream().findFirst();
		return EntityView.of(entity, clazz);
	}

	/**
	 * <p>Returns a single entity matching the given {@link Specification} or {@link Optional#empty()} if none found.</p>
	 * 
	 * @param <T>
	 * @param filter can be null.
	 * @param function
	 * @return never null.
	 * 
	 * @see {@link JpaSpecificationExecutor#findOne(Specification)}
	 */
	default <T> Optional<T> findOne(@Nullable Filter<E> filter, Function<E, T> function) {
		Optional<E> entity = getRepository().findOne(filter==null? null: filter.getSpecification());
		return EntityView.of(entity, function);
	}

	/**
	 * <p>Returns a single entity matching the given {@link Specification} or {@link Optional#empty()} if none found.</p>
	 * 
	 * @param <D>
	 * @param filter can be null.
	 * @param clazz
	 * @return never null.
	 * 
	 * @see {@link JpaSpecificationExecutor#findOne(Specification)}
	 */
	default <D extends EntityView<E>> Optional<D> findOne(@Nullable Filter<E> filter, Class<D> clazz) {
		Optional<E> entity = getRepository().findOne(filter==null? null: filter.getSpecification());
		return EntityView.of(entity, clazz);
	}

	/**
	 * <p>Retrieves an entity by its id.</p>
	 * 
	 * @param <T>
	 * @param id must not be null.
	 * @param function
	 * @return the entity with the given id or {@link Optional#empty()} if none found.
	 * 
	 * @see {@link CrudRepository#findById(Object)}
	 */
	default <T> Optional<T> findById(I id, Function<E, T> function) {
		Optional<E> optional = getRepository().findById(id);
		return EntityView.of(optional, function);
	}

	/**
	 * <p>Retrieves an entity by its id.</p>
	 * 
	 * @param <D>
	 * @param id must not be null.
	 * @param clazz
	 * @return the entity with the given id or {@link Optional#empty()} if none found.
	 * 
	 * @see {@link CrudRepository#findById(Object)}
	 */
	default <D extends EntityView<E>> Optional<D> findById(I id, Class<D> clazz) {
		Optional<E> optional = getRepository().findById(id);
		return EntityView.of(optional, clazz);
	}

	/**
	 * <p>Saves all given entities.</p>
	 * 
	 * @param <T>
	 * @param <F>
	 * @param list must not be null nor must it contain null.
	 * @param function
	 * @return the saved entities; will never be null. The returned Iterable will have the same sizeas the Iterable passed as an argument.
	 * 
	 * @see {@link JpaRepository#saveAll(Iterable)}
	 */
	default <T, F extends FormEntity<E>> List<T> saveAll(List<F> list, Function<E, T> function) {
		List<E> saved = getRepository().saveAll(FormEntity.of(list));
		return EntityView.of(saved, function);
	}

	/**
	 * <p>Saves all given entities.</p>
	 * 
	 * @param <D>
	 * @param <F>
	 * @param list must not be null nor must it contain null.
	 * @param clazz
	 * @return the saved entities; will never be null. The returned Iterable will have the same sizeas the Iterable passed as an argument.
	 * 
	 * @see {@link JpaRepository#saveAll(Iterable)}
	 */
	default <D extends EntityView<E>, F extends FormEntity<E>> List<D> saveAll(List<F> list, Class<D> clazz) {
		List<E> saved = getRepository().saveAll(FormEntity.of(list));
		return EntityView.of(saved, clazz);
	}

	/**
	 * <p>Saves all given entities.</p>
	 * 
	 * @param <T>
	 * @param <F>
	 * @param ids must not be null nor contain any null values.
	 * @param form
	 * @param function
	 * @return the saved entities; will never be null. The returned Iterable will have the same size as the Iterable passed as an argument.
	 * 
	 * @see {@link JpaRepository#saveAll(Iterable)}
	 */
	default <T, F extends UpdatableEntity<E>> List<T> changeAll(List<I> ids, F form, Function<E, T> function) {
		
		List<E> found = getRepository().findAllById(ids);
		if(found==null || found.isEmpty()) {
			return EntityView.of(found, function);
		}
		
		List<E> saved = getRepository().saveAll(UpdatableEntity.of(found, form));
		return EntityView.of(saved, function);
	}

	/**
	 * <p>Saves all given entities.</p>
	 * 
	 * @param <D>
	 * @param <F>
	 * @param ids must not be null nor contain any null values.
	 * @param form
	 * @param clazz
	 * @return the saved entities; will never be null. The returned Iterable will have the same size as the Iterable passed as an argument.
	 * 
	 * @see {@link JpaRepository#saveAll(Iterable)}
	 */
	default <D extends EntityView<E>, F extends UpdatableEntity<E>> List<D> changeAll(List<I> ids, F form, Class<D> clazz) {
		
		List<E> found = getRepository().findAllById(ids);
		if(found==null || found.isEmpty()) {
			return EntityView.of(found, clazz);
		}
		
		List<E> saved = getRepository().saveAll(UpdatableEntity.of(found, form));
		return EntityView.of(saved, clazz);
	}

	/**
	 * <P>Saves a given entity. Use the returned instance for further operations
	 *  as the save operation might have changed the entity instance completely.</p>
	 * 
	 * @param <T>
	 * @param <F>
	 * @param form must not be null.
	 * @param function
	 * @return the saved entity; will never be null.
	 * 
	 * @see {@link CrudRepository#save(Object)}
	 */
	default <T, F extends FormEntity<E>> T save(F form, Function<E, T> function) {
		E saved = getRepository().save(form.getEntity());
		return function==null? null: function.apply(saved);
	}

	/**
	 * <P>Saves a given entity. Use the returned instance for further operations
	 *  as the save operation might have changed the entity instance completely.</p>
	 * 
	 * @param <D>
	 * @param <F>
	 * @param form must not be null.
	 * @param clazz
	 * @return the saved entity; will never be null.
	 * 
	 * @see {@link CrudRepository#save(Object)}
	 */
	default <D extends EntityView<E>, F extends FormEntity<E>> D save(F form, Class<D> clazz) {
		E saved = getRepository().save(form.getEntity());
		return EntityView.of(saved, clazz);
	}

	/**
	 * <P>Saves a given entity. Use the returned instance for further operations
	 *  as the save operation might have changed the entity instance completely.</p>
	 * 
	 * @param <T>
	 * @param <F>
	 * @param id
	 * @param form
	 * @param function
	 * @return the saved entity; will never be null.
	 * 
	 * @see {@link CrudRepository#save(Object)}
	 */
	default <T, F extends UpdatableEntity<E>> T change(I id, F form, Function<E, T> function) {
		
		Optional<E> optional = getRepository().findById(id);
		if(!optional.isPresent()) {
			return null;
		}
		
		E saved = getRepository().save(UpdatableEntity.of(optional.get(), form));
		return function==null? null: function.apply(saved);
	}

	/**
	 * <P>Saves a given entity. Use the returned instance for further operations
	 *  as the save operation might have changed the entity instance completely.</p>
	 * 
	 * @param <D>
	 * @param <F>
	 * @param id
	 * @param form
	 * @param clazz
	 * @return the saved entity; will never be null.
	 * 
	 * @see {@link CrudRepository#save(Object)}
	 */
	default <D extends EntityView<E>, F extends UpdatableEntity<E>> D change(I id, F form, Class<D> clazz) {
		
		Optional<E> optional = getRepository().findById(id);
		if(!optional.isPresent()) {
			return null;
		}
		
		E saved = getRepository().save(UpdatableEntity.of(optional.get(), form));
		return EntityView.of(saved, clazz);
	}

	/**
	 * <p>Deletes the given entities.</p>
	 * 
	 * @param <F>
	 * @param list must not be null. Must not contain null elements.
	 * @return
	 * 
	 * @see {@link CrudRepository#deleteAll(Iterable)}
	 */
	default <F extends FormEntity<E>> int deleteAll(List<F> list) {
		getRepository().deleteAll(FormEntity.of(list));
		return list.size();
	}

	/**
	 * <p>Deletes the given entities in a batch which means it will create a single query.
	 *  This kind of operation leaves JPAsfirst level cache and the database out of sync.
	 *  Consider flushing the EntityManager before calling this method.</p>
	 * 
	 * @param filter
	 * @return entities to be deleted. Must not be null.
	 * 
	 * @see {@link JpaRepository#deleteAllInBatch(Iterable)}
	 */
	default int delete(Filter<E> filter) {
		List<E> entities = getRepository().findAll(filter.getSpecification());
		getRepository().deleteAllInBatch(entities);
		return entities.size();
	}

	/**
	 * <p>Deletes the given entities in a batch which means it will create a single query.
	 *  This kind of operation leaves JPAsfirst level cache and the database out of sync.
	 *  Consider flushing the EntityManager before calling this method.</p>
	 * 
	 * @param ids
	 * @return entities to be deleted. Must not be null.
	 * 
	 * @see {@link JpaRepository#deleteAllInBatch(Iterable)}
	 */
	default int deleteAllByIds(List<I> ids) {
		List<E> entities = getRepository().findAllById(ids);
		getRepository().deleteAllInBatch(entities);
		return entities.size();
	}

	/**
	 * <p>Deletes the entity with the given id.</p>
	 * 
	 * @param id must not be null.
	 * @return 1
	 * @throws IllegalArgumentException in case the given id is null
	 * 
	 * @see {@link CrudRepository#deleteById(Object)}
	 */
	default int deleteById(I id) {
		getRepository().deleteById(id);
		return 1;
	}
}
