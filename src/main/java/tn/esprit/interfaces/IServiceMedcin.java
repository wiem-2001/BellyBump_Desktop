package tn.esprit.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IServiceMedcin<T> {
    void add(T t, String etablissementNom);

    void update(T t, String etablissementNom);

    void delete(T t);

    List<T> getAll() throws SQLException;



T getOne(int id);
}
