package mystore.demo;

import mystore.argparse.Argparse;
import mystore.demo.database.CategoriaCreator;
import mystore.demo.database.ClienteCreator;
import mystore.demo.database.ProdutoCreator;
import mystore.models.Categoria;
import mystore.models.Cliente;
import mystore.models.Produto;
import mystore.services.CategoriaService;
import mystore.services.ClienteService;
import mystore.services.ProdutoService;
import net.sourceforge.argparse4j.inf.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Namespace namespace = Argparse.parse(args.getSourceArgs());

        if (namespace.getBoolean("createDB")) {
            // Create database
            createCategorias(namespace.getInt("nCategories"));
            List<Categoria> categorias = categoriaService.list();
            createProdutos(namespace.getInt("nProducts"), categorias);
            List<Produto> produtos = produtoService.list();
            createClientes(namespace.getInt("nClients"), produtos);
        }

    }

    public void createCategorias(int nCategories){
        CategoriaCreator categoriaCreator = new CategoriaCreator();
        categoriaCreator.addRandomCategories(nCategories);
        for(Categoria categoria: categoriaCreator.getItems()){
            categoriaService.save(categoria);
        }
    }

    public void createClientes(int nClientes, Collection<Produto> produtos){
        ClienteCreator clienteCreator = new ClienteCreator();
        clienteCreator.addCliente("ruicastroleite@outlook.com","Rui Leite", "123");
        clienteCreator.addCliente("diogomachado@gmail.com","Diogo Machado", "123");
        clienteCreator.addCliente("andrerfcsantos@gmail.com","André Santos", "123");
        clienteCreator.addRandomClientes(nClientes, produtos);
        for(Cliente cliente: clienteCreator.getItems()){
            clienteService.save(cliente);
        }
    }

    public void createProdutos(int nProdutos, Collection<Categoria> categorias){
        ProdutoCreator produtoCreator = new ProdutoCreator();
        produtoCreator.addRandomProducts(nProdutos, categorias);
        for(Produto produto: produtoCreator.getItems()){
            produtoService.save(produto);
        }
    }




//    private void addLoja() {
//        Set<Produto> produtos = new HashSet<>(produtoService.list());
//        Loja loja = new Loja();
//        loja.setNome("MyStore");
//        loja.setLocalizacao(faker.address().fullAddress());
//        loja.setProdutos(produtos);
//        lojaService.save(loja);
//    }

}
