package mystore.services;

public interface BasicService<T> {
    void save(T objToSave);
}
