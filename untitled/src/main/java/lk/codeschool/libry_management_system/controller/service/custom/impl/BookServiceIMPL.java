package lk.codeschool.libry_management_system.controller.service.custom.impl;

import lk.codeschool.libry_management_system.controller.dto.custom.BookDTO;
import lk.codeschool.libry_management_system.controller.entity.custom.Book;
import lk.codeschool.libry_management_system.controller.entity.custom.BookAuthor;
import lk.codeschool.libry_management_system.controller.entity.custom.SubCategory;
import lk.codeschool.libry_management_system.controller.repo.custom.BookAuthorRepo;
import lk.codeschool.libry_management_system.controller.repo.custom.BookRepo;
import lk.codeschool.libry_management_system.controller.repo.custom.impl.BookRepoIMPL;
import lk.codeschool.libry_management_system.controller.service.custom.BookService;
import lk.codeschool.libry_management_system.controller.util.DBConnection;
import lk.codeschool.libry_management_system.controller.util.exception.custom.BookException;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookServiceIMPL implements BookService {
    private final BookRepo repo;
    private final ModelMapper mapper;
    private final BookAuthorRepo bookAuthorRepo;

    public BookServiceIMPL(ModelMapper mapper,BookRepo bookRepo,BookAuthorRepo bookAuthorRepo) {
        this.bookAuthorRepo = bookAuthorRepo;
        this.repo = bookRepo;
        this.mapper = mapper;
    }

    @Override
    public boolean add(BookDTO bookDTO) throws BookException {
        Book book = this.covertDTOtoEntity(bookDTO);
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            Book save = repo.save(book);
            List<BookAuthor> authorList = bookDTO.getAuthorIds().stream().map(e -> new BookAuthor(save.getId(), e)).toList();
            List<SubCategory> subCategoryList = bookDTO.getSubCategoryIds().stream().map(e -> new SubCategory(save.getId(), e)).toList();
            bookAuthorRepo.saveList(authorList);

            return repo.save(book) == null;
        } catch (SQLException | ClassNotFoundException e) {
            if (((SQLException) e).getErrorCode() == 1062) {
                throw new BookException("ID Already Exists - Cannot Save.");
            } else if (((SQLException) e).getErrorCode() == 1406) {
                String message = ((SQLException) e).getMessage();
                String[] s = message.split("'");
                throw new BookException("Data is To Large For "+s[1]);
            }
            throw new BookException("Error Occured Contact Develloper",e);
        }
    }

    @Override
    public boolean delete(Integer integer) throws BookException {
        try {
            return repo.delete(integer);
        } catch (SQLException | ClassNotFoundException e) {
            throw new BookException("No implyment fully yet");
        }
    }

    @Override
    public boolean update(BookDTO bookDTO) throws BookException {
        Book book = this.covertDTOtoEntity(bookDTO);
        try {
            return repo.update(book);
        } catch (SQLException | ClassNotFoundException e) {
            if (((SQLException) e).getErrorCode() == 1406) {
                String message = ((SQLException) e).getMessage();
                String[] s = message.split("'");
                throw new BookException("Data is To Large For "+s[1]);
            }
            throw new BookException("Error Occured Contact Develloper",e);
        }
    }

    @Override
    public Optional<BookDTO> search(Integer integer)throws BookException {
        try {
            Optional<Book> search = repo.search(integer);
            if(search.isPresent()){
                Book book = search.get();
                BookDTO bookDTO = convertEntitytoDTO(book);
                return Optional.of(bookDTO);
            }
            return Optional.empty();
        } catch (SQLException | ClassNotFoundException e) {
            throw new BookException("Contact Developer");
        }
    }

    @Override
    public List<BookDTO> getAll() throws BookException {
        try {
            List<Book> all = repo.getAll();
            List<BookDTO>list = new ArrayList<>();
            for (Book book : all) {
                BookDTO bookDTO = convertEntitytoDTO(book);
                list.add(bookDTO);
            }
            return list;
        } catch (SQLException | ClassNotFoundException e) {
            throw new BookException("Contact Developper",e);
        }
    }
    private Book covertDTOtoEntity(BookDTO dto){
        return mapper.map(dto,Book.class);
    }
    private BookDTO convertEntitytoDTO(Book book){
        return mapper.map(book,BookDTO.class);
    }
}
