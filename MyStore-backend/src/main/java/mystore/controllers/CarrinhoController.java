package mystore.controllers;

import mystore.models.Carrinho;
import mystore.models.Produto;
import mystore.models.Promocao;
import mystore.services.ProdutoService;
import mystore.services.PromocaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.AbstractMap.SimpleEntry;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PromocaoService promocaoService;


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/addProduto", method = PUT)
    public Carrinho addProduto(@RequestBody Map<String, String> body, HttpSession session) {
        if (!body.containsKey("codigo") || !body.containsKey("quantidade")) {
            throw new IllegalArgumentException("Dados inválidos");
        }
        long codigo = Long.valueOf(body.get("codigo"));
        int quantidade = Integer.valueOf(body.get("quantidade"));

        Produto produto = produtoService.get(codigo).orElseThrow(() -> new EntityNotFoundException("Produto não existe"));

        Carrinho carrinho = new Carrinho();
        if (session.getAttribute("carrinho") != null) {
            carrinho = (Carrinho) session.getAttribute("carrinho");
        }
        carrinho.addProduto(produto, quantidade);
        session.removeAttribute("carrinho");
        session.setAttribute("carrinho", carrinho);
        return carrinho;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/removeProduto", method = PUT)
    public Carrinho removeProduto(@RequestBody Map<String, String> body, HttpSession session) {
        if (!body.containsKey("codigo")) {
            throw new IllegalArgumentException("Dados inválidos");
        }
        long codigo = Long.valueOf(body.get("codigo"));

        if (session.getAttribute("carrinho") != null) {
            Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
            carrinho.removeProduto(codigo);
            session.removeAttribute("carrinho");
            session.setAttribute("carrinho", carrinho);
            return carrinho;
        }
        throw new EntityNotFoundException("Produto não está no carrinho");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = GET)
    public Carrinho get(HttpSession session) {
        Carrinho carrinho = new Carrinho();
        if (session.getAttribute("carrinho") != null) {
            carrinho = (Carrinho) session.getAttribute("carrinho");
        }
        session.removeAttribute("carrinho");
        session.setAttribute("carrinho", carrinho);
        return carrinho;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/clear", method = PUT)
    public Carrinho clear(HttpSession session) {
        Carrinho carrinho = new Carrinho();
        if (session.getAttribute("carrinho") != null) {
            carrinho = (Carrinho) session.getAttribute("carrinho");
            carrinho.clear();
        }
        session.removeAttribute("carrinho");
        session.setAttribute("carrinho", carrinho);
        return carrinho;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/update", method = PUT)
    public Carrinho update(@RequestBody Map<Long, Integer> quantidades, HttpSession session) {
        Carrinho carrinho = new Carrinho();
        if (session.getAttribute("carrinho") != null) {
            carrinho = (Carrinho) session.getAttribute("carrinho");
            carrinho.update(quantidades);
        }
        session.removeAttribute("carrinho");
        session.setAttribute("carrinho", carrinho);
        return carrinho;
    }


}
