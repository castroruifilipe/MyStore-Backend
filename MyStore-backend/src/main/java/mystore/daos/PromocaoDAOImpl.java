package mystore.daos;

import mystore.models.Categoria;
import mystore.models.Produto;
import mystore.models.Promocao;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.criteria.JoinType.INNER;

@Repository("promocaoDAO")
public class PromocaoDAOImpl extends GenericDAOImpl<Promocao, Long> implements PromocaoDAO {

    @Override
    public List<Promocao> listByProduto(long codigo) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Promocao> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<Promocao> root = criteriaQuery.from(type);
        Join<Promocao, Produto> promocao_produto = root.join("produto", INNER);

        Predicate emVigor = criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get("dataFim"), LocalDate.now()),
                criteriaBuilder.lessThanOrEqualTo(root.get("dataInicio"), LocalDate.now())
        );
        Predicate sameProduto = criteriaBuilder.equal(promocao_produto.get("codigo"), codigo);

        criteriaQuery
                .multiselect(promocao_produto)
                .where(criteriaBuilder.and(emVigor, sameProduto));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Promocao> listByCategoria(String descricao) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Promocao> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<Promocao> root = criteriaQuery.from(type);
        Join<Promocao, Categoria> promocao_categoria = root.join("categoria", INNER);

        Predicate emVigor = criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get("dataFim"), LocalDate.now()),
                criteriaBuilder.lessThanOrEqualTo(root.get("dataInicio"), LocalDate.now())
        );
        Predicate sameCategoria = criteriaBuilder.equal(promocao_categoria.get("descricao"), descricao);

        criteriaQuery
                .multiselect(promocao_categoria)
                .where(criteriaBuilder.and(emVigor, sameCategoria));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Promocao> listAtuais() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Promocao> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<Promocao> root = criteriaQuery.from(type);

        Predicate emVigor = criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get("dataFim"), LocalDate.now()),
                criteriaBuilder.lessThanOrEqualTo(root.get("dataInicio"), LocalDate.now())
        );

        criteriaQuery
                .select(root)
                .where(emVigor);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
