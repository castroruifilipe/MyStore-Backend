package mystore.demo;

import mystore.argparse.Argparse;
import mystore.demo.database.*;
import mystore.models.*;
import mystore.services.*;
import net.sourceforge.argparse4j.inf.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


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
    PromocaoService promocaoService;

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
            createClientes(namespace.getInt("nClients"), produtos);
            System.out.println("\nInseridos clientes.");
            createPromocoes(produtos, categorias);
            System.out.println("\nInseridas promoções.");
        }

    }

    public void createCategorias(int nCategories) {
        CategoriaCreator categoriaCreator = new CategoriaCreator();
        categoriaCreator.addRandomCategories(nCategories);
        for (Categoria categoria : categoriaCreator.getItems()) {
            categoriaService.save(categoria);
        }
    }

    public void createClientes(int nClientes, Collection<Produto> produtos) {
        ClienteCreator clienteCreator = new ClienteCreator(bCryptPasswordEncoder);
        clienteCreator.addCliente("ruicastroleite@outlook.com", "Rui Leite", "123");
        clienteCreator.addCliente("diogomachado@gmail.com", "Diogo Machado", "123");
        clienteCreator.addCliente("andrerfcsantos@gmail.com", "André Santos", "123");
        clienteCreator.addRandomClientes(nClientes, produtos);
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

}
