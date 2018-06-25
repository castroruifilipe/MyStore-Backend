package mystore.services;

import mystore.daos.EncomendaDAO;
import mystore.daos.LinhaEncomendaDAO;
import mystore.models.*;
import mystore.models.enums.MetodoPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;


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
}
