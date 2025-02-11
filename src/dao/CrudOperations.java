package dao;

import java.util.List;

public interface CrudOperations<E> {
    List<E> getAll(int page, int size);

    E findById(String id);

	List<E> findByCriteria(List<Criteria> criterias, int page, int size);

	List<E> orderByCriteria(List<Criteria> criteria, int page, int size);
    // Both create (if does not exist) or update (if exist) entities
    List<E> saveAll(List<E> entities);
}
