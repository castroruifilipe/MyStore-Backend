package mystore.services;

import mystore.daos.LinhaEncomendaDAO;
import mystore.models.LinhaEncomenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class LinhaEncomendaServiceImpl implements LinhaEncomendaService {

    @Autowired
    protected LinhaEncomendaDAO linhaEncomendaDAO;

    @Override
    public void save(LinhaEncomenda objToSave) {
        linhaEncomendaDAO.save(objToSave);
    }
}
