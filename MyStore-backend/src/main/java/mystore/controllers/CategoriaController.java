package mystore.controllers;

import mystore.models.Categoria;
import mystore.models.enums.RoleUtilizador;
import mystore.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static mystore.models.enums.RoleUtilizador.FUNCIONARIO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;


    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public List<Categoria> list() {
        List<Categoria> categorias = categoriaService.list();
        return categorias;
    }

    @RequestMapping(method = POST, path = "/criar")
    public void criar(@RequestBody Map<String, String> body, @RequestAttribute RoleUtilizador role) {
        if (role != FUNCIONARIO) {
            throw new AuthorizationServiceException("Sem autorização");
        }
        if (!body.containsKey("descricao")) {
            throw new IllegalArgumentException("Dados inválidos");
        }
        Categoria categoria = new Categoria();
        categoria.setDescricao(body.get("descricao"));
        categoriaService.save(categoria);
    }

    @RequestMapping(method = DELETE, path = "/apagar")
    public void delete(@RequestParam String descricao, @RequestAttribute RoleUtilizador role) {
        if (role != FUNCIONARIO) {
            throw new AuthorizationServiceException("Sem autorização");
        }
        Optional<Categoria> optionalCategoria = categoriaService.get(descricao);
        if (optionalCategoria.isPresent()) {
            categoriaService.delete(optionalCategoria.get());
        } else {
            throw new EntityNotFoundException("Categoria não existe");
        }
    }

}
