package lk.codeschool.libry_management_system.controller.service.custom.impl;

import lk.codeschool.libry_management_system.controller.dto.custom.AuthorDTO;
import lk.codeschool.libry_management_system.controller.entity.custom.Author;
import lk.codeschool.libry_management_system.controller.repo.custom.AuthorRepo;
import lk.codeschool.libry_management_system.controller.service.custom.AuthorService;
import lk.codeschool.libry_management_system.controller.util.exception.ServiceException;
import lk.codeschool.libry_management_system.controller.util.exception.custom.AuthorException;
import lk.codeschool.libry_management_system.controller.util.exception.custom.BookException;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorServiceIMPL implements AuthorService {
    //property injection - not a recommended
    private final ModelMapper mapper;
    final AuthorRepo authorRepo;

    //constructor through injection
    public AuthorServiceIMPL(ModelMapper mapper,AuthorRepo authorRepo){
        this.authorRepo = authorRepo;
        this.mapper = mapper;
    }

    @Override
    public boolean add(AuthorDTO authorDTO) throws AuthorException {
        Author author = this.converDTOtoENTITY(authorDTO);
        try {
            return authorRepo.save(author) == null;
        } catch (SQLException | ClassNotFoundException e) {
            if (((SQLException) e).getErrorCode() == 1062) {
                throw new AuthorException("ID Already Exists - Cannot Save.");
            } else if (((SQLException) e).getErrorCode() == 1406) {
                String message = ((SQLException) e).getMessage();
                String[] s = message.split("'");
                throw new AuthorException("Data is To Large For "+s[1]);
            }
            throw new AuthorException("Error Occurred Contact Developer",e);
        }
    }

    @Override
    public boolean delete(Integer integer) throws AuthorException {
        try {
            return authorRepo.delete(integer);
        } catch (SQLException | ClassNotFoundException e) {
            throw new AuthorException("No implement fully yet");
        }
    }

    @Override
    public boolean update(AuthorDTO authorDTO) throws AuthorException {
        Author author = this.converDTOtoENTITY(authorDTO);
        try {
            return authorRepo.update(author);
        } catch (SQLException | ClassNotFoundException e) {
            if (((SQLException) e).getErrorCode() == 1406) {
                String message = ((SQLException) e).getMessage();
                String[] s = message.split("'");
                throw new AuthorException("Data is To Large For "+s[1]);
            }
            throw new AuthorException("Error Occurred Contact Developer",e);
        }
    }

    @Override
    public Optional<AuthorDTO> search(Integer integer) throws AuthorException {
        try {
            Optional<Author> search = authorRepo.search(integer);
            if(search.isPresent()){
                Author author = search.get(); //search ekn gthta
                AuthorDTO authorDTO = convertEntitytoDTO(author); //convert ekt dunna
                return Optional.of(authorDTO); //optional ekkin wrapkara
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new AuthorException("Contact Developer");
        }
        return Optional.empty();
    }

    @Override
    public List<AuthorDTO> getAll() throws AuthorException {
        try {
            List<Author> all = authorRepo.getAll();
//            List<AuthorDTO>list = new ArrayList<>();
//            for (Author author : all) {                                  Using stream API
//                AuthorDTO authorDTO = convertEntitytoDTO(author);
//                list.add(authorDTO);
//            }
//            return list;
            return all.stream().map(this::convertEntitytoDTO).toList();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private Author converDTOtoENTITY(AuthorDTO dto){
        return this.mapper.map(dto,Author.class);
    }
    private AuthorDTO convertEntitytoDTO(Author author){
        return this.mapper.map(author,AuthorDTO.class);
    }
}
