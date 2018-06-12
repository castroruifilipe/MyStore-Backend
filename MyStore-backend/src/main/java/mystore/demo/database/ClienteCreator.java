package mystore.demo.database;

import com.github.javafaker.*;
import mystore.models.Categoria;
import mystore.models.Cliente;
import mystore.models.Produto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class ClienteCreator extends Creator<Cliente> {

    protected Collection<Produto> produtos;

    public ClienteCreator(){
        super();
        this.produtos = null;
    }

    public ClienteCreator(Collection<Produto> produtos){
        super();
        this.produtos = produtos;
    }


    public void addRandomClientes(int nClientes, Collection<Produto> produtos){
        for(int i=0; i<nClientes;i++){
            Cliente cli = new Cliente();
            cli.setEmail(internet.emailAddress());
            cli.setNome(name.nameWithMiddle());
            cli.setPassword("123");
            cli.setTelemovel(phoneNumber.cellPhone());
            cli.setAtivo(true);
            cli.setUrlImagem(internet.avatar());

            cli.setMorada(address.fullAddress());
            cli.setContribuinte(idNumber.valid());
            if(produtos != null){
                EncomendaCreator encomendaCreator = new EncomendaCreator();
                encomendaCreator.addRandomEncomendas(number.numberBetween(0,10),produtos);
                cli.setEncomendas(encomendaCreator.getItems());
            }
            items.add(cli);
        }
    }

    public void addCliente(String email, String name, String password){
        Cliente cli = new Cliente();
        cli.setEmail(email);
        cli.setNome(name);
        cli.setPassword(password);
        cli.setTelemovel(phoneNumber.cellPhone());
        cli.setAtivo(true);
        cli.setUrlImagem(internet.avatar());

        cli.setMorada(address.fullAddress());
        cli.setContribuinte(idNumber.valid());

        items.add(cli);
    }

}
