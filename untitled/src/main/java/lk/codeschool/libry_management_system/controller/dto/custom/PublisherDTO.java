package lk.codeschool.libry_management_system.controller.dto.custom;

import lk.codeschool.libry_management_system.controller.dto.SuperDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublisherDTO implements SuperDTO {
    private int id;
    private String name;
    private String location;
    private String contact;
}
