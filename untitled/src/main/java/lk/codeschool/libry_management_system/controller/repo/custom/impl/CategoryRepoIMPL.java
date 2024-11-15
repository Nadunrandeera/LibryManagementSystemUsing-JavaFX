package lk.codeschool.libry_management_system.controller.repo.custom.impl;

import lk.codeschool.libry_management_system.controller.entity.custom.Category;
import lk.codeschool.libry_management_system.controller.repo.custom.CategoryRepo;
import lk.codeschool.libry_management_system.controller.util.CrudUtill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepoIMPL implements CategoryRepo {

    @Override
    public Category save(Category category) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Category(id,name)"+" VALUES (?,?)";
        CrudUtill.execute(sql, category.getId(), category.getName());
        return category;
    }

    @Override
    public boolean update(Category category) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Category SET name=? WHERE id=?";
        return CrudUtill.execute(sql,category.getName(),category.getId());
    }

    @Override
    public Optional<Category> search(Integer integer) throws SQLException, ClassNotFoundException {
        String sql  = "SELECT * FROM Category WHERE id = ?";
        ResultSet rs = CrudUtill.execute(sql, integer);
        if(rs.next()){
            Category category = new Category();
            category.setId(rs.getInt(1));
            category.setName(rs.getString(2));
            return Optional.of(category);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer integer) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Category WHERE id =?";
        boolean execute = CrudUtill.execute(sql, integer);
        return execute;
    }

    @Override
    public List<Category> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Category";
        ResultSet rs = CrudUtill.execute(sql);
        List<Category>list = new ArrayList<>();
        while (rs.next()){
            Category category = new Category();
            category.setId(rs.getInt(1));
            category.setName(rs.getString(2));
            list.add(category);
        }
        return list;
    }
}
