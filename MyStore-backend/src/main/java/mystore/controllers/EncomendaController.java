package mystore.controllers;

import mystore.models.Cliente;
import mystore.models.Encomenda;
import mystore.models.enums.RoleUtilizador;
import mystore.services.ClienteService;
import mystore.services.EncomendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static mystore.models.enums.RoleUtilizador.FUNCIONARIO;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/encomendas")
public class EncomendaController {

    @Autowired
    private EncomendaService encomendaService;

    @Autowired
    private ClienteService clienteService;


    @RequestMapping(method = GET)
    public List<Encomenda> list(@RequestAttribute RoleUtilizador role) {
        if (role != FUNCIONARIO) {
            //throw new AuthorizationServiceException("Sem autorização");
        }
        return encomendaService.list();
    }

    @RequestMapping(path = "/cliente", method = GET)
    public List<Encomenda> ofCliente(@RequestAttribute long uid) {
        Optional<Cliente> optionalCliente = clienteService.get(uid);
        if (optionalCliente.isPresent()) {
            return new ArrayList<>(optionalCliente.get().getEncomendas());
        }
        throw new EntityNotFoundException("Cliente não existe");
    }

    @RequestMapping(path = "/{id}", method = GET)
    public Encomenda get(@RequestAttribute long uid, @RequestAttribute RoleUtilizador role, @PathVariable long id) {
        Optional<Encomenda> optionalEncomenda = encomendaService.get(id);
        if (optionalEncomenda.isPresent()) {
            Encomenda encomenda = optionalEncomenda.get();
            if (role == FUNCIONARIO || encomenda.getCliente().getId() == uid) {
                return encomenda;
            }
        }
        throw new EntityNotFoundException("Encomenda não existe");
    }
}
