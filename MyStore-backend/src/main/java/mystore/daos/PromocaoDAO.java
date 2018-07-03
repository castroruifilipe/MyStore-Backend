package mystore.daos;


import mystore.models.Promocao;

import java.util.List;

public interface PromocaoDAO extends GenericDAO<Promocao, Long>{

    List<Promocao> listByProduto(long codigo);

    List<Promocao> listByCategoria(String descricao);

    List<Promocao> listAtuais();

}
