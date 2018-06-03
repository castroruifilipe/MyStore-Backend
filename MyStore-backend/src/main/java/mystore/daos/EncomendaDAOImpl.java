package mystore.daos;

import mystore.models.Encomenda;
import org.springframework.stereotype.Repository;

@Repository("encomendaDAO")
public class EncomendaDAOImpl extends GenericDAOImpl<Encomenda, Long> implements EncomendaDAO {

}
