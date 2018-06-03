package mystore.services;

import mystore.models.Utilizador;
import mystore.models.enums.RoleUtilizador;

import java.util.Optional;

public interface UtilizadorService {

    void signup(String email, String password, String nome, RoleUtilizador role);

    Optional<? extends Utilizador> signin(String email, String password);

    String tokenFor(Utilizador utilizador);

    Optional<? extends Utilizador> get(String email);

    Optional<? extends Utilizador> verify(String token);
}
