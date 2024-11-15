package lk.codeschool.libry_management_system.controller.service.custom.impl;

import lk.codeschool.libry_management_system.controller.dto.custom.CategoryDTO;
import lk.codeschool.libry_management_system.controller.entity.custom.Category;
import lk.codeschool.libry_management_system.controller.repo.custom.CategoryRepo;
import lk.codeschool.libry_management_system.controller.repo.custom.impl.CategoryRepoIMPL;
import lk.codeschool.libry_management_system.controller.service.custom.CategoryService;
import lk.codeschool.libry_management_system.controller.util.exception.ServiceException;
import lk.codeschool.libry_management_system.controller.util.exception.custom.BookException;
import lk.codeschool.libry_management_system.controller.util.exception.custom.CategoryException;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryServiceIMPL implements CategoryService {

    private final ModelMapper mapper;
    private final CategoryRepo repo;

    public CategoryServiceIMPL(ModelMapper mapper,CategoryRepo repo){
        this.mapper = mapper;
        this.repo = repo;
    }
    @Override
    public boolean add(CategoryDTO categoryDTO) throws ServiceException {
        Category category = convertDTOtoEntity(categoryDTO);
        try {
            return repo.save(category) == null;
        } catch (SQLException | ClassNotFoundException e) {
            if (((SQLException) e).getErrorCode() == 1062) {
                throw new BookException("ID Already Exists - Cannot Save.");
            } else if (((SQLException) e).getErrorCode() == 1406) {
                String message = ((SQLException) e).getMessage();
                String[] s = message.split("'");
                throw new BookException("Data is To Large For "+s[1]);
            }
            e.printStackTrace();
            throw new BookException("Error Occurred Contact Developer",e);
        }
    }

    @Override
    public boolean delete(Integer integer) throws ServiceException {
        try {
            boolean delete = repo.delete(integer);
            return delete;
        } catch (SQLException | ClassNotFoundException e) {
            throw new BookException("Error Occurred Contact Developper", e);
        }
    }

    @Override
    public boolean update(CategoryDTO categoryDTO) throws ServiceException {
        Category category = convertDTOtoEntity(categoryDTO);
        try {
            return repo.update(category);
        } catch (SQLException | ClassNotFoundException e) {
            if (e instanceof SQLException){
                if (((SQLException) e).getErrorCode() == 1406) {
                    String message = ((SQLException) e).getMessage();
                    String[] s = message.split("'");
                    throw new CategoryException("Data is To Large For "+s[1]);
                }
            }
            throw new CategoryException("Error Occurred - Contact Developer",e);
        }
    }

    @Override
    public Optional<CategoryDTO> search(Integer integer) throws ServiceException {
        try {
            Optional<Category> search = repo.search(integer);
            if(search.isPresent()){
                return Optional.of(convertEntitytoDTO(search.get()));
            }
            return Optional.empty();
        } catch (SQLException | ClassNotFoundException e) {
            throw new BookException("Contact Developer");
        }
    }

    @Override
    public List<CategoryDTO> getAll() throws ServiceException {
        try {
            return repo.getAll().stream()
                    .map(this::convertEntitytoDTO).toList();
        } catch (SQLException | ClassNotFoundException e) {
            throw new CategoryException("Contact Develloper",e);
        }
    }

    private Category convertDTOtoEntity(CategoryDTO dto){
        return mapper.map(dto,Category.class);
    }

    private CategoryDTO convertEntitytoDTO(Category category){
        return mapper.map(category,CategoryDTO.class);
    }
}
