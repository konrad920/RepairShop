package edu.wsiiz.repairshop.employee.ui.employee;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import edu.wsiiz.repairshop.employee.application.EmployeeService;
import edu.wsiiz.repairshop.employee.domain.employee.Employee;
import edu.wsiiz.repairshop.employee.domain.employee.Positions;
import edu.wsiiz.repairshop.employee.domain.employee.Qualifications;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;

import java.net.DatagramSocket;
import java.util.function.Consumer;

public class EmployeeForm extends BaseForm<Employee> {


  TextField firstName = new TextField(i18n("Imie"));
  TextField lastName = new TextField(i18n("Nazwisko"));
  TextField address = new TextField(i18n("Adres"));
  TextField email = new TextField(i18n("Email"));
  TextField workTime = new TextField(i18n("WymiarCzasuPracy"));
  ComboBox<Positions> position = new ComboBox<>(i18n("Stanowisko(position)"));
//  ComboBox<Qualifications> qualifications = new ComboBox<>(i18n("Kwalifikacje"));
//  MultiSelectComboBox<Qualifications> qualifications = new MultiSelectComboBox<>(i18n("Kwalifikacje"));
  private MultiSelectComboBox<Qualifications> qualifications = new MultiSelectComboBox<>(i18n("Kwalifikacje"));
  TextArea notes = new TextArea(i18n("Notatki"));

  public EmployeeForm(Mode mode, Employee item, EmployeeService service, Consumer<Employee> afterSave) {
    super(mode,
        () -> Employee.builder()
            .position(Positions.KIEROWNIK)
            .build(),
        () -> item.getId() == null ? item : service.get(item.getId()),
        service::save,
        afterSave);
  }

  @Override
  public void setupFields() {

    position.setItems(Positions.values());
    position.setItemLabelGenerator(this::i18n);

    qualifications.setItems(Qualifications.values());
    qualifications.setItemLabelGenerator(this::i18n);

    notes.setMinRows(5);

    layout.add(firstName, lastName, address, email, workTime, position, qualifications, notes);



  }


}
