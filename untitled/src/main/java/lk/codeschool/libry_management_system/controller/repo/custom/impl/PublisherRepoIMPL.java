package lk.codeschool.libry_management_system.controller.repo.custom.impl;

import lk.codeschool.libry_management_system.controller.entity.custom.Publisher;
import lk.codeschool.libry_management_system.controller.repo.custom.PublisherRepo;
import lk.codeschool.libry_management_system.controller.util.CrudUtill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublisherRepoIMPL implements PublisherRepo {
    @Override
    public Publisher save(Publisher publisher) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Publisher(name,location,contact) VALUES (?,?,?)";
        CrudUtill.execute(sql,publisher.getName(), publisher.getLocation(), publisher.getContact());
        return publisher;

    }

    @Override
    public boolean update(Publisher publisher) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Publisher SET name=?,location=?,contact=? WHERE id=?";
        boolean execute = CrudUtill.execute(sql, publisher.getId(), publisher.getName(), publisher.getLocation(), publisher.getContact());
        return execute;
    }

    @Override
    public Optional<Publisher> search(Integer integer) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Author WHERE id = ?";
        ResultSet rs = CrudUtill.execute(sql, integer);
        if(rs.next()){
            Publisher publisher = new Publisher();
            publisher.setId(rs.getInt(1));
            publisher.setName(rs.getString(2));
            publisher.setContact(rs.getString(3));
            publisher.setLocation(rs.getString(4));
            return Optional.of(publisher);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer integer) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Publisher WHERE id = ?";
        boolean execute = CrudUtill.execute(sql, integer);
        return execute;
    }

    @Override
    public List<Publisher> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Author";
        ResultSet rs = CrudUtill.execute(sql);
        List<Publisher>list = new ArrayList<>();
        while (rs.next()){
            Publisher publisher = new Publisher();
            publisher.setId(rs.getInt(1));
            publisher.setName(rs.getString(2));
            publisher.setContact(rs.getString(3));
            publisher.setLocation(rs.getString(4));
            list.add(publisher);
        }
        return list;
    }
}
