package mystore.controllers;

import mystore.models.Categoria;
import mystore.models.Produto;
import mystore.models.enums.RoleUtilizador;
import mystore.services.CategoriaService;
import mystore.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static mystore.models.enums.RoleUtilizador.FUNCIONARIO;
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

    @RequestMapping(value = "/{codigo}", method = GET)
    public Produto get(@PathVariable long codigo) {
        return produtoService.get(codigo).orElseThrow(() -> new EntityNotFoundException("Produto não existe"));
    }

    @RequestMapping(value = "criar", method = POST)
    public Produto criar(@RequestBody Map<String, String> body, @RequestAttribute RoleUtilizador role) {
        if (role != FUNCIONARIO) {
            throw new AuthorizationServiceException("Sem autorização");
        }
        if (!body.containsKey("nome") || !body.containsKey("descricao") || !body.containsKey("precoBase")
                || !body.containsKey("stock") || !body.containsKey("categoria")) {
            throw new IllegalArgumentException("Dados inválidos");
        }
        String nome = body.get("nome");
        String descricao = body.get("descricao");
        double precoBase = Double.valueOf(body.get("precoBase"));
        int stock = Integer.valueOf(body.get("stock"));
        Categoria categoria = categoriaService
                .get(body.get("categoria"))
                .orElseThrow(() -> new EntityNotFoundException("Categoria não existe"));
        return produtoService.criar(nome, descricao, precoBase, stock, categoria);
    }

    @RequestMapping(value = "/novidades/{quantidadeProdutos}", method = GET)
    public List<Produto> novidades(@PathVariable int quantidadeProdutos) {
        return produtoService.novidades(quantidadeProdutos);
    }

    @RequestMapping(value = "/maisVendidos/{quantidadeProdutos}", method = GET)
    public List<Produto> maisVendidos(@PathVariable int quantidadeProdutos) {
        return produtoService.maisVendidos(quantidadeProdutos);
    }

    @RequestMapping(value = "/maisVendidosDetail/{quantidadeProdutos}", method = GET)
    public List<Produto> maisVendidosDetail(@PathVariable int quantidadeProdutos) {
        return produtoService.maisVendidosDetail(quantidadeProdutos);
    }

    @RequestMapping(value = "/emPromocao/{quantidadeProdutos}", method = GET)
    public List<Produto> emPromocao(@PathVariable int quantidadeProdutos) {
        return produtoService.emPromocao(quantidadeProdutos);
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
            return produtoService.related(optionalProduto.get(), quantidadeProdutos);
        }
        throw new EntityNotFoundException("Produto não existe");
    }

    @RequestMapping(value = "apagar", method = DELETE)
    public void apagar(@RequestParam long codigo, @RequestAttribute RoleUtilizador role) {
        if (role != FUNCIONARIO) {
            throw new AuthorizationServiceException("Sem autorização");
        }
        produtoService.apagar(codigo);
    }

    @RequestMapping(value = "editar/{codigo}", method = PUT)
    public void editar(@RequestBody Map<String, String> body, @RequestAttribute RoleUtilizador role) {
        if (role != FUNCIONARIO) {
            throw new AuthorizationServiceException("Sem autorização");
        }
        if (!body.containsKey("codigo")) {
            throw new IllegalArgumentException("Dados inválidos");
        }
        long codigo = Long.valueOf(body.get("codigo"));
        produtoService.editar(codigo, body).orElseThrow(() -> new EntityNotFoundException("Produto não existe"));
    }

}
