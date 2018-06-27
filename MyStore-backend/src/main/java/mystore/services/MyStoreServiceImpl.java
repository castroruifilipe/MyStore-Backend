package mystore.services;

import mystore.models.MyStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.TreeMap;

@Service
@Transactional
public class MyStoreServiceImpl implements MyStoreService {

    @Autowired
    private MyStore myStore;


    @Override
    public Map<String, String> getInformacoes() {
        Map<String, String> informacoes = new TreeMap<>();
        informacoes.put("nome", myStore.getNome());
        informacoes.put("localizacao", myStore.getLocalizao());
        informacoes.put("contacto", myStore.getContacto());
        informacoes.put("email", myStore.getEmail());
        return informacoes;
    }
}
