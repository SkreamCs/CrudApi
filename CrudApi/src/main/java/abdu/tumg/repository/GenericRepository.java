package abdu.tumg.repository;

import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<T, ID> {

    T getByID(ID id);

    void update(T t);

    void delete(ID id);

    void save(T t);

    List<T> getAll();
}

