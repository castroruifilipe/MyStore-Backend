package mystore.services;

import mystore.models.Utilizador;
import mystore.models.enums.RoleUtilizador;

import java.util.Map;
import java.util.Optional;

public interface UtilizadorService {

    void signup(String email, String password, String nome, RoleUtilizador role);

    Optional<? extends Utilizador> signin(String email, String password);

    void atualizarDados(long uid, Map<String, String> dados);

    String tokenFor(Utilizador utilizador);

    Optional<? extends Utilizador> get(String email);

    Optional<? extends Utilizador> get(long uid);

    Optional<? extends Utilizador> verify(String accessToken);
}
