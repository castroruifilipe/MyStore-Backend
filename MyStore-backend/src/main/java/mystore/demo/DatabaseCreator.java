package mystore.demo;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import mystore.models.Produto;
import mystore.services.MyStoreService;
import mystore.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

@Component
public class DatabaseCreator implements ApplicationRunner {

    private static final String CREATE_DB_ARG = "createDB";
    private static final int N_PRODUTOS = 30;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private MyStoreService myStoreService;

    private Random random = new Random();

    private Faker faker = new Faker(new Locale("pt"));


    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean createDB = false;
        if (args.containsOption(CREATE_DB_ARG)) {
            createDB = true;
        }
        if (createDB) {
            this.addProdutos();
        }
    }

    private void addProdutos() {
        for (int i = 0; i < N_PRODUTOS; i++) {
            Book book = faker.book();
            Produto produto = new Produto();
            produto.setNome(book.title());
            produto.setDescricao("Autor: " + book.author() + " Editora: " + book.publisher());
            produto.setPrecoBase(random.nextInt(100) + 19.99);
            produto.setIva(6);
            produto.setStock(random.nextInt(140) + 10);
            produtoService.save(produto);
        }

    }
}
