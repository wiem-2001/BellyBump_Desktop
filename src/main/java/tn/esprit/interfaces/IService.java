package tn.esprit.interfaces;

import java.util.List;

public interface IService <T> {
    //CRUD

    //ADD
    void add(T t);
    //UPDATE
    void update(T t);
    //DELETE
    void delete(T t);
    //SHOW ALL
    List<T> getAll();
    //SHOW ONE
    T getOne(int id);

}
