package edu.wsiiz.repairshop.customers.ui.customer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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
import edu.wsiiz.repairshop.customers.domain.customer.*;
import edu.wsiiz.repairshop.foundation.ui.component.MessageDialog;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import lombok.val;
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
  private final MarketingConsentRepository marketingConsentRepository;
  private final AddressService addressService;

  public CustomerListView(
          CustomerRepository customerRepository,
          CustomerService customerService,
          MarketingConsentRepository marketingConsentRepository,
          AddressService addressService) {

    this.customerRepository = customerRepository;
    this.customerService = customerService;
    this.marketingConsentRepository = marketingConsentRepository;
    this.addressService = addressService;

    setFilters(new CustomerFilters(this::refreshGrid));
    setTitleText(i18n("title"));
    setupLayout();
  }


  @Override
  protected void addAdditionalActions(HorizontalLayout actions) {
    Button addBtn = new Button("Dezaktywuj klienta",LineAwesomeIcon.USER_SLASH_SOLID
            .create());
    addBtn.addClickListener(e -> runOnSelected(this::onDeactivate));

    actions.add(addBtn);

    Button addAddressBtn = new Button("Dodaj/zmień adres", LineAwesomeIcon.MAP_MARKED_SOLID.create());
    addAddressBtn.addClickListener(e -> runOnSelected(this::showAddressForm));
    actions.add(addAddressBtn);


  }

  @Override
  protected TriFunction<Customer, Mode, Consumer<Customer>, BaseForm<Customer>> detailsFormSupplier() {
    return (id, mode, afterSave) ->
            new CustomerForm(mode, id, customerService, afterSave, marketingConsentRepository);
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

  protected void onDeactivate(Customer item) {
    MessageDialog.question()
            .withTitle(i18n("confirmation"))
            .withMessage(i18n("onDeactivate"))
            .withNoButton(() -> {
            })
            .withYesButton(() -> {
              customerService.deactivate(item);
              refreshGrid();
            })
            .show();
  }
//  private void showAddressForm(Customer customer) {
//    val dialog = new Dialog();
//    dialog.setMinWidth("40em");
//
//    AddressForm form = new AddressForm(Mode.ADD, null, addressService, address -> {
//      address.setCustomer(customer);  // <- to przypisuje adres klientowi
//      addressService.save(address);   // <- możesz też to zrobić w writer
//      dialog.close();
//      showNotifation("Adres zapisany dla klienta: " + customer.getId());
//    });
//
//    form.init(dialog);
//    dialog.add(form);
//    dialog.open();
//  }



  private void showAddressForm(Customer customer) {
    val dialog = new Dialog();
    dialog.setMinWidth("40em");

    Address address = addressService.findByCustomerId(customer.getId())
            .stream()
            .findFirst()
            .orElse(Address.builder().customer(customer).build());

    Mode formMode = address.getId() == null ? Mode.ADD : Mode.EDIT;

    AddressForm form = new AddressForm(formMode , address, addressService, savedAddress -> {
      savedAddress.setCustomer(customer);
      addressService.save(savedAddress);
      dialog.close();
      showNotifation("Adres zapisany dla klienta: " + customer.getId());
    });
    form.init(dialog);
    dialog.add(form);
    dialog.open();
  }

}
