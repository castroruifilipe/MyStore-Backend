package mystore.demo.database;

import mystore.models.Cliente;
import mystore.models.Morada;
import mystore.models.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

public class ClienteCreator extends Creator<Cliente> {

    protected Collection<Produto> produtos;

    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public ClienteCreator(BCryptPasswordEncoder bCryptPasswordEncoder) {
        super();
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.produtos = null;
    }

    public ClienteCreator(Collection<Produto> produtos, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super();
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.produtos = produtos;
    }


    public void addRandomClientes(int nClientes, Collection<Produto> produtos) {
        for (int i = 0; i < nClientes; i++) {
            Cliente cli = new Cliente();
            cli.setEmail(internet.emailAddress());
            cli.setNome(name.nameWithMiddle());
            cli.setPassword(bCryptPasswordEncoder.encode("123"));
            cli.setTelemovel(phoneNumber.cellPhone());
            cli.setAtivo(true);
            cli.setContribuinte(idNumber.valid());

            Morada morada = new Morada();
            morada.setRua(address.streetAddress());
            morada.setCodigoPostal(address.zipCode());
            morada.setLocalidade(address.city());

            cli.setMorada(morada);

            if (produtos != null) {
                EncomendaCreator encomendaCreator = new EncomendaCreator();
                encomendaCreator.addRandomEncomendas(number.numberBetween(0, 10), produtos, cli);
                cli.setEncomendas(encomendaCreator.getItems());
            }

            items.add(cli);
        }
    }

    public void addCliente(String email, String name, String password) {
        Cliente cli = new Cliente();
        cli.setEmail(email);
        cli.setNome(name);
        cli.setPassword(bCryptPasswordEncoder.encode(password));
        cli.setTelemovel(phoneNumber.cellPhone());
        cli.setAtivo(true);
        cli.setContribuinte(idNumber.valid());

        Morada morada = new Morada();
        morada.setRua(address.streetAddress());
        morada.setCodigoPostal(address.zipCode());
        morada.setLocalidade(address.city());

        cli.setMorada(morada);

        items.add(cli);
    }

}
