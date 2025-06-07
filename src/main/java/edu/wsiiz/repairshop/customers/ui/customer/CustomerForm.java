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
    ComboBox<MarketingConsentCustomer> marketingConsentCustomerComboBox = new ComboBox<>(i18n("marketingConsentCustomerComboBox"));
    Checkbox klientBiznesowy = new Checkbox(i18n("klientBiznesowy"));
    Checkbox klientBiznesowy2 = new Checkbox(i18n("klientBiznesowy"));
//  private final Map<Zgoda, Checkbox> zgodyCheckboxy = new EnumMap<>(Zgoda.class);
//private final Map<String, Checkbox> zgodyCheckboxy = new EnumMap<>;
    private final Map<MarketingConsent, Checkbox> zgodyCheckboxy = new HashMap<>();



    public CustomerForm(Mode mode, Customer item, CustomerService service, Consumer<Customer> afterSave,
                        MarketingConsentRepository marketingConsentRepository) {
        super(mode,
                () -> Customer.builder().build(),
                () -> service.get(item.getId()),
                service::save,
                afterSave);
        this.marketingConsentRepository = marketingConsentRepository;
    }


    @Override
    public void setupFields() {
//    for (Zgoda zgoda : Zgoda.values()) {
//      Checkbox checkbox = new Checkbox(getLabelForZgoda(zgoda));
//      zgodyCheckboxy.put(zgoda, checkbox);
//      add(checkbox);

        klientBiznesowy.setLabel("Zgoda1");
        klientBiznesowy2.setLabel("Zgoda2");
//        List<MarketingConsent> consents = marketingConsentRepository.findAll();
//        for (MarketingConsent consent : consents) {
//            Checkbox checkbox = new Checkbox(consent.getDescription());
//            layout.add(checkbox);
//        }

        layout.add(firstName, lastName, pesel, regon, companyName,phoneNumber, vehicleRegistrationNumber, klientBiznesowy, klientBiznesowy2);
    }

}
