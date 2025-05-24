package edu.wsiiz.repairshop.vehicle.ui.vehicle;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import edu.wsiiz.repairshop.communication.application.ContactService;
import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import edu.wsiiz.repairshop.communication.domain.contact.ContactChannel;
import edu.wsiiz.repairshop.communication.domain.contact.ContactStatus;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;

import java.util.function.Consumer;

public class VehicleForm extends BaseForm<Contact> {

  String userCode;

  TextField description = new TextField(i18n("description"));
  DatePicker plannedDate = new DatePicker(i18n("plannedDate"));
  ComboBox<ContactChannel> channel = new ComboBox<>(i18n("channel"));
  ComboBox<ContactStatus> status = new ComboBox<>(i18n("status"));
  TextArea notes = new TextArea(i18n("notes"));

  public VehicleForm(Mode mode, Contact item, ContactService service, Consumer<Contact> afterSave) {
    super(mode,
        () -> Contact.builder()
            .status(ContactStatus.PLANNED)
            .build(),
        () -> service.get(item.getId()),
        service::save,
        afterSave);
  }

  @Override
  public void setupFields() {

    status.setItems(ContactStatus.values());
    status.setItemLabelGenerator(this::i18n);

    channel.setItems(ContactChannel.values());
    channel.setItemLabelGenerator(this::i18n);

    notes.setMinRows(5);

    layout.add(description, plannedDate, channel, status, notes);
  }

}
