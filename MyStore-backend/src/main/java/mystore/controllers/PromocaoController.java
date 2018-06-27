package mystore.controllers;

import mystore.models.Promocao;
import mystore.services.PromocaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/promocoes")
public class PromocaoController {

    @Autowired
    private PromocaoService promocaoService;


    @RequestMapping(method = GET)
    public List<Promocao> list() {
        return promocaoService.list();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Promocao get(@PathVariable long id) {
        return promocaoService.get(id).orElseThrow(() -> new EntityNotFoundException("Promocao não existe"));
    }
}
