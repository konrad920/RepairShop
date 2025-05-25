package edu.wsiiz.repairshop.customers.ui.customer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.communication.application.ContactService;
import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import edu.wsiiz.repairshop.communication.domain.contact.ContactItem;
import edu.wsiiz.repairshop.communication.domain.contact.ContactRepository;
import edu.wsiiz.repairshop.communication.ui.contact.ContactFilters;
import edu.wsiiz.repairshop.communication.ui.contact.ContactForm;
import edu.wsiiz.repairshop.customers.application.CustomerService;
import edu.wsiiz.repairshop.customers.domain.customer.Address;
import edu.wsiiz.repairshop.customers.domain.customer.Customer;
import edu.wsiiz.repairshop.customers.domain.customer.CustomerRepository;
import edu.wsiiz.repairshop.foundation.ui.component.MessageDialog;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.Optional;
import java.util.function.Consumer;

@PageTitle("Kontakty z klientem")
@Route("customer-list")
public class CustomerListView extends ListView<Customer> {

  final CustomerRepository customerRepository;
  final CustomerService customerService;

  public CustomerListView(
          CustomerRepository customerRepository,
          CustomerService customerService) {
    this.customerRepository = customerRepository;
    this.customerService = customerService;
    setFilters(new CustomerFilters(this::refreshGrid));
    setTitleText(i18n("title"));
    setupLayout();
  }

  @Override
  protected void addAdditionalActions(HorizontalLayout actions) {
    Button addBtn = new Button(LineAwesomeIcon.PLUS_SOLID.create());
    addBtn.addClickListener(e -> onAdd());
    actions.add( addBtn);
  }

  @Override
  protected TriFunction<Customer, Mode, Consumer<Customer>, BaseForm<Customer>> detailsFormSupplier() {
    return (id, mode, afterSave) ->
            new CustomerForm(mode, id, customerService, afterSave);
  }

  @Override
  protected void setupGrid() {
    grid.addColumn("iD", Customer::getId);
    grid.addColumn("firstName", Customer::getFirstName);
    grid.addColumn("lastName", Customer::getLastName);
    grid.addColumn("pesel", Customer::getPesel);
    grid.addColumn("companyName", Customer::getCompanyName);
    grid.addColumn("regon", Customer::getRegon);
    grid.addColumn("phoneNumber", Customer::getPhoneNumber);
    grid.addColumn("vehicleRegistrationNumber", Customer::getVehicleRegistrationNumber);




    grid.setItems(
            query ->
                    customerRepository
                            .findAll(getFilters(), PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id")))
                            .stream());
//        query -> {
//          int count = (int) customerRepository.count(getFilters());
//          countField.setText(String.valueOf(count));
//          return count;
//        });
  }

  @Override
  protected void onDelete(Customer item) {
    MessageDialog.question()
            .withTitle(i18n("confirmation"))
            .withMessage(i18n("onDelete"))
            .withNoButton(() -> {
            })
            .withYesButton(() -> {
              customerService.remove(item);
              refreshGrid();
            })
            .show();
  }
}
