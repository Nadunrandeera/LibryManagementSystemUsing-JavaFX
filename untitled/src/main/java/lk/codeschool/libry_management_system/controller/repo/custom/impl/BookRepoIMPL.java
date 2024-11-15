package lk.codeschool.libry_management_system.controller.repo.custom.impl;

import lk.codeschool.libry_management_system.controller.entity.custom.Book;
import lk.codeschool.libry_management_system.controller.repo.custom.BookRepo;
import lk.codeschool.libry_management_system.controller.util.DBConnection;
import lk.codeschool.libry_management_system.controller.util.CrudUtill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepoIMPL implements BookRepo {

    @Override
    public Book save(Book book) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO book(id,name,isbn,price,publisher_id,main_category_id)" +
                " VALUES (?,?,?,?,?,?)";
        //CrudUtill.execute(sql, book.getId(), book.getName(), book.getIsbn(), book.getPrice(), book.getPublisherId(), book.getMainCategory());
        PreparedStatement ps = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ps.setInt(1,book.getId());
        ps.setString(2,book.getName());
        ps.setString(3,book.getIsbn());
        ps.setDouble(4,book.getPrice());
        ps.setInt(5,book.getPublisherId());
        ps.setInt(6,book.getMainCategory());
        int affectedRows = ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if(generatedKeys.next()){
            book.setId(generatedKeys.getInt(1));
        }
        return book;
    }

    @Override
    public boolean update(Book book) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE book set name = ? , isbn = ? , price = ? , publisher_id = ? , " +
                "main_category_id = ? where id = ?";
        boolean execute = CrudUtill.execute(sql, book.getName(), book.getIsbn(), book.getPrice(), book.getPublisherId(), book.getMainCategory(), book.getId());
        return execute;
    }

    @Override
    public Optional<Book> search(Integer integer) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM book WHERE id = ?";
        ResultSet rs = CrudUtill.execute(sql, integer);
        if(rs.next()){
            Book book = new Book();
            book.setId(rs.getInt(1));
            book.setName(rs.getString(2));
            book.setIsbn(rs.getString(3));
            book.setPrice(rs.getDouble(4));
            book.setPublisherId(rs.getInt(5));
            book.setMainCategory(rs.getInt(6));
            return Optional.of(book);

        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer integer) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM book WHERE id = ?";
        boolean execute = CrudUtill.execute(sql, integer);
        return execute;
    }

    @Override
    public List<Book> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM book";
        ResultSet rs = CrudUtill.execute(sql);
        List<Book> books = new ArrayList<>();
        while (rs.next()){
            Book book = new Book();
            book.setId(rs.getInt(1));
            book.setName(rs.getString(2));
            book.setIsbn(rs.getString(3));
            book.setPrice(rs.getDouble(4));
            book.setPublisherId(rs.getInt(5));
            book.setMainCategory(rs.getInt(6));
            books.add(book);
        }
        return books;
    }
}
