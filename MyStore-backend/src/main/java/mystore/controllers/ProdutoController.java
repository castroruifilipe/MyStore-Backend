package mystore.controllers;


import mystore.models.Produto;
import mystore.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

}
