package mystore.controllers;

import com.mchange.rmi.NotAuthorizedException;
import mystore.models.Cliente;
import mystore.models.Encomenda;
import mystore.models.enums.RoleUtilizador;
import mystore.services.ClienteService;
import mystore.services.EncomendaService;
import mystore.services.UtilizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
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
