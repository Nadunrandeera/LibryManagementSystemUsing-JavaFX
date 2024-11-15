package lk.codeschool.libry_management_system.controller.repo.custom.impl;

import lk.codeschool.libry_management_system.controller.entity.custom.Author;
import lk.codeschool.libry_management_system.controller.repo.custom.AuthorRepo;
import lk.codeschool.libry_management_system.controller.util.CrudUtill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorRepoIMPL implements AuthorRepo {
    @Override
    public Author save(Author author) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Author(id,name,contact)"+" VALUES(?,?,?)";
        CrudUtill.execute(sql, author.getId(), author.getName(), author.getContact());
        return author;
    }

    @Override
    public boolean update(Author author) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Author set id = ?,name = ?,contact = ?";
        boolean execute = CrudUtill.execute(sql, author.getId(), author.getName(), author.getContact());
        return execute;
    }

    @Override
    public Optional<Author> search(Integer integer) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Author WHERE id = ?";
        ResultSet rs = CrudUtill.execute(sql, integer);
        if(rs.next()){
            Author author =new Author();
            author.setId(rs.getInt(1));
            author.setName(rs.getString(2));
            author.setContact(rs.getString(3));

            return Optional.of(author);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer integer) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Author WHERE id = ?";
        boolean execute = CrudUtill.execute(sql, integer);
        return execute;
    }

    @Override
    public List<Author> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM author";
        ResultSet rs = CrudUtill.execute(sql);
        List<Author>list = new ArrayList<>();
        while (rs.next()){
            Author author =new Author();
            author.setId(rs.getInt(1));
            author.setName(rs.getString(2));
            author.setContact(rs.getString(3));
            list.add(author);
        }
        return list;
    }
}
