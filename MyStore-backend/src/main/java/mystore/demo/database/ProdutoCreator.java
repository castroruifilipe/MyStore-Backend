package mystore.demo.database;

import mystore.demo.RandomCollectionUtil;
import mystore.models.Categoria;
import mystore.models.Produto;

import java.util.Arrays;
import java.util.Collection;


public class ProdutoCreator extends Creator<Produto> {

    public static String[] imageURLs = {
            "http://bworldonline.com/wp-content/uploads/2018/01/Lenovo-Mirage-Solo-with-Daydream.jpg",
            "https://1enu9c17f1aj3pmqfi35l21c-wpengine.netdna-ssl.com/wp-content/uploads/2017/09/Screen-Shot-2017-09-08-at-4.42.47-PM.png",
            "https://www.co-27.com/wp-content/uploads/2016/08/mochila-samsonite-metropolis-luxemburgo-para-pc-17-negra-D_NQ_NP_19923-MLA20181179202_102014-O.jpg",
            "https://www.tatacliq.com/que/wp-content/uploads/2016/10/wireless-headphone.jpg",
            "https://brain-images-ssl.cdn.dixons.com/2/1/10167312/u_10167312.jpg",
            "https://assets.umart.com.au/newsite/images/201703/source_img/38431_G_1488953358907.jpg",
            "https://airmore.com/wp-content/uploads/2018/04/play-pubg-mobile-on-pc.jpg"
    };

    public ProdutoCreator() {
        super();
    }

    public void addRandomProducts(int nProducts, Collection<Categoria> categorias) {
        for (int i = 0; i < nProducts; i++) {

            Produto produto = new Produto();
            produto.setNome(commerce.productName());
            produto.setDescricao(lorem.paragraph(3));
            produto.setPrecoBase(number.randomDouble(2, 1, 100));
            produto.setStock(number.numberBetween(0, 300));
            if (categorias != null) {
                produto.setCategoria(RandomCollectionUtil.choice(categorias));
            }
            produto.setImageURL(RandomCollectionUtil.choice(Arrays.asList(imageURLs)));
            items.add(produto);
        }
    }

}
