package lk.codeschool.libry_management_system.controller.service.util;

import org.modelmapper.ModelMapper;

public class OtherDependecies {
    private final static OtherDependecies instance = new OtherDependecies();
    private final ModelMapper mapper;

    private OtherDependecies(){
        this.mapper = new ModelMapper();
    }

    public static OtherDependecies getInstance(){
        return instance;
    }

    public ModelMapper getMapper() {
        return mapper;
    }
}
