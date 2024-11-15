package lk.codeschool.libry_management_system.controller.service.custom.impl;

import lk.codeschool.libry_management_system.controller.dto.custom.MemberDTO;
import lk.codeschool.libry_management_system.controller.entity.custom.Member;
import lk.codeschool.libry_management_system.controller.repo.custom.MemberRepo;
import lk.codeschool.libry_management_system.controller.repo.custom.impl.MemberRepoIMPL;
import lk.codeschool.libry_management_system.controller.service.custom.MemberService;
import lk.codeschool.libry_management_system.controller.util.exception.custom.MemberException;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberServiceIMPL implements MemberService {
    public final MemberRepo memberRepo;
    private final ModelMapper modelMapper;

    public MemberServiceIMPL(ModelMapper mapper,MemberRepo memberRepo){
        this.memberRepo = memberRepo;
        this.modelMapper = mapper;
    }

    @Override
    public boolean add(MemberDTO member) throws MemberException {
        Member entity = this.convertMemberDTOtoEntity(member);

        try {
            boolean isSaved = memberRepo.save(entity) == null;
            return isSaved;

        } catch (SQLException | ClassNotFoundException e) {
            if(e instanceof SQLException){
                if(((SQLException)e).getErrorCode() == 1062){
                    throw new MemberException("ID Already Exists - Can't save");
                } else if (((SQLException)e).getErrorCode() == 1406) {
                    String message = ((SQLException) e).getMessage();
                    String[] split = message.split("'") ;
                    throw new MemberException("Data is too large for "+split[1]);
                }
            }
            throw new MemberException("Error occurd contact developer",e);
        }
    }
    @Override
    public boolean delete(String id) throws MemberException {
        try {
            boolean isdelete = memberRepo.delete(id);
            return isdelete;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw  new MemberException("Error occured Contact Developer",e);
        }
    }
    @Override
    public boolean update(MemberDTO member) throws MemberException {
        Member entity = this.convertMemberDTOtoEntity(member);
        try {
            boolean isUpdate = memberRepo.update(entity);
            return isUpdate;
        } catch (SQLException | ClassNotFoundException e) {
            if(e instanceof SQLException){
                if (((SQLException)e).getErrorCode() == 1406) {
                    String message = ((SQLException) e).getMessage();
                    String[] split = message.split("'") ;
                    throw new MemberException("Data is too large for "+split[1]);
                }
            }
            throw new MemberException("Error occured");
        }
    }
    @Override
    public Optional<MemberDTO> search(String id) throws MemberException {
        try {
            Optional<Member> member = memberRepo.search(id);
            if(member.isPresent()){
                MemberDTO memberDTO = convertMemberEntitytoDTO(member.get());
                return Optional.of(memberDTO);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new MemberException("Contact Develloper",e);
        }
        return Optional.empty();
    }
    @Override
    public List<MemberDTO> getAll() throws MemberException {
        try {
            List<Member> all = memberRepo.getAll();
            if(all.isEmpty()){
                throw new MemberException("No members found");
            }
            List<MemberDTO> memberDTOList = new ArrayList<>();
            for (Member member : all) {
                MemberDTO memberDTO = convertMemberEntitytoDTO(member);
                memberDTOList.add(memberDTO);
            }
            return memberDTOList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new MemberException("Contact Developer", e);
        }
    }

    private Member convertMemberDTOtoEntity(MemberDTO memberDTO){
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setName(memberDTO.getName());
        member.setAddress(memberDTO.getAddress());
        member.setEmail(memberDTO.getEmail());
        member.setContact(memberDTO.getContact());

        return member;
    }
    private MemberDTO convertMemberEntitytoDTO(Member memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setName(memberEntity.getName());
        memberDTO.setAddress(memberEntity.getAddress());
        memberDTO.setEmail(memberEntity.getEmail());
        memberDTO.setContact(memberEntity.getContact());
        return memberDTO;
    }
}
