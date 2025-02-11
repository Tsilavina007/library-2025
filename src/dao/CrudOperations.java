package dao;

import java.util.List;

public interface CrudOperations<E> {
    List<E> getAll(int page, int size);

    E findById(String id);

	List<E> findByCriteria(List<Criteria> criterias);

    // Both create (if does not exist) or update (if exist) entities
    List<E> saveAll(List<E> entities);
}
