package edu.wsiiz.repairshop.customers.ui.customer;

import com.vaadin.flow.component.textfield.TextField;
import edu.wsiiz.repairshop.customers.domain.customer.Address;
import edu.wsiiz.repairshop.customers.domain.customer.AddressService;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;

import java.util.function.Consumer;

public class AddressForm extends BaseForm<Address> {

    TextField street = new TextField(i18n("street"));
    TextField city = new TextField(i18n("city"));
    TextField zipCode = new TextField(i18n("zipCode"));
    TextField country = new TextField(i18n("country"));
    TextField type = new TextField(i18n("type")); // Np. "Korespondencyjny", "Siedziby"

    public AddressForm(Mode mode, Address item, AddressService service, Consumer<Address> afterSave) {
        super(mode,
                () -> Address.builder().build(),
                () -> service.get(item != null ? item.getId() : null),
                service::save,
                afterSave);
    }

    @Override
    public void setupFields() {
        layout.add(street, city, zipCode, country, type);
    }

    @Override
    protected void bindFields() {
        binder.bindInstanceFields(this);
    }

}
