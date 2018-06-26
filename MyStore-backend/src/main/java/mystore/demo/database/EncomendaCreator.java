package mystore.demo.database;

import mystore.demo.RandomCollectionUtil;
import mystore.models.*;
import mystore.models.enums.EstadoEncomenda;
import mystore.models.enums.MetodoPagamento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static mystore.models.enums.EstadoEncomenda.*;

public class EncomendaCreator extends Creator<Encomenda> {


    public EncomendaCreator() {
        super();
    }

    public List<Encomenda> addRandomEncomendas(int nEncomendas, Collection<Produto> produtos, Collection<Cliente> clientes) {
        Collection<EstadoEncomenda> estados = Arrays.asList(AGUARDA_PAGAMENTO, CANCELADA);
        Collection<MetodoPagamento> metodosPagamento = Arrays.asList(MetodoPagamento.values());

        List<Encomenda> encomendas = new ArrayList<>();

        for (int i = 0; i < nEncomendas; i++) {
            Cliente cli = RandomCollectionUtil.choice(clientes);
            EstadoEncomenda estado = RandomCollectionUtil.choice(estados);
            LocalDateTime data = dateAndTime.past(700, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            MetodoPagamento metodoPagamento = RandomCollectionUtil.choice(metodosPagamento);
            int nLinhas = number.numberBetween(0, 10);

            Morada morada = new Morada();
            morada.setRua(address.streetAddress());
            morada.setCodigoPostal(address.zipCode());
            morada.setLocalidade(address.city());

            Encomenda encomenda = new Encomenda();
            encomenda.setDataRegisto(data);
            encomenda.setMoradaEntrega(morada);
            encomenda.setEstado(estado);
            encomenda.setMetodoPagamento(metodoPagamento);
            encomenda.setCliente(cli);

            Set<LinhaEncomenda> linhas = createLinhasEncomenda(encomenda, nLinhas, produtos);
            encomenda.setLinhasEncomenda(linhas);

            encomendas.add(encomenda);
        }
        return encomendas;
    }

    private Set<LinhaEncomenda> createLinhasEncomenda(Encomenda encomenda, int nLinhas, Collection<Produto> produtos) {
        Set<LinhaEncomenda> linhas = new HashSet<>();

        for (int i = 0; i < nLinhas; i++) {
            Produto rp = RandomCollectionUtil.choice(produtos);

            LinhaEncomenda linhaEncomenda = new LinhaEncomenda();
            linhaEncomenda.setProduto(rp);
            linhaEncomenda.setEncomenda(encomenda);
            linhaEncomenda.setQuantidade(number.numberBetween(1, 5));
            linhas.add(linhaEncomenda);
        }

        return linhas;
    }

}
