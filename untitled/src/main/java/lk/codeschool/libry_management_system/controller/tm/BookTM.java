package lk.codeschool.libry_management_system.controller.tm;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookTM {
    private int id;
    private String name;
    private String isbn;
    private double price;
    private int publisherId;
    private int mainCategory;
}
