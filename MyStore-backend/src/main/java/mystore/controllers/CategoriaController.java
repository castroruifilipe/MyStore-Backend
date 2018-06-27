package mystore.controllers;

import mystore.models.Categoria;
import mystore.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

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

    @RequestMapping(method = DELETE)
    public void delete(@RequestParam String descricao) {
        Optional<Categoria> optionalCategoria = categoriaService.get(descricao);
        if (optionalCategoria.isPresent()) {
            categoriaService.delete(optionalCategoria.get());
        } else {
            throw new EntityNotFoundException("Categoria não existe");
        }
    }

}
