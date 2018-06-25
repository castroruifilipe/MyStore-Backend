package mystore.daos;

import mystore.models.Encomenda;
import mystore.models.LinhaEncomenda;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;

@Repository("encomendaDAO")
public class EncomendaDAOImpl extends GenericDAOImpl<Encomenda, Long> implements EncomendaDAO {

    @Override
    public List<Encomenda> listByCliente(long uid) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Encomenda> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<Encomenda> root = criteriaQuery.from(type);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.equal(root.get("cliente"), uid));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Encomenda> ultimas(int quantidadeEncomendas) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Encomenda> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<Encomenda> root = criteriaQuery.from(type);
        Order porDataDesc = criteriaBuilder.desc(root.get("dataRegisto"));
        criteriaQuery
                .select(root)
                .orderBy(porDataDesc);
        return entityManager.createQuery(criteriaQuery).setMaxResults(quantidadeEncomendas).getResultList();
    }
}
