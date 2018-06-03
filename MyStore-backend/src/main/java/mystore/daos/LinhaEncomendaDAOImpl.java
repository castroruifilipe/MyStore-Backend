package mystore.daos;

import mystore.models.LinhaEncomenda;
import org.springframework.stereotype.Repository;

@Repository("linhaEncomendaDAO")
public class LinhaEncomendaDAOImpl extends GenericDAOImpl<LinhaEncomenda, Long> implements LinhaEncomendaDAO {

}
