package mystore.controllers;

import com.mchange.rmi.NotAuthorizedException;
import mystore.models.Cliente;
import mystore.models.Encomenda;
import mystore.models.enums.RoleUtilizador;
import mystore.services.ClienteService;
import mystore.services.EncomendaService;
import mystore.services.UtilizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;
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
    public List<Encomenda> getEncomendas(@RequestAttribute RoleUtilizador role) throws AccessDeniedException {
        if (role != FUNCIONARIO) {
            throw new AccessDeniedException("Sem autorização");
        }
        return new ArrayList<>();
    }

    @RequestMapping(path = "/cliente", method = GET)
    public List<Encomenda> getEncomendas(@RequestAttribute long uid) {
        Optional<Cliente> optionalCliente = clienteService.get(uid);
        if (optionalCliente.isPresent()) {
            return new ArrayList<>(optionalCliente.get().getEncomendas());
        } else {
            throw new EntityExistsException("Cliente não existe");
        }
    }
}
