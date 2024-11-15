package lk.codeschool.libry_management_system.controller.repo;

import lk.codeschool.libry_management_system.controller.entity.SuperEntity;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends SuperEntity,ID extends Serializable> {
    T save(T t) throws SQLException , ClassNotFoundException;
    boolean update(T t) throws SQLException , ClassNotFoundException;
    Optional<T> search(ID id) throws SQLException , ClassNotFoundException;;
    boolean delete(ID id) throws SQLException , ClassNotFoundException;
    List<T> getAll() throws SQLException , ClassNotFoundException;
}
