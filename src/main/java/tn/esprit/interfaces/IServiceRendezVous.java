package tn.esprit.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IServiceRendezVous <T> {
    void add(T t, String medcinNom);

    void update(T t);

    void delete(T t);

    List<T> getAll() throws SQLException;



    T getOne(int id);
}