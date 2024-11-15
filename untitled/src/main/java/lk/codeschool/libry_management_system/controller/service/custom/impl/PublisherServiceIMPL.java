package lk.codeschool.libry_management_system.controller.service.custom.impl;

import lk.codeschool.libry_management_system.controller.dto.custom.PublisherDTO;
import lk.codeschool.libry_management_system.controller.entity.custom.Publisher;
import lk.codeschool.libry_management_system.controller.repo.custom.PublisherRepo;
import lk.codeschool.libry_management_system.controller.repo.custom.impl.PublisherRepoIMPL;
import lk.codeschool.libry_management_system.controller.service.custom.PublisherService;
import lk.codeschool.libry_management_system.controller.util.exception.ServiceException;
import lk.codeschool.libry_management_system.controller.util.exception.custom.PublisherException;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublisherServiceIMPL implements PublisherService {

    private final ModelMapper mapper ;
    final PublisherRepo repo ;

    public PublisherServiceIMPL(ModelMapper mapper,PublisherRepo publisherRepo){
        this.repo = publisherRepo;
        this.mapper = mapper;
    }

    @Override
    public boolean add(PublisherDTO publisherDTO) throws PublisherException {
        Publisher publisher = this.convertToEntity(publisherDTO);
        try {
            boolean save = repo.save(publisher) == null;
            return save;
        } catch (SQLException | ClassNotFoundException e) {
            if(((SQLException) e).getErrorCode() == 1062){
                throw new PublisherException("ID Already Exists - Cannot Save.");
            } else if (((SQLException)e).getErrorCode() == 1406) {
                String message = ((SQLException)e).getMessage();
                String[] split = message.split("'");
                throw new PublisherException("Data is To Large For "+split[1]);
            }
            throw new PublisherException("Error Occurred Contact Develloper",e);
        }
    }

    @Override
    public boolean delete(Integer integer) throws PublisherException {
        try {
            return repo.delete(integer);
        } catch (SQLException | ClassNotFoundException e) {
            throw new PublisherException("Not Implemented Yet");
        }
    }

    @Override
    public boolean update(PublisherDTO publisherDTO) throws PublisherException {
        Publisher publisher = this.convertToEntity(publisherDTO);
        try {
            return repo.update(publisher);
        } catch (SQLException | ClassNotFoundException e) {
            if (((SQLException)e).getErrorCode() == 1406) {
                String message = ((SQLException)e).getMessage();
                String[] split = message.split("'");
                throw new PublisherException("Data is To Large For "+split[1]);
            }
            throw new PublisherException("Error Occurred Contact Develloper",e);
        }
    }

    @Override
    public Optional<PublisherDTO> search(Integer integer) throws PublisherException {
        try {
            Optional<Publisher> search = repo.search(integer);
            if(search.isPresent()){
                return Optional.of(convertToDTO(search.get()));
            }
            return Optional.empty();
        } catch (SQLException | ClassNotFoundException e) {
            throw new PublisherException("Contact Developer",e);
        }
    }

    @Override
    public List<PublisherDTO> getAll() throws PublisherException {
        try {
            List<Publisher> all = repo.getAll();
            ArrayList<PublisherDTO> publisherDTOS = new ArrayList<>();
            for (Publisher publisher : all) {
                PublisherDTO publisherDTO = convertToDTO(publisher);
                publisherDTOS.add(publisherDTO);
            }
            return publisherDTOS;
        } catch (SQLException | ClassNotFoundException e) {
            throw new PublisherException("Contact Developer",e);
        }
    }

    private PublisherDTO convertToDTO(Publisher publisher){
        return mapper.map(publisher,PublisherDTO.class);
    }

    private Publisher convertToEntity(PublisherDTO publisherDTO){
        return mapper.map(publisherDTO,Publisher.class);
    }
}
