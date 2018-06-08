package mystore.demo.database;

import com.github.javafaker.Faker;
import mystore.models.LinhaEncomenda;

import java.util.Locale;
import java.util.Set;

public class LinhaEncomendaCreator {

    protected Faker faker;
    protected Set<LinhaEncomenda> linhasEncomendasCriadas;

    public LinhaEncomendaCreator(){
        this.faker = new Faker(new Locale("pt"));
    }

}
