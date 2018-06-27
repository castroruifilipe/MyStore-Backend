package mystore.daos;

import mystore.models.Categoria;
import mystore.models.Produto;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository("categoriaDAO")
public class CategoriaDAOImpl extends GenericDAOImpl<Categoria, Long> implements CategoriaDAO {

    @Override
    public Optional<Categoria> find(String descricao) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Categoria> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<Categoria> root = criteriaQuery.from(type);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(criteriaBuilder.lower(root.get("descricao")), descricao.toLowerCase()));
        try {
            return Optional.of(entityManager.createQuery(criteriaQuery).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean canDelete(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);
        criteriaQuery
                .select(root.get("codigo"))
                .where(criteriaBuilder.equal(root.get("categoria"), id));
        return entityManager.createQuery(criteriaQuery).setMaxResults(1).getResultList().isEmpty();
    }
}
