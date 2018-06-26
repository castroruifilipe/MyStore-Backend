package mystore.services;

import mystore.daos.EncomendaDAO;
import mystore.daos.LinhaEncomendaDAO;
import mystore.models.*;
import mystore.models.enums.EstadoEncomenda;
import mystore.models.enums.MetodoPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static mystore.models.enums.EstadoEncomenda.*;


@Service
@Transactional
public class EncomendaServiceImpl implements EncomendaService {

    @Autowired
    private EncomendaDAO encomendaDAO;

    @Autowired
    private LinhaEncomendaDAO linhaEncomendaDAO;


    @Override
    public void save(Encomenda encomenda) {
        for (LinhaEncomenda linha : encomenda.getLinhasEncomenda()) {
            linhaEncomendaDAO.save(linha);
        }
        encomendaDAO.save(encomenda);
        for (LinhaEncomenda linha : encomenda.getLinhasEncomenda()) {
            encomendaDAO.updateEstatisticasEncomenda(linha);
        }

        System.out.println("\n\nAQUI");
    }

    @Override
    public void update(Encomenda encomenda) {
        encomendaDAO.update(encomenda);
        for (LinhaEncomenda linha : encomenda.getLinhasEncomenda()) {
            encomendaDAO.updateEstatisticasVenda(linha);
        }
    }

    @Override
    @Transactional
    public List<Encomenda> list() {
        return encomendaDAO.getAll();
    }

    @Override
    public List<Encomenda> ultimas(int quantidadeEncomendas) {
        return encomendaDAO.ultimas(quantidadeEncomendas);
    }

    @Override
    @Transactional
    public List<Encomenda> listByCliente(long uid) {
        return null;
    }

    @Override
    public Optional<Encomenda> get(long id) {
        return encomendaDAO.find(id);
    }

    @Override
    public Encomenda checkout(Cliente cliente, Morada moradaEntrega, Carrinho carrinho, MetodoPagamento metodoPagamento) {
        Encomenda encomenda = new Encomenda();
        encomenda.setCliente(cliente);
        encomenda.setMoradaEntrega(moradaEntrega);
        encomenda.setMetodoPagamento(metodoPagamento);
        Set<LinhaEncomenda> linhasEncomenda = new HashSet<>();
        for (LinhaCarrinho linhaCarrinho : carrinho.getLinhasCarrinho()) {
            LinhaEncomenda linhaEncomenda = new LinhaEncomenda();
            linhaEncomenda.setEncomenda(encomenda);
            linhaEncomenda.setProduto(linhaCarrinho.getProduto());
            linhaEncomenda.setQuantidade(linhaCarrinho.getQuantidade());
            linhasEncomenda.add(linhaEncomenda);
        }
        encomenda.setLinhasEncomenda(linhasEncomenda);
        save(encomenda);
        return encomenda;
    }

    @Override
    public Optional<Encomenda> pagar(long id) {
        Optional<Encomenda> optionalEncomenda = encomendaDAO.find(id);
        if (optionalEncomenda.isPresent()) {
            Encomenda e = optionalEncomenda.get();
            LocalDateTime now = LocalDateTime.now();
            if (e.getDataLimitePagamento().isBefore(now.toLocalDate())) {
                return Optional.empty();
            }
            if (e.getEstado() != AGUARDA_PAGAMENTO) {
                return Optional.empty();
            }
            e.setDataPagamento(now);
            e.setEstado(EM_PROCESSAMENTO);
            update(e);
            return Optional.of(e);
        } else {
            return Optional.empty();
        }
    }
}
