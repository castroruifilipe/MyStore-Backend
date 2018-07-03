package mystore;

import mystore.services.UtilizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;

import static mystore.models.enums.RoleUtilizador.FUNCIONARIO;

@Configuration
@PropertySource({"classpath:mystore.properties"})
@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private UtilizadorService utilizadorService;

    @Value("${mystore.admin.email}")
    private String email;

    @Value("${mystore.admin.password}")
    private String password;

    @Value("${mystore.admin.nome}")
    private String nome;


    @Override
    public void run(ApplicationArguments args) {
        try {
            utilizadorService.signup(email, password, nome, FUNCIONARIO, 0);
            System.out.println("\t\tAdmin registado");
        } catch (EntityExistsException e) {
            System.out.println("\t\tAdmin já registado");
        }
    }
}
