package mystore.services;

import mystore.daos.CategoriaDAO;
import mystore.daos.ProdutoDAO;
import mystore.models.Categoria;
import mystore.models.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoDAO produtoDAO;

    @Autowired
    private CategoriaDAO categoriaDAO;


    @Override
    @Transactional
    public List<Produto> list() {
        return produtoDAO.find("active", true);
    }

    @Override
    public Optional<Produto> get(long codigo) {
        return produtoDAO.find(codigo);
    }

    @Override
    public List<Produto> novidades(int quantidadeProdutos) {
        return produtoDAO.novidades(quantidadeProdutos);
    }

    @Override
    public List<Produto> maisVendidos(int quantidadeProdutos) {
        return produtoDAO.maisVendidos(quantidadeProdutos);
    }

    @Override
    @Transactional
    public List<Produto> maisVendidosDetail(int quantidadeProdutos) {
        return produtoDAO.maisVendidos(quantidadeProdutos);
    }

    @Override
    public List<Produto> emPromocao(int quantidadeProdutos) {
        return produtoDAO.emPromocao(quantidadeProdutos);
    }

    @Override
    public List<Produto> porCategoria(long categoria, int pagina, int size) {
        return produtoDAO.listByCategoria(categoria, (pagina - 1) * size, size);
    }

    @Override
    public List<Produto> related(Produto produto, int size) {
        return produtoDAO.related(produto, size);
    }

    @Override
    public List<Produto> search(String value, int pagina, int size) {
        return produtoDAO.search(value, (pagina - 1) * size, size);
    }

    @Override
    public List<Produto> search(long categoria, String value, int pagina, int size) {
        return produtoDAO.search(categoria, value, (pagina - 1) * size, size);
    }

    @Override
    public void apagar(long codigo) {
        produtoDAO.apagar(codigo);
    }

    @Override
    public Optional<Produto> editar(long codigo, Map<String, String> dados) {
        Optional<Produto> optionalProduto = produtoDAO.find(codigo);
        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            if (dados.containsKey("nome")) {
                produto.setNome(dados.get("nome"));
            }
            if (dados.containsKey("precoBase")) {
                produto.setPrecoBase(Double.valueOf(dados.get("precoBase")));
            }
            if (dados.containsKey("descricao")) {
                produto.setDescricao(dados.get("descricao"));
            }
            if (dados.containsKey("stock")) {
                produto.setStock(Integer.valueOf(dados.get("stock")));
            }
            if (dados.containsKey("categoria")) {
                Optional<Categoria> optionalCategoria = categoriaDAO.find(dados.get("categoria"));
                if (optionalCategoria.isPresent()) {
                    produto.setCategoria(optionalCategoria.get());
                } else {
                    return Optional.empty();
                }
            }
            if (dados.containsKey("image") && dados.containsKey("format")) {
                produto.setImageURL(getURLFromImageStorage(dados.get("image"), dados.get("format")));
            }
            produtoDAO.update(produto);
            return Optional.of(produto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void save(Produto produto) {
        produtoDAO.save(produto);
    }

    @Override
    public Produto criar(String nome, String descricao, String[] image, double precoBase, int stock, Categoria categoria) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPrecoBase(precoBase);
        produto.setStock(stock);
        produto.setCategoria(categoria);
        produto.setImageURL(getURLFromImageStorage(image[0], image[1]));
        save(produto);
        return produto;
    }

    @SuppressWarnings("unchecked")
    private String getURLFromImageStorage(String image, String format) {
        if (image == null || format == null) {
            return "";
        }

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");

        headers.setAll(map);

        Map req_payload = new HashMap();
        req_payload.put("base64", image);
        req_payload.put("format", format);

        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);
        String url = "http://base64.blurryface.pt";

        ResponseEntity<?> response = new RestTemplate().postForEntity(url, request, String.class);
        String res = (String) response.getBody();
        return res.split(":")[1].replace("\"", "").replace("}", "");
    }

}
