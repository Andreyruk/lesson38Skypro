package dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T>{

    T get(long id);

    List<T> getAll();

    void save(T t);

    void update(long id, T model);

    void delete(long id);
}
