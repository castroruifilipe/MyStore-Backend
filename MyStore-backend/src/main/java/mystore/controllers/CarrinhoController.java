package mystore.controllers;

import mystore.models.Carrinho;
import mystore.models.Produto;
import mystore.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private ProdutoService produtoService;


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/addProduto", method = PUT)
    public void addProduto(@RequestParam long codigo, @RequestParam int quantidade, HttpSession session) {
        Produto produto = produtoService.get(codigo).orElseThrow(() -> new EntityNotFoundException("Produto não existe"));
        Carrinho carrinho = new Carrinho();
        if (session.getAttribute("carrinho") != null) {
            carrinho = (Carrinho) session.getAttribute("carrinho");
        }
        carrinho.addProduto(produto, quantidade);
        session.setAttribute("carrinho", carrinho);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/removeProduto", method = PUT)
    public void removeProduto(@RequestParam long codigo, HttpSession session) {
        if (session.getAttribute("carrinho") != null) {
            Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
            carrinho.removeProduto(codigo);
        }
        throw new EntityNotFoundException("Produto não está no carrinho");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = GET)
    public Carrinho get(HttpSession session) {
        if (session.getAttribute("carrinho") != null) {
            Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
            return carrinho;
        } else {
            return new Carrinho();
        }
    }
}
