package mystore.daos;

import mystore.models.Produto;
import mystore.models.Promocao;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository("produtoDAO")
public class ProdutoDAOImpl extends GenericDAOImpl<Produto, Long> implements ProdutoDAO {

    @Override
    public List<Produto> novidades(int quantidadeProdutos) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);
        Predicate novidade = criteriaBuilder.greaterThanOrEqualTo(root.get("dataRegisto"), LocalDateTime.now().minusDays(7));
        Order porDataDesc = criteriaBuilder.desc(root.get("dataRegisto"));
        criteriaQuery
                .select(root)
                .where(novidade)
                .orderBy(porDataDesc);
        return entityManager.createQuery(criteriaQuery).setMaxResults(quantidadeProdutos).getResultList();
    }

    @Override
    public List<Produto> maisVendidos(int quantidadeProdutos) {
        return null;
    }

    @Override
    public List<Produto> findPromocao() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> produto = criteriaQuery.from(Produto.class);
        Join<Produto, Promocao> promocao = produto.join("promocoes");
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
