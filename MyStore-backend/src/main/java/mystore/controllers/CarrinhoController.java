package mystore.controllers;

import mystore.models.LinhaCarrinho;
import mystore.models.Produto;
import mystore.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private ProdutoService produtoService;


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "addProduto", method = PUT)
    public void addProduto(@RequestParam long codigo, @RequestParam int quantidade, HttpSession session) {
        Produto produto = produtoService.get(codigo).orElseThrow(() -> new EntityExistsException("Produto não existe"));
        List<LinhaCarrinho> carrinho = new ArrayList<>();
        if (session.getAttribute("carrinho") != null) {
            carrinho = (List<LinhaCarrinho>) session.getAttribute("carrinho");
        }
        carrinho.add(new LinhaCarrinho(produto, quantidade));
        carrinho.forEach(System.out::println);
        session.setAttribute("carrinho", carrinho);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "removeProduto", method = PUT)
    public void removeProduto(@RequestParam long codigo, HttpSession session) {
        if (session.getAttribute("carrinho") != null) {
            List<LinhaCarrinho> carrinho = (List<LinhaCarrinho>) session.getAttribute("carrinho");
            for (LinhaCarrinho linha : carrinho) {
                if (linha.getProduto().getCodigo() == codigo) {
                    carrinho.remove(linha);
                    return;
                }
            }
        }
        throw new EntityExistsException("Produto não está no carrinho");
    }
}
