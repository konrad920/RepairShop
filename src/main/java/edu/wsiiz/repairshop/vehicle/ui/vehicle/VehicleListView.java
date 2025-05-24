package edu.wsiiz.repairshop.vehicle.ui.vehicle;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.communication.application.ContactService;
import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import edu.wsiiz.repairshop.communication.domain.contact.ContactRepository;
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
@Route("contact-list")
public class VehicleListView extends ListView<Contact> {

  final ContactRepository contactRepository;
  final ContactService contactService;

  public VehicleListView(
      ContactRepository contactRepository,
      ContactService contactService) {
    this.contactRepository = contactRepository;
    this.contactService = contactService;
    setFilters(new VehicleFilters(this::refreshGrid));
    setTitleText(i18n("title"));
    setupLayout();
  }

  @Override
  protected TriFunction<Contact, Mode, Consumer<Contact>, BaseForm<Contact>> detailsFormSupplier() {
    return (item, mode, afterSave) -> new VehicleForm(mode, item, contactService, afterSave);
  }

  @Override
  protected void setupGrid() {
    grid.addColumn("description", Contact::getDescription);
    grid.addColumn("plannedDate", Contact::getPlannedDate);
    grid.addColumn("channel", c -> Optional.ofNullable(c.getChannel()).map(this::i18n).orElse(null));
    grid.addColumn("status", c -> i18n(c.getStatus()));

    grid.setItems(
        query ->
            contactRepository
                .findAll(getFilters(), PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("plannedDate")))
                .stream(),
        query -> {
          int count = (int) contactRepository.count(getFilters());
          countField.setText(String.valueOf(count));
          return count;
        });
  }

  @Override
  protected void onDelete(Contact item) {
    MessageDialog.question()
        .withTitle(i18n("confirmation"))
        .withMessage(i18n("onDelete"))
        .withNoButton(() -> {
        })
        .withYesButton(() -> {
          contactService.remove(item);
          refreshGrid();
        })
        .show();
  }
}
