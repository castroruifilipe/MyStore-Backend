package mystore.demo.database;

import mystore.demo.RandomCollectionUtil;
import mystore.models.Categoria;
import mystore.models.Produto;
import mystore.models.Promocao;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.DAYS;


public class PromocaoCreator extends Creator<Promocao> {

    public PromocaoCreator() {
        super();
    }

    public void addRandomPromocoes(List<Produto> produtos, List<Categoria> categorias) {
        for (int i = 0; i < 40; i++) {
            Promocao promocao = new Promocao();

            int f = number.numberBetween(0, 1);
            if (f == 0) {
                promocao.setCategoria(categorias.get(number.numberBetween(0, categorias.size() - 1)));
            } else {
                int index1 = number.numberBetween(0, produtos.size() - 1);
                int index2 = number.numberBetween(index1, produtos.size() - 1);
                promocao.setProdutos(new HashSet<>(produtos.subList(index1, index2)));
            }

            promocao.setDesconto(number.numberBetween(10, 80));

            LocalDate inicio, fim;
            Date inicio_d, fim_d;
            f = number.numberBetween(0, 2);
            if (f == 0) {
                fim_d = dateAndTime.past(number.numberBetween(5, 60), DAYS);
                inicio_d = dateAndTime.past(number.numberBetween(2, 10), DAYS, fim_d);
            } else if (f == 2) {
                inicio_d = dateAndTime.future(number.numberBetween(10, 20), DAYS);
                fim_d = dateAndTime.future(number.numberBetween(2, 10), DAYS, inicio_d);
            } else {
                inicio_d = dateAndTime.past(number.numberBetween(10, 15), DAYS);
                fim_d = dateAndTime.future(number.numberBetween(1, 15), DAYS, inicio_d);
            }

            inicio = Instant.ofEpochMilli(inicio_d.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            fim = Instant.ofEpochMilli(fim_d.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            promocao.setDataInicio(inicio);
            promocao.setDataFim(fim);

            promocao.setDescricao(lorem.sentence(4));

            items.add(promocao);
        }
    }

}
