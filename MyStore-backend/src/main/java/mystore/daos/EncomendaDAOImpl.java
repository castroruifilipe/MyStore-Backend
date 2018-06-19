package mystore.daos;

import mystore.models.Encomenda;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("encomendaDAO")
public class EncomendaDAOImpl extends GenericDAOImpl<Encomenda, Long> implements EncomendaDAO {

    @Override
    public List<Encomenda> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Encomenda> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<Encomenda> root = criteriaQuery.from(type);
        root.fetch("cliente", JoinType.INNER);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
