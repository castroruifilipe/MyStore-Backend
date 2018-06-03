package mystore.daos;

import mystore.models.Promocao;
import org.springframework.stereotype.Repository;

@Repository("promocaoDAO")
public class PromocaoDAOImpl extends GenericDAOImpl<Promocao, Long> implements PromocaoDAO {

}
