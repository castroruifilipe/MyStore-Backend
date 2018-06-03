package mystore.daos;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public abstract class GenericDAOImpl<T, K extends Serializable> implements GenericDAO<T, K> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> type;


    public GenericDAOImpl() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        this.type = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    @Override
    public Optional<T> find(K id) {
        return Optional.ofNullable(entityManager.find(type, id));
    }

    @Override
    public List<T> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<T> find(String property, Object value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get(property), value));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<T> findUnique(String property, Object value) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
            Root<T> root = criteriaQuery.from(type);
            criteriaQuery
                    .select(root)
                    .where(criteriaBuilder.equal(root.get(property), value));
            return Optional.of(entityManager.createQuery(criteriaQuery).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

    @Override
    public boolean contains(String property, Object value) {
        return findUnique(property, value).isPresent();
    }

    @Override
    public void delete(T object) {
        entityManager.remove(object);
    }

    @Override
    public void save(T object) {
        entityManager.persist(object);
    }

    @Override
    public void update(T object) {
        entityManager.merge(object);
    }
}
