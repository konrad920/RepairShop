package edu.wsiiz.repairshop.employee.ui.employee;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


import edu.wsiiz.repairshop.employee.application.EmployeeService;
import edu.wsiiz.repairshop.employee.domain.employee.Employee;
import edu.wsiiz.repairshop.employee.domain.employee.EmployeeRepository;
import edu.wsiiz.repairshop.foundation.ui.component.MessageDialog;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.function.Consumer;

@PageTitle("Kontakty z klientem")
@Route("employee-list")
public class EmployeeListView extends ListView<Employee> {

  final EmployeeRepository employeeRepository;
  final EmployeeService employeeService;

  public EmployeeListView(
      EmployeeRepository employeeRepository,
      EmployeeService employeeService) {
    this.employeeRepository = employeeRepository;
    this.employeeService = employeeService;
    setFilters(new EmployeeFilters(this::refreshGrid));
    setTitleText(i18n("title"));
    setupLayout();
  }

  @Override
  protected TriFunction<Employee, Mode, Consumer<Employee>, BaseForm<Employee>> detailsFormSupplier() {
    return (item, mode, afterSave) -> new EmployeeForm(mode, item, employeeService, afterSave);
  }

  @Override
  protected void setupGrid() {
    grid.addColumn("Imie", Employee::getFirstName);
    grid.addColumn("Nazwisko", Employee::getLastName);
    grid.addColumn("Adres", Employee::getAddress);
    grid.addColumn("Email", Employee::getEmail);
    grid.addColumn("WymiarCzasuPracy", Employee::getWorkTime);
    grid.addColumn("Stanowisko", Employee::getPosition);
    grid.addColumn("Kwalifikacje", Employee::getQualifications);
    grid.addColumn("Notatki", Employee::getNotes);
//    grid.addColumn("channel", c -> Optional.ofNullable(c.getChannel()).map(this::i18n).orElse(null));
//    grid.addColumn("status", c -> i18n(c.getStatus()));

    grid.setItems(
        query ->
            employeeRepository
                .findAll(getFilters(), PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("plannedDate")))
                .stream(),
        query -> {
          int count = (int) employeeRepository.count(getFilters());
          countField.setText(String.valueOf(count));
          return count;
        });
  }

  @Override
  protected void onDelete(Employee item) {
    MessageDialog.question()
        .withTitle(i18n("confirmation"))
        .withMessage(i18n("onDelete"))
        .withNoButton(() -> {
        })
        .withYesButton(() -> {
          employeeService.remove(item);
          refreshGrid();
        })
        .show();
  }
}
