package mystore.daos;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, K> {

    Optional<T> find(K id);

    List<T> getAll();

    List<T> find(String property, Object value);

    Optional<T> findUnique(String property, Object value);

    boolean contains(String property, Object value);

    void delete(T object);

    void save(T object);

    void update(T object);

}
