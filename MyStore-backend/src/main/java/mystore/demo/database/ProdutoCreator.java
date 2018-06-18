package mystore.demo.database;

import mystore.demo.RandomCollectionUtil;
import mystore.models.Categoria;
import mystore.models.Produto;

import java.util.Collection;


public class ProdutoCreator extends Creator<Produto> {

    public ProdutoCreator(){
        super();
    }

    public void addRandomProducts(int nProducts, Collection<Categoria> categorias){
        for(int i=0; i<nProducts;i++){

            Produto produto = new Produto();
            produto.setNome(commerce.productName());
            produto.setDescricao(lorem.paragraph(3));
            produto.setIva(number.numberBetween(10,23) / 100.0f);
            produto.setPrecoBase(number.randomDouble(2, 1,100));
            produto.setStock(number.numberBetween(0,300));
            if(categorias!=null){
                produto.setCategoria(RandomCollectionUtil.choice(categorias));
            }

            items.add(produto);
        }
    }


}
