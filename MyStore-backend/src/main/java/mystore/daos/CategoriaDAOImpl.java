package mystore.daos;

import mystore.models.Categoria;
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
}
