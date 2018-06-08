package mystore.demo.database;

import mystore.models.Produto;


public class ProdutoCreator extends Creator<Produto> {

    public ProdutoCreator(){
        super();
    }

    public void addRandomProducts(int nProducts){
        for(int i=0; i<nProducts;i++){
            Produto produto = new Produto();
            produto.setNome(commerce.productName());
            produto.setDescricao(lorem.paragraph());
            produto.setIva(number.numberBetween(10,100) / 100.0f);
            produto.setPrecoBase(number.randomDouble(2, 1,100));
            produto.setStock(number.numberBetween(0,300));
            items.add(produto);
        }
    }


}
