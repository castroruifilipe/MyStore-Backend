package mystore.daos;

import mystore.models.Produto;
import mystore.models.Promocao;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("produtoDAO")
public class ProdutoDAOImpl extends GenericDAOImpl<Produto, Long> implements ProdutoDAO {

    @Override
    public List<Produto> findPromocao() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> produto = criteriaQuery.from(Produto.class);
        Join<Produto, Promocao> promocao = produto.join("promocoes");
        /*
        criteriaQuery
                .select(produto)
                .where(criteriaBuilder.all(root.get("promocoes"), ));
        */
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
