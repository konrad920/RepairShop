package edu.wsiiz.repairshop.customers.ui.customer;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.checkbox.Checkbox;


import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import edu.wsiiz.repairshop.communication.domain.contact.ContactChannel;
import edu.wsiiz.repairshop.communication.domain.contact.ContactStatus;
import edu.wsiiz.repairshop.customers.application.CustomerService;
import edu.wsiiz.repairshop.customers.domain.customer.*;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;




public class CustomerForm extends BaseForm<Customer> {

    private final MarketingConsentRepository marketingConsentRepository;

    private VerticalLayout authorizedPeopleSection = new VerticalLayout();


    TextField firstName = new TextField(i18n("firstName"));
    TextField lastName = new TextField(i18n("lastName"));
    TextField pesel = new TextField(i18n("pesel"));
    TextField regon = new TextField(i18n("regon"));
    TextField companyName = new TextField(i18n("companyName"));
    TextField phoneNumber = new TextField(i18n("phoneNumber"));
    TextField vehicleRegistrationNumber = new TextField(i18n("vehicleRegistrationNumber"));
    private final Map<MarketingConsent, Checkbox> zgodyCheckboxy = new HashMap<>();
    private Checkbox checkbox;


    public CustomerForm(Mode mode, Customer item, CustomerService service, Consumer<Customer> afterSave, MarketingConsentRepository marketingConsentRepository) {
        super(mode,
                () -> Customer.builder().build(),
                () -> service.get(item.getId()),
                service::save,
                afterSave);
        this.marketingConsentRepository = marketingConsentRepository;
    }


    @Override
    public void setupFields() {
        layout.add(firstName, lastName, pesel, regon, companyName,phoneNumber, vehicleRegistrationNumber);
        List<MarketingConsent> consents = marketingConsentRepository.findAll();
        for (MarketingConsent consent : consents) {
            checkbox = new Checkbox(consent.getDescription());
            zgodyCheckboxy.put(consent, checkbox);
            layout.add(checkbox);
        }
    }

}
