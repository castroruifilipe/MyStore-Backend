package mystore.controllers;

import mystore.models.Categoria;
import mystore.models.Produto;
import mystore.services.CategoriaService;
import mystore.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaService categoriaService;


    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public List<Produto> list() {
        return produtoService.list();
    }

    @RequestMapping(value = "/{codigo}", method = GET)
    public Produto get(@PathVariable long codigo) {
        return produtoService.get(codigo).orElseThrow(() -> new EntityNotFoundException("Produto não existe"));
    }

    @RequestMapping(value = "/novidades/{quantidadeProdutos}", method = GET)
    public List<Produto> novidades(@PathVariable int quantidadeProdutos) {
        return produtoService.novidades(quantidadeProdutos);
    }

    @RequestMapping(value = "/maisVendidos/{quantidadeProdutos}", method = GET)
    public List<Produto> maisVendidos(@PathVariable int quantidadeProdutos) {
        return produtoService.maisVendidos(quantidadeProdutos);
    }

    @RequestMapping(value = "/categoria", method = GET)
    public List<Produto> ofCategoria(@RequestParam String categoria, @RequestParam int pagina, @RequestParam int size) {
        Optional<Categoria> categoria_obj = categoriaService.get(categoria);
        if (categoria_obj.isPresent()) {
            return produtoService.porCategoria(categoria_obj.get().getId(), pagina, size);
        } else {
            return new ArrayList<>();
        }
    }

    @RequestMapping(value = "/search", method = GET)
    public List<Produto> search(@RequestParam String value) {
        return produtoService.search(value);
    }

    @RequestMapping(value = "/search/categoria", method = GET)
    public List<Produto> search(@RequestParam String categoria, @RequestParam String value) {
        Optional<Categoria> categoria_obj = categoriaService.get(categoria);
        if (categoria_obj.isPresent()) {
            return produtoService.search(categoria_obj.get().getId(), value);
        } else {
            return new ArrayList<>();
        }
    }

    @RequestMapping(value = "/relacionados/{quantidadeProdutos}", method = GET)
    public List<Produto> relacionados(@PathVariable int quantidadeProdutos, @RequestParam long codigo) {
        Optional<Produto> optionalProduto = produtoService.get(codigo);
        if (optionalProduto.isPresent()) {
            long categoria = optionalProduto.get().getCategoria().getId();
            return produtoService.porCategoria(categoria, quantidadeProdutos);
        }
        throw new EntityNotFoundException("Produto não existe");
    }

}
