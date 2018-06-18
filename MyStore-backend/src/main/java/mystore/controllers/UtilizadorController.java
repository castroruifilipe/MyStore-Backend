package mystore.controllers;

import mystore.models.Cliente;
import mystore.models.Utilizador;
import mystore.models.enums.RoleUtilizador;
import mystore.services.UtilizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/utilizadores")
public class UtilizadorController {

    @Autowired
    private UtilizadorService utilizadorService;


    @RequestMapping(path = "signin", method = POST)
    public Utilizador signin(@RequestBody Map<String, String> credentials, HttpServletResponse response) {
        if (!credentials.containsKey("email") || !credentials.containsKey("password")) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }
        String email = credentials.get("email");
        String password = credentials.get("password");

        return utilizadorService.signin(email, password)
                .map(utilizador -> {
                    try {
                        response.setHeader("Access-Token", utilizadorService.tokenFor(utilizador));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return utilizador;
                })
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Credenciais inválidas"));
    }

    @RequestMapping(path = "/signup", method = POST)
    public void signup(@RequestBody Map<String, String> body) {
        if (!body.containsKey("email") || !body.containsKey("password") ||
                !body.containsKey("nome") || !body.containsKey("role")) {
            throw new IllegalArgumentException("Dados inválidos");
        }

        String email = body.get("email");
        String password = body.get("password");
        String nome = body.get("nome");
        RoleUtilizador role = RoleUtilizador.valueOf(body.get("role"));

        utilizadorService.signup(email, password, nome, role);
    }

    @RequestMapping(path = "/editarDados", method = PUT)
    public Utilizador editarDados(@RequestAttribute long uid, @RequestBody Map<String, String> body) {
        return utilizadorService.atualizarDados(uid, body).orElseThrow(() -> new EntityExistsException("Utilizador não existe"));
    }

    @RequestMapping(path = "/dados", method = GET)
    public Utilizador getDados(@RequestAttribute long uid) {
        return utilizadorService.get(uid).orElseThrow(() -> new EntityExistsException("Utilizador não existe"));
    }

    @RequestMapping(path = "/alterarPassword", method = PUT)
    public void alterarPassword(@RequestAttribute long uid, @RequestBody Map<String, String> body) {

    }
}
