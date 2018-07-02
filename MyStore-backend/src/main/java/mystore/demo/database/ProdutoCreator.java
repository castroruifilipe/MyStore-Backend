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
            "https://airmore.com/wp-content/uploads/2018/04/play-pubg-mobile-on-pc.jpg",
            "http://www.lg.com/uk/images/AC/features/UK_signature-products-airpurifier-list-LSA50A-M_170810.jpg",
            "https://www.apple.com/support/products/images/iphone-hero.jpg",
            "https://cdn.cheapism.com/images/040417_products_made_in_usa_slide_25_fs.max-784x410.jpg",
            "https://media1.popsugar-assets.com/files/thumbor/63FRo69L2l-SDx6MnOTWdAHMdb8/fit-in/1024x1024/filters:format_auto-!!-:strip_icc-!!-/2017/05/24/020/n/1922153/259c0a9659261743104de9.19448885_edit_img_cover_file_43573202_1495661587/i/Best-Hyaluronic-Acid-Products-Drugstore.jpg",
            "https://cdn2.stylecraze.com/wp-content/uploads/2013/09/1173_Best-Biotique-Face-Care-Products.jpg",
            "http://www.lg.com/us/images/TV/features/signature-products-washers-list-wm9500hka-m.jpg",
            "https://i5.walmartimages.com/asr/9db73d48-a58a-40e7-8175-4bf7f2b30b69_1.b12aa34447019965370891cc70077475.jpeg"
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
            produto.setImageURL(randomImage());
            items.add(produto);
        }
    }

    public static String randomImage() {
        return RandomCollectionUtil.choice(Arrays.asList(imageURLs));
    }

}
