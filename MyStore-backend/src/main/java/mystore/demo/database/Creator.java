package mystore.demo.database;

import com.github.javafaker.*;
import com.github.javafaker.Number;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Creator<T> {

    protected Set<T> items;
    protected Faker faker;
    protected Commerce commerce;
    protected Internet internet;
    protected Name name;
    protected PhoneNumber phoneNumber;
    protected Address address;
    protected IdNumber idNumber;
    protected Number number;
    protected Lorem lorem;

    public Creator(){
        this.items = new HashSet<>();
        this.faker = new Faker(new Locale("en"));
        this.commerce = faker.commerce();
        this.internet = faker.internet();
        this.name = faker.name();
        this.phoneNumber = faker.phoneNumber();
        this.address = faker.address();
        this.idNumber = faker.idNumber();
        this.number = faker.number();
        this.lorem = faker.lorem();
    }

    public Set<T> getItems(){
        return items;
    }

    public void addItem(T item){
        items.add(item);
    }

    public int getNumItems(){
        return items.size();
    }


}
