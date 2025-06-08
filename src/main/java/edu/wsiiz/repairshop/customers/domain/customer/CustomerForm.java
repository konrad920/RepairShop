//package edu.wsiiz.repairshop.customers.domain.customer;
//
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.datepicker.DatePicker; // Choć DatePicker nie jest używany bezpośrednio w CustomerForm, zostawiam na wypadek, gdyby był potrzebny w przyszłości.
//import com.vaadin.flow.component.formlayout.FormLayout;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.component.html.Span;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextArea; // Choć TextArea nie jest używana bezpośrednio w CustomerForm, zostawiam na wypadek, gdyby była potrzebna w przyszłości.
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.BeanValidationBinder;
//import com.vaadin.flow.data.binder.Binder;
//import com.vaadin.flow.data.binder.ValidationException;
//import com.vaadin.flow.component.dialog.Dialog;
//import com.vaadin.flow.component.ClickEvent; // Import dla ClickEvent
//import edu.wsiiz.repairshop.customers.domain.customer.*; // Importy dla klas klienta i powiązanych encji
//import edu.wsiiz.repairshop.foundation.ui.component.Grid;
//import edu.wsiiz.repairshop.foundation.ui.component.MessageDialog;
//import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
//import edu.wsiiz.repairshop.foundation.ui.view.Mode;
//import lombok.val;
//
//import java.time.LocalDate; // Import dla LocalDate, jeśli używane w inicjalizacji
//import java.util.ArrayList;
//import java.util.function.Consumer;
//import java.util.Optional;
//public class CustomerForm  extends BaseForm<Customer> {
//
//    // Pola formularza klienta
//    TextField firstName = new TextField(i18n("firstName"));
//    TextField lastName = new TextField(i18n("lastName"));
//    TextField pesel = new TextField(i18n("pesel"));
//    TextField regon = new TextField(i18n("regon"));
//    TextField companyName = new TextField(i18n("companyName"));
//    TextField vehicleRegistrationNumber = new TextField(i18n("vehicleRegistrationNumber"));
//    ComboBox<CustomerType> customerType = new ComboBox<>(i18n("customerType"));
//
//    // Sekcja dla osób odpowiedzialnych
//    private VerticalLayout authorizedPeopleSection = new VerticalLayout();
//    private Grid<AuthorizedPerson> authorizedPeopleGrid;
//    private Button addAuthorizedPersonButton = new Button(i18n("addAuthorizedPerson"), VaadinIcon.PLUS.create());
//    private Button editAuthorizedPersonButton = new Button(i18n("editAuthorizedPerson"), VaadinIcon.EDIT.create());
//    private Button removeAuthorizedPersonButton = new Button(i18n("removeAuthorizedPerson"), VaadinIcon.TRASH.create());
//
//    // Sekcja dla adresów
//    private VerticalLayout addressesSection = new VerticalLayout();
//    private Grid<Address> addressesGrid;
//    private Button addAddressButton = new Button(i18n("addAddress"), VaadinIcon.PLUS.create());
//    private Button editAddressButton = new Button(i18n("editAddress"), VaadinIcon.EDIT.create());
//    private Button removeAddressButton = new Button(i18n("removeAddress"), VaadinIcon.TRASH.create());
//
//    // Sekcja dla zgód marketingowych
//    private VerticalLayout marketingConsentsSection = new VerticalLayout();
//    private Grid<MarketingConsentCustomer> marketingConsentsGrid;
//    private Button addMarketingConsentButton = new Button(i18n("addMarketingConsent"), VaadinIcon.PLUS.create());
//    private Button editMarketingConsentButton = new Button(i18n("editMarketingConsent"), VaadinIcon.EDIT.create());
//    private Button removeMarketingConsentButton = new Button(i18n("removeMarketingConsent"), VaadinIcon.TRASH.create());
//
//
//    // Serwisy wstrzykiwane do formularza
//    private final AuthorizedPersonService authorizedPersonService;
//    private final AddressService addressService;
//    private final MarketingConsentService marketingConsentService;
//    private Dialog currentDialog;
//
//    public CustomerForm(Mode mode, Customer item,
//                        CustomerService customerService,
//                        AuthorizedPersonService authorizedPersonService,
//                        AddressService addressService,
//                        MarketingConsentService marketingConsentService,
//                        Consumer<Customer> afterSave) {
//        super(mode,
//                () -> {
//                    Customer newCustomer = Customer.builder().isActive(true).build();
//                    newCustomer.setAddresses(new ArrayList<>()); // Inicjalizacja list, aby uniknąć NullPointerException
//                    newCustomer.setMarketingConsents(new ArrayList<>());
//                    newCustomer.setAuthorizedPeople(new ArrayList<>());
//                    return newCustomer;
//                },
//                () -> customerService.get(item != null ? item.getId() : null), // Reader: pobierz klienta, jeśli istnieje
//                customerService::save, // Writer: metoda zapisu klienta
//                afterSave);
//
//        this.authorizedPersonService = authorizedPersonService;
//        this.addressService = addressService;
//        this.marketingConsentService = marketingConsentService;
//    }
//
//    @Override
//    public void setupFields() {
//        customerType.setItems(CustomerType.values());
//        customerType.setItemLabelGenerator(this::i18n);
//        customerType.setRequired(true);
//        customerType.addValueChangeListener(event -> updateFormFieldsVisibility(event.getValue()));
//
//        // Dodanie podstawowych pól klienta do layoutu
//        layout.add(customerType, firstName, lastName, pesel, regon, companyName, vehicleRegistrationNumber);
//
//        // Konfiguracja i dodanie sekcji dla osób odpowiedzialnych
//        setupAuthorizedPeopleSection();
//        layout.add(authorizedPeopleSection);
//
//        // Konfiguracja i dodanie sekcji dla adresów
//        setupAddressesSection();
//        layout.add(addressesSection);
//
//        // Konfiguracja i dodanie sekcji dla zgód marketingowych
//        setupMarketingConsentsSection();
//        layout.add(marketingConsentsSection);
//    }
//
//    private void setupAuthorizedPeopleSection() {
//        authorizedPeopleGrid = new Grid<>(AuthorizedPerson.class);
//        authorizedPeopleGrid.addColumn("firstName", AuthorizedPerson::getFirstName);
//        authorizedPeopleGrid.addColumn("lastName", AuthorizedPerson::getLastName);
//        authorizedPeopleGrid.addColumn("phoneNumber", AuthorizedPerson::getPhoneNumber);
//        authorizedPeopleGrid.addColumn("email", AuthorizedPerson::getEmail);
//        authorizedPeopleGrid.addColumn("role", AuthorizedPerson::getRole);
//
//        HorizontalLayout buttonsLayout = new HorizontalLayout(
//                addAuthorizedPersonButton,
//                editAuthorizedPersonButton,
//                removeAuthorizedPersonButton
//        );
//        buttonsLayout.setPadding(false);
//        buttonsLayout.setSpacing(true);
//
//        addAuthorizedPersonButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        addAuthorizedPersonButton.addClickListener(e -> onAddAuthorizedPerson());
//
//        editAuthorizedPersonButton.addClickListener(e -> onEditAuthorizedPerson());
//        editAuthorizedPersonButton.setEnabled(false);
//
//        removeAuthorizedPersonButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
//        removeAuthorizedPersonButton.addClickListener(e -> onRemoveAuthorizedPerson());
//        removeAuthorizedPersonButton.setEnabled(false);
//
//        authorizedPeopleGrid.asSingleSelect().addValueChangeListener(event -> {
//            boolean isSelected = event.getValue() != null;
//            editAuthorizedPersonButton.setEnabled(isSelected);
//            removeAuthorizedPersonButton.setEnabled(isSelected);
//        });
//
//        authorizedPeopleSection.add(new Span(i18n("authorizedPeopleTitle")), buttonsLayout, authorizedPeopleGrid);
//        authorizedPeopleSection.setWidthFull();
//        authorizedPeopleSection.setVisible(false); // Domyślnie ukryta, pokazywana dla firm
//    }
//
//    private void setupAddressesSection() {
//        addressesGrid = new Grid<>(Address.class);
//        addressesGrid.addColumn("street", Address::getStreet);
//        addressesGrid.addColumn("city", Address::getCity);
//        addressesGrid.addColumn("zipCode", Address::getZipCode);
//        addressesGrid.addColumn("country", Address::getCountry);
//        addressesGrid.addColumn("type", Address::getType);
//
//        HorizontalLayout buttonsLayout = new HorizontalLayout(
//                addAddressButton,
//                editAddressButton,
//                removeAddressButton
//        );
//        buttonsLayout.setPadding(false);
//        buttonsLayout.setSpacing(true);
//
//        addAddressButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        addAddressButton.addClickListener(e -> onAddAddress());
//
//        editAddressButton.addClickListener(e -> onEditAddress());
//        editAddressButton.setEnabled(false);
//
//        removeAddressButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
//        removeAddressButton.addClickListener(e -> onRemoveAddress());
//        removeAddressButton.setEnabled(false);
//
//        addressesGrid.asSingleSelect().addValueChangeListener(event -> {
//            boolean isSelected = event.getValue() != null;
//            editAddressButton.setEnabled(isSelected);
//            removeAddressButton.setEnabled(isSelected);
//        });
//
//        addressesSection.add(new Span(i18n("addressesTitle")), buttonsLayout, addressesGrid);
//        addressesSection.setWidthFull();
//        // Adresy są zawsze widoczne, więc nie ustawiamy domyślnie na 'false'
//    }
//
//    private void setupMarketingConsentsSection() {
//        marketingConsentsGrid = new Grid<>(MarketingConsentCustomer.class);
//        marketingConsentsGrid.addColumn("consentId", mcc -> mcc.getMarketingConsent() != null ? mcc.getMarketingConsent().getId() : null).setHeader(i18n("consentId"));
//        marketingConsentsGrid.addColumn("description", mcc -> mcc.getMarketingConsent() != null ? mcc.getMarketingConsent().getDescription() : null).setHeader(i18n("consentDescription"));
//        marketingConsentsGrid.addColumn("consentDate", MarketingConsentCustomer::getConsentDate).setHeader(i18n("consentDate"));
//        marketingConsentsGrid.addColumn("granted", mcc -> i18n(mcc.isGranted() ? "yes" : "no")).setHeader(i18n("granted"));
//
//        HorizontalLayout buttonsLayout = new HorizontalLayout(
//                addMarketingConsentButton,
//                editMarketingConsentButton,
//                removeMarketingConsentButton
//        );
//        buttonsLayout.setPadding(false);
//        buttonsLayout.setSpacing(true);
//
//        addMarketingConsentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        addMarketingConsentButton.addClickListener(e -> onAddMarketingConsent());
//
//        editMarketingConsentButton.addClickListener(e -> onEditMarketingConsent());
//        editMarketingConsentButton.setEnabled(false);
//
//        removeMarketingConsentButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
//        removeMarketingConsentButton.addClickListener(e -> onRemoveMarketingConsent());
//        removeMarketingConsentButton.setEnabled(false);
//
//        marketingConsentsGrid.asSingleSelect().addValueChangeListener(event -> {
//            boolean isSelected = event.getValue() != null;
//            editMarketingConsentButton.setEnabled(isSelected);
//            removeMarketingConsentButton.setEnabled(isSelected);
//        });
//
//        marketingConsentsSection.add(new Span(i18n("marketingConsentsTitle")), buttonsLayout, marketingConsentsGrid);
//        marketingConsentsSection.setWidthFull();
//    }
//
//
//    @Override
//    protected void bindFields() {
//        super.bindFields();
//    }
//
//    @Override
//    protected void onSave(ClickEvent<Button> event) {
//        try {
//            binder.writeBean(model);
//            this.model = writer.apply(model);
//            showNotifation(i18n(BaseForm.class, "writeSuccess"));
//            if (afterSave != null) {
//                afterSave.accept(model);
//            }
//        } catch (ValidationException e) {
//            showNotifation(i18n(BaseForm.class, "validationErrors"));
//        }
//    }
//
//    @Override
//    public void init(Dialog dialog) {
//        super.init(dialog);
//        this.currentDialog = dialog;
//        updateFormFieldsVisibility(model.getCustomerType());
//
//        if(model.getAuthorizedPeople() != null) {
//            authorizedPeopleGrid.setItems(model.getAuthorizedPeople());
//        }
//        if(model.getAddresses() != null) {
//            addressesGrid.setItems(model.getAddresses());
//        }
//        if(model.getMarketingConsents() != null) {
//            marketingConsentsGrid.setItems(model.getMarketingConsents());
//        }
//
//        if (mode.isView()) {
//            addAuthorizedPersonButton.setEnabled(false);
//            editAuthorizedPersonButton.setEnabled(false);
//            removeAuthorizedPersonButton.setEnabled(false);
//            addAddressButton.setEnabled(false);
//            editAddressButton.setEnabled(false);
//            removeAddressButton.setEnabled(false);
//            addMarketingConsentButton.setEnabled(false);
//            editMarketingConsentButton.setEnabled(false);
//            removeMarketingConsentButton.setEnabled(false);
//        }
//    }
//
//    private void updateFormFieldsVisibility(CustomerType currentCustomerType) {
//        boolean isCompany = currentCustomerType == CustomerType.COMPANY;
//
//        // Widoczność pól dla klienta indywidualnego
//        firstName.setVisible(!isCompany);
//        lastName.setVisible(!isCompany);
//        pesel.setVisible(!isCompany);
//
//        // Widoczność pól dla firmy
//        regon.setVisible(isCompany);
//        companyName.setVisible(isCompany);
//
//        // Widoczność sekcji osób odpowiedzialnych (tylko dla firm)
//        authorizedPeopleSection.setVisible(isCompany);
//
//        // Czyszczenie wartości pól, które są ukrywane, aby uniknąć problemów z walidacją i danymi
//        if (!isCompany) {
//            regon.clear();
//            companyName.clear();
//            if (model != null) {
//                model.setRegon(null);
//                model.setCompanyName(null);
//                // Wyczyść listę, jeśli zmieniamy na os. fizyczną, aby usunąć powiązania z firmą
//                model.setAuthorizedPeople(new ArrayList<>());
//            }
//        } else {
//            pesel.clear();
//            firstName.clear();
//            lastName.clear();
//            if (model != null) {
//                model.setPesel(null);
//                model.setFirstName(null);
//                model.setLastName(null);
//            }
//        }
//    }
//
//
//    // --- Metody do zarządzania osobami odpowiedzialnymi ---
//    private void onAddAuthorizedPerson() {
//        Dialog dialog = new Dialog();
//        dialog.setMinWidth("30em");
//        AuthorizedPersonForm apForm = new AuthorizedPersonForm(Mode.ADD, null, authorizedPersonService, createdPerson -> {
//            if (model.getAuthorizedPeople() == null) {
//                model.setAuthorizedPeople(new ArrayList<>());
//            }
//            model.getAuthorizedPeople().add(createdPerson);
//            authorizedPeopleGrid.setItems(model.getAuthorizedPeople());
//            showNotifation(i18n("authorizedPersonAdded"));
//            dialog.close();
//        });
//        apForm.init(dialog);
//        dialog.add(apForm);
//        dialog.open();
//    }
//
//    private void onEditAuthorizedPerson() {
//        Optional<AuthorizedPerson> selectedPerson = authorizedPeopleGrid.asSingleSelect().getOptionalValue();
//        selectedPerson.ifPresent(personToEdit -> {
//            Dialog dialog = new Dialog();
//            dialog.setMinWidth("30em");
//            AuthorizedPersonForm apForm = new AuthorizedPersonForm(Mode.EDIT, personToEdit, authorizedPersonService, updatedPerson -> {
//                // Odśwież tylko zmieniony element w gridzie
//                authorizedPeopleGrid.getDataProvider().refreshItem(updatedPerson);
//                showNotifation(i18n("authorizedPersonEdited"));
//                dialog.close();
//            });
//            apForm.init(dialog);
//            dialog.add(apForm);
//            dialog.open();
//        });
//    }
//
//    private void onRemoveAuthorizedPerson() {
//        Optional<AuthorizedPerson> selectedPerson = authorizedPeopleGrid.asSingleSelect().getOptionalValue();
//        selectedPerson.ifPresent(personToRemove -> {
//            MessageDialog.question()
//                    .withTitle(i18n("confirmation"))
//                    .withMessage(i18n("onRemoveAuthorizedPerson"))
//                    .withNoButton(() -> currentDialog.close()) // ZMIANA TUTAJ: użyj currentDialog
//                    .withYesButton(() -> {
//                        if (model.getAuthorizedPeople() != null) {
//                            model.getAuthorizedPeople().remove(personToRemove);
//                        }
//                        authorizedPeopleGrid.setItems(model.getAuthorizedPeople());
//                        showNotifation(i18n("authorizedPersonRemoved"));
//                    })
//                    .show();
//        });
//    }
//
//    // --- Metody do zarządzania adresami ---
//    private void onAddAddress() {
//        Dialog dialog = new Dialog();
//        dialog.setMinWidth("30em");
//        AddressForm addForm = new AddressForm(Mode.ADD, null, addressService, createdAddress -> {
//            if (model.getAddresses() == null) {
//                model.setAddresses(new ArrayList<>());
//            }
//            model.getAddresses().add(createdAddress);
//            addressesGrid.setItems(model.getAddresses());
//            showNotifation(i18n("addressAdded"));
//            dialog.close();
//        });
//        addForm.init(dialog);
//        dialog.add(addForm);
//        dialog.open();
//    }
//
//    private void onEditAddress() {
//        Optional<Address> selectedAddress = addressesGrid.asSingleSelect().getOptionalValue();
//        selectedAddress.ifPresent(addressToEdit -> {
//            Dialog dialog = new Dialog();
//            dialog.setMinWidth("30em");
//            AddressForm addForm = new AddressForm(Mode.EDIT, addressToEdit, addressService, updatedAddress -> {
//                addressesGrid.getDataProvider().refreshItem(updatedAddress);
//                showNotifation(i18n("addressEdited"));
//                dialog.close();
//            });
//            addForm.init(dialog);
//            dialog.add(addForm);
//            dialog.open();
//        });
//    }
//
//    private void onRemoveAddress() {
//        Optional<Address> selectedAddress = addressesGrid.asSingleSelect().getOptionalValue();
//        selectedAddress.ifPresent(addressToRemove -> {
//            MessageDialog.question()
//                    .withTitle(i18n("confirmation"))
//                    .withMessage(i18n("onRemoveAddress"))
//                    .withNoButton(() -> currentDialog.close()) // ZMIANA TUTAJ
//                    .withYesButton(() -> {
//                        if (model.getAddresses() != null) {
//                            model.getAddresses().remove(addressToRemove);
//                        }
//                        addressesGrid.setItems(model.getAddresses());
//                        showNotifation(i18n("addressRemoved"));
//                    })
//                    .show();
//        });
//    }
//
//    // --- Metody do zarządzania zgodami marketingowymi ---
//    private void onAddMarketingConsent() {
//        Dialog dialog = new Dialog();
//        dialog.setMinWidth("30em");
//        MarketingConsentCustomerForm mccForm = new MarketingConsentCustomerForm(Mode.ADD, null, marketingConsentService, createdConsent -> {
//            if (model.getMarketingConsents() == null) {
//                model.setMarketingConsents(new ArrayList<>());
//            }
//            model.getMarketingConsents().add(createdConsent);
//            marketingConsentsGrid.setItems(model.getMarketingConsents());
//            showNotifation(i18n("marketingConsentAdded"));
//            dialog.close();
//        });
//        mccForm.init(dialog);
//        dialog.add(mccForm);
//        dialog.open();
//    }
//
//    private void onEditMarketingConsent() {
//        Optional<MarketingConsentCustomer> selectedConsent = marketingConsentsGrid.asSingleSelect().getOptionalValue();
//        selectedConsent.ifPresent(consentToEdit -> {
//            Dialog dialog = new Dialog();
//            dialog.setMinWidth("30em");
//            MarketingConsentCustomerForm mccForm = new MarketingConsentCustomerForm(Mode.EDIT, consentToEdit, marketingConsentService, updatedConsent -> {
//                marketingConsentsGrid.getDataProvider().refreshItem(updatedConsent);
//                showNotifation(i18n("marketingConsentEdited"));
//                dialog.close();
//            });
//            mccForm.init(dialog);
//            dialog.add(mccForm);
//            dialog.open();
//        });
//    }
//
//    private void onRemoveMarketingConsent() {
//        Optional<MarketingConsentCustomer> selectedConsent = marketingConsentsGrid.asSingleSelect().getOptionalValue();
//        selectedConsent.ifPresent(consentToRemove -> {
//            MessageDialog.question()
//                    .withTitle(i18n("confirmation"))
//                    .withMessage(i18n("onRemoveMarketingConsent"))
//                    .withNoButton(() -> currentDialog.close()) // ZMIANA TUTAJ
//                    .withYesButton(() -> {
//                        if (model.getMarketingConsents() != null) {
//                            model.getMarketingConsents().remove(consentToRemove);
//                        }
//                        marketingConsentsGrid.setItems(model.getMarketingConsents());
//                        showNotifation(i18n("marketingConsentRemoved"));
//                    })
//                    .show();
//        });
//    }
//}
