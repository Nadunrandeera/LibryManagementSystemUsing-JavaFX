package lk.codeschool.libry_management_system.controller.tm;

import javafx.scene.control.CheckBox;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthorTMWithCheckBox {
    private int id;
    private String name;
    private CheckBox checkBox;

}
