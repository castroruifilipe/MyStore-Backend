package mystore.services;

import mystore.daos.EncomendaDAO;
import mystore.daos.LinhaEncomendaDAO;
import mystore.models.Encomenda;
import mystore.models.LinhaEncomenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class EncomendaServiceImpl implements EncomendaService {

    @Autowired
    private EncomendaDAO encomendaDAO;

    @Autowired
    private LinhaEncomendaDAO linhaEncomendaDAO;

    @Override
    public void save(Encomenda encomenda) {
        for (LinhaEncomenda linha : encomenda.getLinhasEncomenda()) {
            linhaEncomendaDAO.save(linha);
        }
        encomendaDAO.save(encomenda);
    }

    @Override
    public List<Encomenda> list() {
        return encomendaDAO.getAll();
    }

}
