package lk.codeschool.libry_management_system.controller.dto.custom;

import lk.codeschool.libry_management_system.controller.dto.SuperDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO implements SuperDTO {
    private int id;
    private String name;
    private String isbn;
    private double price;
    private int publisherId;
    private int mainCategory;
    private List<Integer> subCategoryIds;
    private List<Integer> authorIds;

}
