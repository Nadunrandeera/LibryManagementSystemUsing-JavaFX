package lk.codeschool.libry_management_system.controller.service;

import lk.codeschool.libry_management_system.controller.dto.SuperDTO;
import lk.codeschool.libry_management_system.controller.util.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface CrudService<T extends SuperDTO,ID> extends SuperService {
    boolean add(T t) throws ServiceException;
    boolean delete(ID id) throws ServiceException;
    boolean update(T t) throws ServiceException;
    Optional<T> search(ID id) throws ServiceException;
    List<T> getAll() throws ServiceException;
}
