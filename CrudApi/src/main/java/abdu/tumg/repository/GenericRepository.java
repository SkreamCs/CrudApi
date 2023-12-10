package abdu.tumg.repository;

import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<T, ID> {
     String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
     String URL_BASE = "jdbc:mysql://localhost:3306/crud_table";
     String USER = "root";
     String PASSWORD = "Zvezda002";
    T getByID(ID id) throws SQLException;

    void update(T t) throws SQLException;

    void delete(ID id) throws SQLException;

    void save(T t) throws SQLException;

    List<T> getAll() throws SQLException;
}

