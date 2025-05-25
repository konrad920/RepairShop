package edu.wsiiz.repairshop.customers.domain.customer;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;

import java.util.function.Consumer;
import org.apache.commons.lang3.function.TriFunction;

@PageTitle("Klienci")
@Route("customer-list")
public class CustomerListView extends ListView {

    final AuthorizedPersonService authorizedPersonService;
    final AddressService addressService;
    final MarketingConsentService marketingConsentService;

    public CustomerListView(
            AuthorizedPersonService authorizedPersonService,
            AddressService addressService,
            MarketingConsentService marketingConsentService) {
        this.authorizedPersonService = authorizedPersonService;
        this.addressService = addressService;
        this.marketingConsentService = marketingConsentService;

        setFilters(new CustomerFilters(this::refreshGrid));
        setTitleText(i18n("title"));
        setupLayout();
    }

    @Override
    protected TriFunction<Customer, Mode, Consumer<Customer>, BaseForm<Customer>> detailsFormSupplier() {
        // Przekaż wszystkie serwisy do CustomerForm
        return (item, mode, afterSave) -> new CustomerForm(mode, item, addressService, marketingConsentService, authorizedPersonService, afterSave);
    }

}
