package mystore.controllers;

import mystore.models.Carrinho;
import mystore.models.Cliente;
import mystore.models.Encomenda;
import mystore.models.Morada;
import mystore.models.enums.MetodoPagamento;
import mystore.models.enums.RoleUtilizador;
import mystore.services.ClienteService;
import mystore.services.EncomendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static mystore.models.enums.RoleUtilizador.FUNCIONARIO;
import static org.springframework.web.bind.annotation.RequestMethod.*;

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
            throw new AuthorizationServiceException("Sem autorização");
        }
        return encomendaService.list();
    }

    @RequestMapping(path = "/ultimas/{quantidadeEncomendas}")
    public List<Encomenda> ultimas(@RequestAttribute RoleUtilizador role, @PathVariable int quantidadeEncomendas) {
        return encomendaService.ultimas(quantidadeEncomendas);
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

    @SuppressWarnings("unchecked")
    @RequestMapping(path = "/checkout", method = POST)
    public Encomenda checkout(@RequestBody Map<String, Object> body, @RequestAttribute long uid, HttpSession session) {
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
        if (carrinho == null) {
            throw new EntityNotFoundException("Carrinho não existe");
        }
        if (carrinho.getLinhasCarrinho().isEmpty()) {
            throw new EntityNotFoundException("Carrinho sem produtos");
        }
        if (!body.containsKey("moradaEntrega") || !body.containsKey("metodoPagamento")) {
            throw new IllegalArgumentException("Dados inválidos");
        }
        MetodoPagamento metodoPagamento = MetodoPagamento.valueOf((String) body.get("metodoPagamento"));
        Map<String, String> morada = (Map<String, String>) body.get("moradaEntrega");
        if (!morada.containsKey("rua") || !morada.containsKey("localidade") || !morada.containsKey("codigoPostal")) {
            throw new IllegalArgumentException("Dados inválidos");
        }
        Morada moradaEntrega = new Morada();
        moradaEntrega.setRua(morada.get("rua"));
        moradaEntrega.setLocalidade(morada.get("localidade"));
        moradaEntrega.setCodigoPostal(morada.get("codigoPostal"));

        Optional<Cliente> optionalCliente = clienteService.get(uid);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            Encomenda encomenda = encomendaService.checkout(cliente, moradaEntrega, carrinho, metodoPagamento);
            carrinho.clear();
            return encomenda;
        }
        throw new EntityNotFoundException("Cliente não existe");
    }

    @RequestMapping(path = "/pagar", method = PUT)
    public Encomenda pagar(@RequestBody Map<String, Long> body, @RequestAttribute RoleUtilizador role) {
        if (role != FUNCIONARIO) {
            throw new AuthorizationServiceException("Sem autorização");
        }
        if (!body.containsKey("encomenda")) {
            throw new IllegalArgumentException("Dados inválidos");
        }
        long id = body.get("encomenda");
        return encomendaService
                .pagar(id)
                .orElseThrow(() -> new IllegalArgumentException("Não foi possível pagar"));
    }

}
