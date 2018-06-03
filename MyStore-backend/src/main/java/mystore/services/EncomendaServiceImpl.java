package mystore.services;

import mystore.daos.EncomendaDAO;
import mystore.models.Encomenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class EncomendaServiceImpl implements EncomendaService {

    @Autowired
    protected EncomendaDAO encomendaDAO;

    @Override
    public void save(Encomenda objToSave) {
        encomendaDAO.save(objToSave);
    }
}
