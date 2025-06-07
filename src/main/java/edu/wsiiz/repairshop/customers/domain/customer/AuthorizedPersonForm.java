package edu.wsiiz.repairshop.customers.domain.customer;

import com.vaadin.flow.component.textfield.TextField;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import java.util.function.Consumer;
public class AuthorizedPersonForm extends BaseForm<AuthorizedPerson> {
    TextField firstName = new TextField(i18n("firstName"));
    TextField lastName = new TextField(i18n("lastName"));
    TextField phoneNumber = new TextField(i18n("phoneNumber"));
    TextField email = new TextField(i18n("email"));
    TextField role = new TextField(i18n("role"));

    public AuthorizedPersonForm(Mode mode, AuthorizedPerson item, AuthorizedPersonService service, Consumer<AuthorizedPerson> afterSave) {
        super(mode,
                () -> AuthorizedPerson.builder().build(),
                () -> service.get(item != null ? item.getId() : null),
                service::save,
                afterSave);
    }

    @Override
    public void setupFields() {
        layout.add(firstName, lastName, phoneNumber, email, role);
    }

    @Override
    protected void bindFields() {
        binder.bindInstanceFields(this);
    }
}
