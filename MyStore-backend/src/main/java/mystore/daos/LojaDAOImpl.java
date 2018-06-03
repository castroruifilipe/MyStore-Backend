package mystore.daos;

import mystore.models.Loja;
import org.springframework.stereotype.Repository;

@Repository("lojaDAO")
public class LojaDAOImpl extends GenericDAOImpl<Loja, Long> implements LojaDAO {

}
