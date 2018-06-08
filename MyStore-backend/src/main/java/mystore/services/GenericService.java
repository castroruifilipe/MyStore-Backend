package mystore.services;

import java.util.List;

public interface GenericService<T> {

    void save(T objToSave);

}
