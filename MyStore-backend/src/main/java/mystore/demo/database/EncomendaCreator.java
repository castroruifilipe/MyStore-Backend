package mystore.demo.database;

import mystore.demo.RandomCollectionUtil;
import mystore.models.*;
import mystore.models.enums.EstadoEncomenda;
import mystore.models.enums.MetodoPagamento;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class EncomendaCreator extends Creator<Encomenda> {


    public EncomendaCreator() {
        super();
    }

    public void addRandomEncomendas(int nEncomendas, Collection<Produto> produtos, Cliente cli) {
        Collection<EstadoEncomenda> estados = Arrays.asList(EstadoEncomenda.values());
        Collection<MetodoPagamento> metodosPagamento = Arrays.asList(MetodoPagamento.values());

        for (int i = 0; i < nEncomendas; i++) {
            EstadoEncomenda estado = RandomCollectionUtil.choice(estados);
            LocalDate data = dateAndTime.past(700, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dataPagamento = dateAndTime.past(10, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            MetodoPagamento metodoPagamento = RandomCollectionUtil.choice(metodosPagamento);
            int nLinhas = number.numberBetween(0, 10);

            Morada morada = new Morada();
            morada.setRua(address.streetAddress());
            morada.setCodigoPostal(address.zipCode());
            morada.setLocalidade(address.city());

            Encomenda encomenda = new Encomenda();
            encomenda.setData(data);
            encomenda.setMoradaEnvio(morada);
            encomenda.setMoradaFaturacao(cli.getMorada());
            encomenda.setEstado(estado);
            encomenda.setTrackingID(number.numberBetween(0, 10000000));
            encomenda.setPortes(number.randomDouble(2, 0, 4));
            encomenda.setMetodoPagamento(metodoPagamento);
            encomenda.setCliente(cli);

            Set<LinhaEncomenda> linhas = createLinhasEncomenda(encomenda, nLinhas, produtos);
            encomenda.setLinhasEncomenda(linhas);

            double total = linhas
                    .stream()
                    .mapToDouble(linha -> linha.getQuantidade() * (linha.getPrecoUnitario() - linha.getValorDesconto()))
                    .sum();

            encomenda.setTotal(total);

            if (encomenda.getEstado() != EstadoEncomenda.AGUARDA_PAGAMENTO
                    && encomenda.getEstado() != EstadoEncomenda.CANCELADA) {
                encomenda.setDataPagamento(dataPagamento);
            }

            items.add(encomenda);
        }
    }

    private Set<LinhaEncomenda> createLinhasEncomenda(Encomenda encomenda, int nLinhas, Collection<Produto> produtos) {
        Set<LinhaEncomenda> linhas = new HashSet<>();

        for (int i = 0; i < nLinhas; i++) {
            Produto rp = RandomCollectionUtil.choice(produtos);

            LinhaEncomenda linhaEncomenda = new LinhaEncomenda();
            linhaEncomenda.setProduto(rp);
            linhaEncomenda.setEncomenda(encomenda);
            linhaEncomenda.setQuantidade(number.numberBetween(1, 5));
            linhaEncomenda.setValorDesconto(0);
            double precoUnitario = rp.getPrecoBase() * (1 + rp.getIva());
            linhaEncomenda.setPrecoUnitario(precoUnitario);

            linhas.add(linhaEncomenda);
        }

        return linhas;
    }

}
