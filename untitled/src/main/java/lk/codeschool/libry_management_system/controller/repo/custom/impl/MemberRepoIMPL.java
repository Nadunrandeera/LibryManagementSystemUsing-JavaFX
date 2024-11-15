package lk.codeschool.libry_management_system.controller.repo.custom.impl;

import lk.codeschool.libry_management_system.controller.entity.custom.Member;
import lk.codeschool.libry_management_system.controller.repo.custom.MemberRepo;
import lk.codeschool.libry_management_system.controller.util.CrudUtill;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberRepoIMPL implements MemberRepo {

    public Member save(Member member) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO member(id,name,address,email,contact)"+" VALUES (?,?,?,?,?)";
        CrudUtill.execute(sql, member.getId(), member.getName(),
                member.getAddress(), member.getEmail(), member.getEmail());
        return member;
    }
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM member WHERE id=?";
        boolean execute = CrudUtill.execute(sql, id);
        return execute;
    }
    public boolean update(Member member) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE member set name = ? , address = ? , email = ? , contact = ? where id = ?";
        boolean execute = CrudUtill.execute(sql,member.getName(), member.getAddress(), member.getEmail(), member.getContact(),member.getId());
        return execute;
    }
    public Optional<Member>search(String memberID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM member WHERE id = ?";
        ResultSet rs = CrudUtill.execute(sql, memberID);
        if(rs.next()){
            Member member = new Member();
            member.setId(rs.getString(1));
            member.setName(rs.getString(2));
            member.setAddress(rs.getString(3));
            member.setEmail(rs.getString(4));
            member.setContact(rs.getString(5));
            return Optional.of(member);
        }
        return Optional.empty();
    }
    public List<Member> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM member";
        ResultSet rs = CrudUtill.execute(sql);
        List<Member>list = new ArrayList<>();
        while (rs.next()){
            Member member = new Member();
            member.setId(rs.getString(1));
            member.setName(rs.getString(2));
            member.setAddress(rs.getString(3));
            member.setEmail(rs.getString(4));
            member.setContact(rs.getString(5));
            list.add(member);
        }
        return list;
    }
}
