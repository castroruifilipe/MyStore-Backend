package mystore.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import mystore.daos.ClienteDAO;
import mystore.daos.FuncionarioDAO;
import mystore.models.Cliente;
import mystore.models.Funcionario;
import mystore.models.Utilizador;
import mystore.models.enums.RoleUtilizador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;
import static java.util.Collections.*;
import static mystore.models.enums.RoleUtilizador.CLIENTE;
import static mystore.models.enums.RoleUtilizador.FUNCIONARIO;

@Service
@Transactional
public class UtilizadorServiceImpl implements UtilizadorService {

    private static final String ISSUER = "in.sdqali.jwt";
    private static final String SECRET = "SECRET";

    @Autowired
    protected ClienteDAO clienteDAO;

    @Autowired
    protected FuncionarioDAO funcionarioDAO;

    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void signup(String email, String password, String nome, RoleUtilizador role) {
        if (clienteDAO.contains("email", email) || funcionarioDAO.contains("email", email)) {
            throw new EntityExistsException("Utilizador já existe");
        }
        switch (role) {
            case CLIENTE:
                Cliente cliente = new Cliente();
                cliente.setEmail(email);
                cliente.setPassword(bCryptPasswordEncoder.encode(password));
                cliente.setNome(nome);
                clienteDAO.save(cliente);
                break;
            case FUNCIONARIO:
                Funcionario funcionario = new Funcionario();
                funcionario.setEmail(email);
                funcionario.setPassword(bCryptPasswordEncoder.encode(password));
                funcionario.setNome(nome);
                funcionarioDAO.save(funcionario);
        }
    }

    @Override
    public String tokenFor(Utilizador utilizador) {
        Date expiration = Date.from(LocalDateTime.now().plusHours(24).toInstant(UTC));
        RoleUtilizador role = (utilizador instanceof Cliente ? CLIENTE : FUNCIONARIO);
        return Jwts.builder()
                .setSubject(utilizador.getEmail())
                .claim("Role", role.toString())
                .setExpiration(expiration)
                .setIssuer(ISSUER)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    @Override
    public Optional<? extends Utilizador> get(String email) {
        Optional<? extends Utilizador> utilizador = Optional.empty();
        Optional<Cliente> cliente = clienteDAO.findUnique("email", email);
        if (cliente.isPresent()) {
            utilizador = cliente;
        } else {
            Optional<Funcionario> funcionario = funcionarioDAO.findUnique("email", email);
            if (funcionario.isPresent()) {
                utilizador = funcionario;
            }
        }
        return utilizador;
    }

    @Override
    public Optional<? extends Utilizador> verify(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        return get(claims.getBody().getSubject().toString());
    }

    @Override
    public Optional<? extends Utilizador> signin(String email, String password) {
        Optional<? extends Utilizador> utilizador = get(email);
        if (utilizador.isPresent()) {
            if (bCryptPasswordEncoder.matches(password, utilizador.get().getPassword())) {
                return utilizador;
            }
        }
        return Optional.empty();
    }
}

