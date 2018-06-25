package mystore.controllers;

import mystore.models.Carrinho;
import mystore.models.Produto;
import mystore.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private ProdutoService produtoService;


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/addProduto", method = PUT)
    public Carrinho addProduto(@RequestBody Map<String, String> body, HttpSession session) {
        if (!body.containsKey("codigo") || !body.containsKey("quantidade")) {
            throw new IllegalArgumentException("Dados inválidos");
        }
        long codigo = Long.valueOf(body.get("codigo"));
        int quantidade = Integer.valueOf(body.get("quantidade"));

        Produto produto = produtoService.get(codigo).orElseThrow(() -> new EntityNotFoundException("Produto não existe"));

        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho != null) {
            carrinho.addProduto(produto, quantidade);
        } else {
            carrinho = new Carrinho();
            carrinho.addProduto(produto, quantidade);
            session.setAttribute("carrinho", carrinho);
        }
        return carrinho;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/removeProduto", method = PUT)
    public Carrinho removeProduto(@RequestBody Map<String, String> body, HttpSession session) {
        if (!body.containsKey("codigo")) {
            throw new IllegalArgumentException("Dados inválidos");
        }
        long codigo = Long.valueOf(body.get("codigo"));

        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho != null) {
            carrinho.removeProduto(codigo);
            return carrinho;
        }
        throw new EntityNotFoundException("Produto não está no carrinho");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = GET)
    public Carrinho get(HttpSession session) {
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new Carrinho();
            session.setAttribute("carrinho", carrinho);
        }
        return carrinho;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/clear", method = DELETE)
    public Carrinho clear(HttpSession session) {
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho != null) {
            carrinho.clear();
        } else {
            carrinho = new Carrinho();
            session.setAttribute("carrinho", carrinho);
        }
        return carrinho;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/update", method = PUT)
    public Carrinho update(@RequestBody Map<Long, Integer> quantidades, HttpSession session) {
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho != null) {
            carrinho.update(quantidades);
        } else {
            carrinho = new Carrinho();
            session.setAttribute("carrinho", carrinho);
        }
        return carrinho;
    }



}
