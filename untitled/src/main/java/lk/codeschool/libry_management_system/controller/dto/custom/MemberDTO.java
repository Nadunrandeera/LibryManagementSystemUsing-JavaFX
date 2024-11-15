package lk.codeschool.libry_management_system.controller.dto.custom;

import lk.codeschool.libry_management_system.controller.dto.SuperDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO implements SuperDTO {
    private String id;
    private String name;
    private String address;
    private String email;
    private String contact;
}
