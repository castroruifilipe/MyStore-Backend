package mystore.daos;

import mystore.models.LinhaEncomenda;
import mystore.models.Produto;
import mystore.models.Promocao;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<LinhaEncomenda> root = criteriaQuery.from(LinhaEncomenda.class);

        Join<LinhaEncomenda, Produto> linhaEncomenda_produto = root.join("produto", JoinType.INNER);

        Expression<?>[] expressions = new Expression[2];
        expressions[0] = linhaEncomenda_produto.get("codigo");
        expressions[1] = criteriaBuilder.sum(root.get("quantidade"));

        Order porQuantidadeComprada = criteriaBuilder.desc(criteriaBuilder.sum(root.get("quantidade")));

        criteriaQuery
                .multiselect(expressions)
                .groupBy(linhaEncomenda_produto.get("codigo"))
                .orderBy(porQuantidadeComprada);

        List<Object[]> result = entityManager.createQuery(criteriaQuery).setMaxResults(quantidadeProdutos).getResultList();
        result.forEach(objects -> System.out.println(objects[0] + "\t\t" + objects[1]));
        return result.parallelStream().map(objects -> find((long) objects[0]).get()).collect(Collectors.toList());
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
