package mystore.daos;

import mystore.models.*;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.criteria.JoinType.*;

@Repository("produtoDAO")
public class ProdutoDAOImpl extends GenericDAOImpl<Produto, Long> implements ProdutoDAO {

    @Override
    public List<Produto> novidades(int quantidadeProdutos) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);
        Predicate novidade = criteriaBuilder.greaterThanOrEqualTo(root.get("dataRegisto"), LocalDateTime.now().minusDays(7));
        Predicate active = criteriaBuilder.equal(root.get("active"), true);
        Order porDataDesc = criteriaBuilder.desc(root.get("dataRegisto"));
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(novidade, active))
                .orderBy(porDataDesc);
        return entityManager.createQuery(criteriaQuery).setMaxResults(quantidadeProdutos).getResultList();
    }

    @Override
    public List<Produto> maisVendidos(int quantidadeProdutos) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<EstatisticasVendas> root = criteriaQuery.from(EstatisticasVendas.class);

        Predicate active = criteriaBuilder.equal(root.get("active"), true);

        Order porQuantidadeVendida = criteriaBuilder.desc(root.get("numeroVendas"));

        criteriaQuery
                .select(root.get("produto"))
                .where(active)
                .orderBy(porQuantidadeVendida);

        return entityManager.createQuery(criteriaQuery).setMaxResults(quantidadeProdutos).getResultList();
    }

    @Override
    public List<Produto> emPromocao(int quantidadeProdutos) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<Produto> root = criteriaQuery.from(type);
        Predicate emPromocao = criteriaBuilder.notEqual(root.get("precoPromocional"), 0.0);
        Predicate active = criteriaBuilder.equal(root.get("active"), true);

        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(emPromocao, active));
        return entityManager.createQuery(criteriaQuery).setMaxResults(quantidadeProdutos).getResultList();
    }

    @Override
    public List<Produto> listByCategoria(long categoria, int firstResult, int maxResults) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(type);
        Predicate active = criteriaBuilder.equal(root.get("active"), true);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(criteriaBuilder.equal(root.get("categoria"), categoria), active));
        return entityManager.createQuery(criteriaQuery).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    @Override
    public List<Produto> related(Produto produto, int maxResults) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<Produto> root = criteriaQuery.from(type);
        Predicate active = criteriaBuilder.equal(root.get("active"), true);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(active,
                        criteriaBuilder.equal(root.get("categoria"), produto.getCategoria().getId())),
                        criteriaBuilder.notEqual(root.get("codigo"), produto.getCodigo()));
        return entityManager.createQuery(criteriaQuery).setMaxResults(maxResults).getResultList();
    }

    @Override
    public List<Produto> search(String value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<Produto> root = criteriaQuery.from(type);
        Predicate active = criteriaBuilder.equal(root.get("active"), true);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(active, criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + value.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("descricao")), "%" + value.toLowerCase() + "%")))
                );
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Produto> search(long categoria, String value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<Produto> root = criteriaQuery.from(type);
        Predicate active = criteriaBuilder.equal(root.get("active"), true);
        criteriaQuery
                .select(root)
                .where(criteriaBuilder.and(active,
                        criteriaBuilder.or(
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + value.toLowerCase() + "%"),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("descricao")), "%" + value.toLowerCase() + "%"))),
                        criteriaBuilder.equal(root.get("categoria"), categoria)
                );
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public void updatePrices(Promocao promocao) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Produto> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(type);
        Root<Produto> root = criteriaUpdate.from(type);

        criteriaUpdate
                .set(root.<Double>get("precoPromocional"),
                        criteriaBuilder.diff(root.get("precoBase"),
                                criteriaBuilder.prod(root.get("precoBase"), promocao.getDesconto())));

        if (promocao.getCategoria() != null) {
            criteriaUpdate
                    .where(criteriaBuilder.equal(root.get("categoria"), promocao.getCategoria().getId()));
        } else {
            Set<Long> ids = promocao.getProdutos().parallelStream().map(Produto::getCodigo).collect(Collectors.toSet());
            criteriaUpdate
                    .where(root.get("codigo").in(ids));
        }
        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public void apagar(long codigo) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Produto> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(type);
        Root<Produto> root = criteriaUpdate.from(type);
        criteriaUpdate
                .set(root.get("active"), false)
                .where(criteriaBuilder.equal(root.get("codigo"), codigo));
        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }
}
