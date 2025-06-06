package edu.wsiiz.repairshop.communication.ui.contact;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.auth.security.Secured;
import edu.wsiiz.repairshop.auth.domain.user.UserRole;
import edu.wsiiz.repairshop.communication.application.ContactService;
import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import edu.wsiiz.repairshop.communication.domain.contact.ContactRepository;
import edu.wsiiz.repairshop.foundation.ui.component.MessageDialog;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;

import java.util.Optional;
import java.util.function.Consumer;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@PageTitle("Kontakty z klientem")
@Route("contact-list")
@Secured(roles = {UserRole.EMPLOYEE,UserRole.ADMIN})
public class ContactListView extends ListView<Contact> {

  final ContactRepository contactRepository;
  final ContactService contactService;

  public ContactListView(
      ContactRepository contactRepository,
      ContactService contactService) {
    this.contactRepository = contactRepository;
    this.contactService = contactService;
    setFilters(new ContactFilters(this::refreshGrid));
    setTitleText(i18n("title"));
    setupLayout();
  }

  @Override
  protected TriFunction<Contact, Mode, Consumer<Contact>, BaseForm<Contact>> detailsFormSupplier() {
    return (item, mode, afterSave) -> new ContactForm(mode, item, contactService, afterSave);
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
