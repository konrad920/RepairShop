package edu.wsiiz.repairshop.customers.domain.customer;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.ClickEvent;
import edu.wsiiz.repairshop.customers.domain.customer.MarketingConsent;
import edu.wsiiz.repairshop.customers.domain.customer.MarketingConsentCustomer;
import edu.wsiiz.repairshop.customers.domain.customer.MarketingConsentService;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import java.time.LocalDate;
import java.util.function.Consumer;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
public class MarketingConsentCustomerForm extends BaseForm<MarketingConsentCustomer> {

    ComboBox<MarketingConsent> marketingConsent = new ComboBox<>(i18n("marketingConsent"));
    DatePicker consentDate = new DatePicker(i18n("consentDate"));
    Checkbox granted = new Checkbox(i18n("granted"));

    private final MarketingConsentService marketingConsentService;

    public MarketingConsentCustomerForm(Mode mode, MarketingConsentCustomer item, MarketingConsentService marketingConsentService, Consumer<MarketingConsentCustomer> afterSave) {
        super(mode,
                () -> MarketingConsentCustomer.builder()
                        .consentDate(LocalDate.now()) // Domyślna data jako dzisiejsza
                        .granted(true) // Domyślnie zgoda udzielona
                        .build(), // Fabryka nowego obiektu MarketingConsentCustomer
                () -> item != null && item.getId() != null ? null : item,
                (mcc) -> mcc,
                afterSave);

        this.marketingConsentService = marketingConsentService;
    }

    @Override
    public void setupFields() {
        // Wypełnij ComboBox predefiniowanymi zgodami marketingowymi
        marketingConsent.setItems(marketingConsentService.getAllConsents());
        marketingConsent.setItemLabelGenerator(MarketingConsent::getDescription);
        marketingConsent.setRequired(true);

        layout.add(marketingConsent, consentDate, granted);
    }

    @Override
    protected void bindFields() {
        super.bindFields();
        binder.forField(marketingConsent)
                .asRequired(i18n("marketingConsentRequired"))
                .bind(MarketingConsentCustomer::getMarketingConsent, MarketingConsentCustomer::setMarketingConsent);
    }

    // Ponieważ MarketingConsentCustomer jest zarządzane kaskadowo przez Customer,
    // nie wywołujemy tu bezpośrednio save() serwisu w onSave().
    // Zmiany są obsługiwane w CustomerForm i zapisywane razem z Customer.
    @Override
    protected void onSave(ClickEvent<Button> event) {
        try {
            binder.writeBean(model);
            showNotifation(i18n(BaseForm.class, "writeSuccess"));
            if (afterSave != null) {
                afterSave.accept(model);
            }
        } catch (ValidationException e) {
            showNotifation(i18n(BaseForm.class, "validationErrors"));
        }
    }
}
