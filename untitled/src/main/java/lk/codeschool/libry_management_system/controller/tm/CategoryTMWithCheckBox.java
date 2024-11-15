package lk.codeschool.libry_management_system.controller.tm;

import javafx.scene.control.CheckBox;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryTMWithCheckBox {
    private int id;
    private String name;
    private CheckBox checkBox;

}
