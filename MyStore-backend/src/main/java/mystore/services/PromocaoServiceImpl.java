package mystore.services;

import mystore.daos.PromocaoDAO;
import mystore.models.Promocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PromocaoServiceImpl implements PromocaoService {

    @Autowired
    protected PromocaoDAO promocaoDAO;

    @Override
    public void save(Promocao objToSave) {
        promocaoDAO.save(objToSave);
    }
}
