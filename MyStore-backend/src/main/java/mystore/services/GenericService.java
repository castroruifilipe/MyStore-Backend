package mystore.services;

public interface GenericService<T> {

    void save(T objToSave);

}
