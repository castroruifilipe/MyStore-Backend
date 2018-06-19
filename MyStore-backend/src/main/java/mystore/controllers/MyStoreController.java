package mystore.controllers;


import mystore.models.MyStore;
import mystore.services.MyStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/mystore")
public class  MyStoreController {

    @Autowired
    private MyStoreService myStoreService;


    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public Map<String, String> informacoes() {
        return myStoreService.getInformacoes();
    }
}
