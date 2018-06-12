package mystore.demo.database;

import mystore.demo.RandomCollectionUtil;
import mystore.models.Encomenda;
import mystore.models.LinhaEncomenda;
import mystore.models.Produto;
import mystore.models.enums.EstadoEncomenda;
import mystore.models.enums.MetodoPagamento;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class EncomendaCreator extends Creator<Encomenda> {

    public EncomendaCreator(){
        super();
    }

    public void addRandomEncomendas(int nEncomendas, Collection<Produto> produtos){
        Collection<EstadoEncomenda> estados = Arrays.asList(EstadoEncomenda.values());
        Collection<MetodoPagamento> metodosPagamento = Arrays.asList(MetodoPagamento.values());

        for(int i=0; i< nEncomendas; i++){
            EstadoEncomenda estado = RandomCollectionUtil.choice(estados);
            LocalDate data = dateAndTime.past(700, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dataPagamento = dateAndTime.past(10,TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            MetodoPagamento metodoPagamento = RandomCollectionUtil.choice(metodosPagamento);
            int nLinhas = number.numberBetween(0,10);
            Collection<LinhaEncomenda> linhasEncomenda = createLinhasEncomenda(nLinhas,produtos);

            Encomenda encomenda = new Encomenda();
            encomenda.setData(data);
            encomenda.setEndereco(address.fullAddress());
            encomenda.setEstado(estado);
            encomenda.setTrackingID(number.numberBetween(0,10000000));
            encomenda.setPortes((float) number.randomDouble(2,0,4));
            encomenda.setLinhasEncomenda(new HashSet<>(linhasEncomenda));
            encomenda.setMetodoPagamento(metodoPagamento);
            encomenda.setId(getAndIncrementId());

            if(encomenda.getEstado() != EstadoEncomenda.AGUARDA_PAGAMENTO
                    && encomenda.getEstado() != EstadoEncomenda.CANCELADA){
                encomenda.setDataPagamento(dataPagamento);
            }

            items.add(encomenda);
        }
    }

    protected Collection<LinhaEncomenda> createLinhasEncomenda(int nLinhas, Collection<Produto> produtos){
        Collection<LinhaEncomenda> linhas = new ArrayList<>();

        for(int i=0; i<nLinhas; i++){
            Produto rp = RandomCollectionUtil.choice(produtos);

            LinhaEncomenda linhaEncomenda = new LinhaEncomenda();
            linhaEncomenda.setProduto(rp);
            linhaEncomenda.setQuantidade(number.numberBetween(1,5));
            linhaEncomenda.setValorDesconto(0);
            float precoUnitario = (float) rp.getPrecoBase() * (1+rp.getIva());
            float precoTotal = (precoUnitario * linhaEncomenda.getQuantidade()) - linhaEncomenda.getValorDesconto();
            linhaEncomenda.setPreco(precoTotal);

            linhas.add(linhaEncomenda);
        }

        return linhas;
    }

}
