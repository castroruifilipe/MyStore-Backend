package mystore.controllers;

import mystore.models.Categoria;
import mystore.models.Produto;
import mystore.models.Promocao;
import mystore.models.enums.RoleUtilizador;
import mystore.services.CategoriaService;
import mystore.services.ProdutoService;
import mystore.services.PromocaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.format.DateTimeFormatter.*;
import static mystore.models.enums.RoleUtilizador.FUNCIONARIO;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/promocoes")
public class PromocaoController {

    @Autowired
    private PromocaoService promocaoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProdutoService produtoService;


    @RequestMapping(method = GET)
    public List<Promocao> list(@RequestAttribute RoleUtilizador role) {
        if (role != FUNCIONARIO) {
            throw new AuthorizationServiceException("Sem autorização");
        }
        return promocaoService.list();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Promocao get(@PathVariable long id, @RequestAttribute RoleUtilizador role) {
        if (role != FUNCIONARIO) {
            throw new AuthorizationServiceException("Sem autorização");
        }
        return promocaoService.get(id).orElseThrow(() -> new EntityNotFoundException("Promocao não existe"));
    }

    @RequestMapping(value = "/criar", method = POST)
    public Promocao criar(@RequestBody Map<String, Object> body, @RequestAttribute RoleUtilizador role) {
        if (role != FUNCIONARIO) {
            throw new AuthorizationServiceException("Sem autorização");
        }
        if (!body.containsKey("descricao") || !body.containsKey("desconto") || !body.containsKey("dataInicio")
                || !body.containsKey("dataFim")) {
            throw new IllegalArgumentException("Dados inválidos");
        }
        String descricao = (String) body.get("descricao");
        double desconto = Double.valueOf((String) body.get("desconto")) / 100;


        LocalDate dataInicio = LocalDate.parse((String) body.get("dataInicio"), ISO_LOCAL_DATE);
        LocalDate dataFim = LocalDate.parse((String) body.get("dataFim"), ISO_LOCAL_DATE);


        System.out.println("\n\n" + dataInicio.toString());
        System.out.println("\n\n" + dataInicio.toString());

        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Datas inválidas");
        }

        if (body.containsKey("categoria")) {
            String descricaoCategoria = (String) body.get("categoria");
            Optional<Categoria> optionalCategoria = categoriaService.get(descricaoCategoria);
            if (optionalCategoria.isPresent()) {
                return promocaoService.criar(descricao, desconto, dataInicio, dataFim, optionalCategoria.get());
            }
            throw new EntityNotFoundException("Categoria não existe");
        } else if (body.containsKey("produtos")) {
            List<Long> codigos = (List<Long>) body.get("produtos");
            Set<Produto> produtos = new HashSet<>();
            codigos.forEach(codigo -> produtos.add(
                    produtoService.get(codigo).orElseThrow(() -> new EntityNotFoundException("Produto não existe")))
            );
            return promocaoService.criar(descricao, desconto, dataInicio, dataFim, produtos);
        }
        throw new IllegalArgumentException("Dados inválidos");
    }
}
