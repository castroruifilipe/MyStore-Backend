package mystore.controllers;


import mystore.models.Produto;
import mystore.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;


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

    @RequestMapping(value = "/maisVendidos", method = GET)
    public List<Produto> maisVendidos() {
        return null;
    }


}
