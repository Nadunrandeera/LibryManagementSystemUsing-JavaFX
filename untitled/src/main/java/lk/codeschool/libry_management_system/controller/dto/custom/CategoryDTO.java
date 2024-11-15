package lk.codeschool.libry_management_system.controller.dto.custom;

import lk.codeschool.libry_management_system.controller.dto.SuperDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO implements SuperDTO {
    private int id;
    private String name;
}
