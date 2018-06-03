package mystore.services;

import mystore.models.Loja;

import java.util.Optional;

public interface LojaService extends BasicService<Loja>{

    Optional<Loja> getLoja();
}
