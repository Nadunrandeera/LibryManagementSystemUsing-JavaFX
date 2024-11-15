package lk.codeschool.libry_management_system.controller.repo.custom;

import lk.codeschool.libry_management_system.controller.entity.custom.BookAuthor;
import lk.codeschool.libry_management_system.controller.repo.CrudRepository;

import java.sql.SQLException;
import java.util.List;

public interface BookAuthorRepo extends CrudRepository<BookAuthor, Integer> {
    public boolean saveList (List<BookAuthor> list) throws SQLException, ClassNotFoundException;
}
