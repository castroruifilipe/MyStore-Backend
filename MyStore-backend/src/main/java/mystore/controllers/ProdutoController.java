package mystore.controllers;


import mystore.models.Categoria;
import mystore.models.Produto;
import mystore.services.CategoriaService;
import mystore.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

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

    @RequestMapping(method = POST)
    public Produto save(@RequestBody Map<String, String> body) {
        return new Produto();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Produto get(@PathVariable String id) {
        return new Produto();
    }

    @RequestMapping(value = "/novidades/{quantidadeProdutos}", method = GET)
    public List<Produto> novidades(@PathVariable int quantidadeProdutos) {
        return produtoService.novidades(quantidadeProdutos);
    }

    @RequestMapping(value = "/maisVendidos/{quantidadeProdutos}", method = GET)
    public List<Produto> maisVendidos(@PathVariable int quantidadeProdutos) {
        return produtoService.maisVendidos(quantidadeProdutos);
    }

    @RequestMapping(value = "/categoria/{categoria}", method = GET)
    public List<Produto> porCategoria(@PathVariable String categoria, @RequestParam int pagina, @RequestParam int size) {
        Optional<Categoria> categoria_obj = categoriaService.get(categoria);
        if (categoria_obj.isPresent()) {
            return produtoService.porCategoria(categoria_obj.get().getId(), pagina, size);
        } else {
            return new ArrayList<>();
        }

    }

}
