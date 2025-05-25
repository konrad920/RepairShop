package edu.wsiiz.repairshop.customers.domain.customer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.component.dialog.Dialog;
import edu.wsiiz.repairshop.foundation.ui.component.Grid;
import edu.wsiiz.repairshop.foundation.ui.component.MessageDialog;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.Optional;
public class CustomerForm  extends BaseForm<Customer> {


    private VerticalLayout authorizedPeopleSection = new VerticalLayout();
    private Grid<AuthorizedPerson> authorizedPeopleGrid;
    private Button addAuthorizedPersonButton = new Button(i18n("addAuthorizedPerson"), VaadinIcon.PLUS.create());
    private Button editAuthorizedPersonButton = new Button(i18n("editAuthorizedPerson"), VaadinIcon.EDIT.create());
    private Button removeAuthorizedPersonButton = new Button(i18n("removeAuthorizedPerson"), VaadinIcon.TRASH.create());


    private VerticalLayout addressesSection = new VerticalLayout();
    private Grid<Address> addressesGrid;
    private Button addAddressButton = new Button(i18n("addAddress"), VaadinIcon.PLUS.create());
    private Button editAddressButton = new Button(i18n("editAddress"), VaadinIcon.EDIT.create());
    private Button removeAddressButton = new Button(i18n("removeAddress"), VaadinIcon.TRASH.create());


    private VerticalLayout marketingConsentsSection = new VerticalLayout();
    private Grid<MarketingConsentCustomer> marketingConsentsGrid;
    private Button addMarketingConsentButton = new Button(i18n("addMarketingConsent"), VaadinIcon.PLUS.create());
    private Button editMarketingConsentButton = new Button(i18n("editMarketingConsent"), VaadinIcon.EDIT.create());
    private Button removeMarketingConsentButton = new Button(i18n("removeMarketingConsent"), VaadinIcon.TRASH.create());



    private final AuthorizedPersonService authorizedPersonService;
    private final AddressService addressService;
    private final MarketingConsentService marketingConsentService; // Wstrzyknij MarketingConsentService

    public CustomerForm(Mode mode, Customer item, AddressService addressService, MarketingConsentService marketingConsentService, AuthorizedPersonService authorizedPersonService, Consumer<Customer> afterSave) {
        super(mode,
                () -> {
                    Customer newCustomer = Customer.builder().active(true).build();
                    newCustomer.setAddresses(new ArrayList<>());
                    newCustomer.setMarketingConsents(new ArrayList<>()); // Upewnij się, że lista jest inicjalizowana
                    newCustomer.setAuthorizedPeople(new ArrayList<>());
                    return newCustomer;
                },
                () -> customerService.get(item != null ? item.getId() : null),
                customerService::save,
                afterSave);

        this.authorizedPersonService = authorizedPersonService;
        this.addressService = addressService;
        this.marketingConsentService = marketingConsentService; // Inicjalizuj serwis zgód
    }

    @Override
    public void setupFields() {

        setupAuthorizedPeopleSection();
        layout.add(authorizedPeopleSection);

        setupAddressesSection();
        layout.add(addressesSection);

        setupMarketingConsentsSection(); // NOWA: Sekcja zgód marketingowych
        layout.add(marketingConsentsSection);
    }

    private void setupAuthorizedPeopleSection() {
        authorizedPeopleGrid = new Grid<>(AuthorizedPerson.class);
        authorizedPeopleGrid.addColumn("firstName", AuthorizedPerson::getFirstName);
        authorizedPeopleGrid.addColumn("lastName", AuthorizedPerson::getLastName);
        authorizedPeopleGrid.addColumn("phoneNumber", AuthorizedPerson::getPhoneNumber);
        authorizedPeopleGrid.addColumn("email", AuthorizedPerson::getEmail);
        authorizedPeopleGrid.addColumn("role", AuthorizedPerson::getRole);

        HorizontalLayout buttonsLayout = new HorizontalLayout(
                addAuthorizedPersonButton,
                editAuthorizedPersonButton,
                removeAuthorizedPersonButton
        );
        buttonsLayout.setPadding(false);
        buttonsLayout.setSpacing(true);

        addAuthorizedPersonButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addAuthorizedPersonButton.addClickListener(e -> onAddAuthorizedPerson());

        editAuthorizedPersonButton.addClickListener(e -> onEditAuthorizedPerson());
        editAuthorizedPersonButton.setEnabled(false);

        removeAuthorizedPersonButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        removeAuthorizedPersonButton.addClickListener(e -> onRemoveAuthorizedPerson());
        removeAuthorizedPersonButton.setEnabled(false);

        authorizedPeopleGrid.asSingleSelect().addValueChangeListener(event -> {
            boolean isSelected = event.getValue() != null;
            editAuthorizedPersonButton.setEnabled(isSelected);
            removeAuthorizedPersonButton.setEnabled(isSelected);
        });

        authorizedPeopleSection.add(new Span(i18n("authorizedPeopleTitle")), buttonsLayout, authorizedPeopleGrid);
        authorizedPeopleSection.setWidthFull();
        authorizedPeopleSection.setVisible(false);
    }

    private void setupAddressesSection() {
        addressesGrid = new Grid<>(Address.class);
        addressesGrid.addColumn("street", Address::getStreet);
        addressesGrid.addColumn("city", Address::getCity);
        addressesGrid.addColumn("zipCode", Address::getZipCode);
        addressesGrid.addColumn("country", Address::getCountry);
        addressesGrid.addColumn("type", Address::getType);

        HorizontalLayout buttonsLayout = new HorizontalLayout(
                addAddressButton,
                editAddressButton,
                removeAddressButton
        );
        buttonsLayout.setPadding(false);
        buttonsLayout.setSpacing(true);

        addAddressButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addAddressButton.addClickListener(e -> onAddAddress());

        editAddressButton.addClickListener(e -> onEditAddress());
        editAddressButton.setEnabled(false);

        removeAddressButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        removeAddressButton.addClickListener(e -> onRemoveAddress());
        removeAddressButton.setEnabled(false);

        addressesGrid.asSingleSelect().addValueChangeListener(event -> {
            boolean isSelected = event.getValue() != null;
            editAddressButton.setEnabled(isSelected);
            removeAddressButton.setEnabled(isSelected);
        });

        addressesSection.add(new Span(i18n("addressesTitle")), buttonsLayout, addressesGrid);
        addressesSection.setWidthFull();
    }

    private void setupMarketingConsentsSection() {
        marketingConsentsGrid = new Grid<>(MarketingConsentCustomer.class); // Użyj klasy MarketingConsentCustomer
        // Kolumny dla zgód marketingowych
        marketingConsentsGrid.addColumn("consentId", mcc -> mcc.getMarketingConsent() != null ? mcc.getMarketingConsent().getId() : null).setHeader(i18n("consentId"));
        marketingConsentsGrid.addColumn("description", mcc -> mcc.getMarketingConsent() != null ? mcc.getMarketingConsent().getDescription() : null).setHeader(i18n("consentDescription"));
        marketingConsentsGrid.addColumn("consentDate", MarketingConsentCustomer::getConsentDate).setHeader(i18n("consentDate"));
        marketingConsentsGrid.addColumn("granted", mcc -> i18n(mcc.isGranted() ? "yes" : "no")).setHeader(i18n("granted")); // Tak/Nie dla checkboxa

        // Przyciski do zarządzania zgodami marketingowymi
        HorizontalLayout buttonsLayout = new HorizontalLayout(
                addMarketingConsentButton,
                editMarketingConsentButton,
                removeMarketingConsentButton
        );
        buttonsLayout.setPadding(false);
        buttonsLayout.setSpacing(true);

        addMarketingConsentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addMarketingConsentButton.addClickListener(e -> onAddMarketingConsent());

        editMarketingConsentButton.addClickListener(e -> onEditMarketingConsent());
        editMarketingConsentButton.setEnabled(false); // Domyślnie wyłączony

        removeMarketingConsentButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        removeMarketingConsentButton.addClickListener(e -> onRemoveMarketingConsent());
        removeMarketingConsentButton.setEnabled(false); // Domyślnie wyłączony

        marketingConsentsGrid.asSingleSelect().addValueChangeListener(event -> {
            boolean isSelected = event.getValue() != null;
            editMarketingConsentButton.setEnabled(isSelected);
            removeMarketingConsentButton.setEnabled(isSelected);
        });

        marketingConsentsSection.add(new Span(i18n("marketingConsentsTitle")), buttonsLayout, marketingConsentsGrid);
        marketingConsentsSection.setWidthFull();
    }



    @Override
    protected void bindFields() {
        super.bindFields();
    }

    @Override
    protected void onSave(com.vaadin.flow.component.ClickEvent<Button> event) {
        try {
            binder.writeBean(model);
            this.model = writer.apply(model);
            showNotifation(i18n(BaseForm.class, "writeSuccess"));
            if (afterSave != null) {
                afterSave.accept(model);
            }
        } catch (ValidationException e) {
            showNotifation(i18n(BaseForm.class, "validationErrors"));
        }
    }

    @Override
    public void init(Dialog dialog) {
        super.init(dialog);
        updateFormFieldsVisibility(model.getCustomerType());
        if(model.getAuthorizedPeople() != null) {
            authorizedPeopleGrid.setItems(model.getAuthorizedPeople());
        }
        if(model.getAddresses() != null) {
            addressesGrid.setItems(model.getAddresses());
        }
        if(model.getMarketingConsents() != null) {
            marketingConsentsGrid.setItems(model.getMarketingConsents());
        }
        if (mode.isView()) {
            addAuthorizedPersonButton.setEnabled(false);
            editAuthorizedPersonButton.setEnabled(false);
            removeAuthorizedPersonButton.setEnabled(false);
            addAddressButton.setEnabled(false);
            editAddressButton.setEnabled(false);
            removeAddressButton.setEnabled(false);
            addMarketingConsentButton.setEnabled(false);
            editMarketingConsentButton.setEnabled(false);
            removeMarketingConsentButton.setEnabled(false);
        }
    }

    private void updateFormFieldsVisibility(CustomerType currentCustomerType) {
        boolean isCompany = currentCustomerType == CustomerType.COMPANY;
        authorizedPeopleSection.setVisible(isCompany);

        if (!isCompany) {
            regon.clear();
            companyName.clear();
            if (model != null) {
                model.setRegon(null);
                model.setCompanyName(null);
                model.setAuthorizedPeople(new ArrayList<>());
            }
        } else {
            pesel.clear();
            firstName.clear();
            lastName.clear();
            if (model != null) {
                model.setPesel(null);
                model.setFirstName(null);
                model.setLastName(null);
            }
        }
    }

    private void onAddAuthorizedPerson() { /* ... */ }
    private void onEditAuthorizedPerson() { /* ... */ }
    private void onRemoveAuthorizedPerson() { /* ... */ }

    private void onAddAddress() { /* ... */ }
    private void onEditAddress() { /* ... */ }
    private void onRemoveAddress() { /* ... */ }

    private void onAddMarketingConsent() {
        Dialog dialog = new Dialog();
        dialog.setMinWidth("30em");
        MarketingConsentCustomerForm mccForm = new MarketingConsentCustomerForm(Mode.ADD, null, marketingConsentService, createdConsent -> {
            if (model.getMarketingConsents() == null) {
                model.setMarketingConsents(new ArrayList<>());
            }
            model.getMarketingConsents().add(createdConsent);
            marketingConsentsGrid.setItems(model.getMarketingConsents()); // Odśwież grid
            showNotifation(i18n("marketingConsentAdded"));
            dialog.close();
        });
        mccForm.init(dialog);
        dialog.add(mccForm);
        dialog.open();
    }

    private void onEditMarketingConsent() {
        Optional<MarketingConsentCustomer> selectedConsent = marketingConsentsGrid.asSingleSelect().getOptionalValue();
        selectedConsent.ifPresent(consentToEdit -> {
            Dialog dialog = new Dialog();
            dialog.setMinWidth("30em");
            MarketingConsentCustomerForm mccForm = new MarketingConsentCustomerForm(Mode.EDIT, consentToEdit, marketingConsentService, updatedConsent -> {
                marketingConsentsGrid.getDataProvider().refreshItem(updatedConsent);
                showNotifation(i18n("marketingConsentEdited"));
                dialog.close();
            });
            mccForm.init(dialog);
            dialog.add(mccForm);
            dialog.open();
        });
    }

    private void onRemoveMarketingConsent() {
        Optional<MarketingConsentCustomer> selectedConsent = marketingConsentsGrid.asSingleSelect().getOptionalValue();
        selectedConsent.ifPresent(consentToRemove -> {
            MessageDialog.question()
                    .withTitle(i18n("confirmation"))
                    .withMessage(i18n("onRemoveMarketingConsent"))
                    .withNoButton(dialog::close)
                    .withYesButton(() -> {
                        // Usuń zgodę z listy w modelu
                        if (model.getMarketingConsents() != null) {
                            model.getMarketingConsents().remove(consentToRemove);
                        }
                        // Ponieważ MarketingConsentCustomer ma orphanRemoval = true w Customer,
                        // usunięcie z listy spowoduje usunięcie z bazy przy zapisie klienta.
                        marketingConsentsGrid.setItems(model.getMarketingConsents()); // Odśwież grid
                        showNotifation(i18n("marketingConsentRemoved"));
                    })
                    .show();
        });
    }
}
