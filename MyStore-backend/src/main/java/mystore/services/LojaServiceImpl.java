package mystore.services;

import mystore.daos.LojaDAO;
import mystore.models.Loja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LojaServiceImpl implements LojaService {

    @Autowired
    protected LojaDAO lojaDAO;


    @Override
    public void save(Loja objToSave) {
        lojaDAO.save(objToSave);
    }

    @Override
    public Loja getLoja() {
        List<Loja> lojas = lojaDAO.getAll();
        return lojas.size() > 0 ? lojas.get(0) : null;
    }


}
