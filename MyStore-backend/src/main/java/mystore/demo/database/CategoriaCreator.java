package mystore.demo.database;

import com.github.javafaker.Commerce;
import com.github.javafaker.Faker;
import mystore.models.Categoria;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe para gerir a criação de categorias.
 */
public class CategoriaCreator extends Creator<Categoria> {

    public CategoriaCreator(){
        super();
    }

    /**
     * Adiciona categorias aleatórias recorrendo ao faker.
     * @param maxCategorias tenta adicionar este número de categorias.
     *                      Como as categorias do faker podem ser repetidas,
     *                      o número de categorias adicionadas pode ser inferior.
     */
    public void addRandomCategories(int maxCategorias){
        for(int i=0; i<maxCategorias;i++){
            Categoria cat = new Categoria();
            cat.setDescricao(commerce.department());
            cat.setId(getAndIncrementId());
            items.add(cat);
        }
    }

    /**
     * Adiciona categoria
     * @param categoria categoria a adicionar.
     */
    public void addCategoriaWithDesc(String categoria){
        Categoria cat = new Categoria();
        cat.setDescricao(commerce.department());
        cat.setId(getAndIncrementId());
        items.add(cat);
    }


}
