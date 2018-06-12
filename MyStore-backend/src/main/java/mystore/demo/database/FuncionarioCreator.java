package mystore.demo.database;

import com.github.javafaker.*;
import com.github.javafaker.Number;
import mystore.models.Funcionario;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class FuncionarioCreator {


    protected Faker faker;
    protected Internet internet;
    protected Name name;
    protected PhoneNumber phoneNumber;
    protected Address address;
    protected IdNumber idNumber;
    protected Number number;

    protected Set<Funcionario> funcionariosCriados;

    public FuncionarioCreator(){
        this.funcionariosCriados = new HashSet<>();
        this.faker = new Faker(new Locale("pt"));
        this.internet = faker.internet();
        this.name = faker.name();
        this.phoneNumber = faker.phoneNumber();
        this.address = faker.address();
        this.idNumber = faker.idNumber();
        this.number = faker.number();
    }

    public void addFuncionarios(int nFuncionarios){
        for(int i=0; i<nFuncionarios;i++){
            Funcionario func = new Funcionario();
            func.setEmail(internet.emailAddress());
            func.setNome(name.nameWithMiddle());
            func.setPassword("123");
            func.setTelemovel(phoneNumber.cellPhone());
            func.setAtivo(true);

            func.setUrlImagem(internet.avatar());

            func.setNumero(number.numberBetween(1000000,9999999));
            funcionariosCriados.add(func);
        }
    }

    public Set<Funcionario> getFuncionariosCriados() {
        return funcionariosCriados;
    }
}
