package mystore.daos;

import mystore.models.Encomenda;
import mystore.models.EstatisticasVendas;
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

    @Override
    public void updateEstatisticasEncomenda(LinhaEncomenda linhaEncomenda) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<EstatisticasVendas> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(EstatisticasVendas.class);
        Root<EstatisticasVendas> root = criteriaUpdate.from(EstatisticasVendas.class);

        Path<Integer> numeroEncomendas = root.get("numeroEncomendas");
        Path<Long> codigoProduto = root.get("produto");

        criteriaUpdate
                .set(numeroEncomendas, criteriaBuilder.sum(numeroEncomendas, linhaEncomenda.getQuantidade()))
                .where(criteriaBuilder.equal(codigoProduto, linhaEncomenda.getProduto().getCodigo()));
        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public void updateEstatisticasVenda(LinhaEncomenda linhaEncomenda) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<EstatisticasVendas> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(EstatisticasVendas.class);
        Root<EstatisticasVendas> root = criteriaUpdate.from(EstatisticasVendas.class);

        Path<Integer> numeroVendas = root.get("numeroVendas");
        Path<Integer> numeroVendasPromocao = root.get("numeroVendasPromocao");
        Path<Double> totalFaturado = root.get("totalFaturado");
        Path<Long> codigoProduto = root.get("produto");

        criteriaUpdate
                .set(numeroVendas, criteriaBuilder.sum(numeroVendas, linhaEncomenda.getQuantidade()))
                .set(totalFaturado, criteriaBuilder.sum(totalFaturado, linhaEncomenda.getSubTotal()));

        if (linhaEncomenda.getValorDesconto() != 0) {
            criteriaUpdate
                    .set(numeroVendasPromocao, criteriaBuilder.sum(numeroVendasPromocao, linhaEncomenda.getQuantidade()));
        }

        criteriaUpdate
                .where(criteriaBuilder.equal(codigoProduto, linhaEncomenda.getProduto().getCodigo()));
        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }
}
