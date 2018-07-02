package mystore.demo;

import mystore.argparse.Argparse;
import mystore.demo.database.*;
import mystore.models.*;
import mystore.models.enums.EstadoEncomenda;
import mystore.services.*;
import net.sourceforge.argparse4j.inf.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

import static mystore.models.enums.EstadoEncomenda.EM_PROCESSAMENTO;


@Component
public class DatabaseCreator implements ApplicationRunner {

    private static final String CREATE_DB_ARG = "createDB";
    private static final int N_PRODUTOS = 30;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PromocaoService promocaoService;

    @Autowired
    private EncomendaService encomendaService;

    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Namespace namespace = Argparse.parse(args.getSourceArgs());

        if (namespace.getBoolean("createDB")) {
            // Create database
            createCategorias(namespace.getInt("nCategories"));
            System.out.println("\nInseridas categorias.");
            List<Categoria> categorias = categoriaService.list();

            createProdutos(namespace.getInt("nProducts"), categorias);
            System.out.println("\nInseridos produtos.");
            List<Produto> produtos = produtoService.list();

            createClientes(namespace.getInt("nClients"));
            System.out.println("\nInseridos clientes.");
            List<Cliente> clientes = clienteService.list();

            createPromocoes(produtos, categorias);
            System.out.println("\nInseridas promoções.");

            createEncomendas(produtos, clientes);
            System.out.println("\nInseridas encomendas.");
            List<Encomenda> encomendas = encomendaService.list();

            createPagamentos(encomendas);
            System.out.println("\nEncomendas pagas.");
        }

    }

    public void createCategorias(int nCategories) {
        CategoriaCreator categoriaCreator = new CategoriaCreator();
        categoriaCreator.addRandomCategories(nCategories);
        for (Categoria categoria : categoriaCreator.getItems()) {
            categoriaService.save(categoria);
        }
    }

    public void createClientes(int nClientes) {
        ClienteCreator clienteCreator = new ClienteCreator(bCryptPasswordEncoder);
        clienteCreator.addCliente("ruicastroleite@outlook.com", "Rui Leite", "123");
        clienteCreator.addCliente("diogomachado@gmail.com", "Diogo Machado", "123");
        clienteCreator.addCliente("andrerfcsantos@gmail.com", "André Santos", "123");
        clienteCreator.addRandomClientes(nClientes);
        for (Cliente cliente : clienteCreator.getItems()) {
            clienteService.save(cliente);
        }
    }

    public void createProdutos(int nProdutos, Collection<Categoria> categorias) {
        ProdutoCreator produtoCreator = new ProdutoCreator();
        produtoCreator.addRandomProducts(nProdutos, categorias);
        for (Produto produto : produtoCreator.getItems()) {
            produtoService.save(produto);
        }
    }

    public void createPromocoes(Collection<Produto> produtos, Collection<Categoria> categorias) {
        PromocaoCreator promocaoCreator = new PromocaoCreator();
        promocaoCreator.addRandomPromocoes(new ArrayList<>(produtos), new ArrayList<>(categorias));
        for (Promocao promocao : promocaoCreator.getItems()) {
            promocaoService.save(promocao);
        }
    }

    public void createEncomendas(Collection<Produto> produtos, Collection<Cliente> clientes) {
        EncomendaCreator encomendaCreator = new EncomendaCreator();
        List<Encomenda> encomendas = encomendaCreator.addRandomEncomendas(100, produtos, clientes);
        for (Encomenda encomenda : encomendas) {
            encomendaService.save(encomenda);
        }
    }

    public void createPagamentos(List<Encomenda> encomendas) {
        List<Encomenda> porPagar = encomendas.subList(0, encomendas.size() - 10);
        for (Encomenda e : porPagar) {
            encomendaService.alterarEstado(e.getId(), EM_PROCESSAMENTO);
        }
    }

}
