package mystore.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.Serializable;

@Configuration
@PropertySource({"classpath:mystore.properties"})
public class MyStore implements Serializable {

    @Value("${mystore.nome}")
    private String nome;

    @Value("${mystore.localizacao}")
    private String localizao;

    @Value("${mystore.contacto}")
    private String contacto;

    @Value("${mystore.email}")
    private String email;


    public MyStore() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizao() {
        return localizao;
    }

    public void setLocalizao(String localizao) {
        this.localizao = localizao;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
