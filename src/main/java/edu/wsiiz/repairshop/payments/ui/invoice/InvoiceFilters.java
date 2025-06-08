package edu.wsiiz.repairshop.payments.ui.invoice;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import edu.wsiiz.repairshop.communication.domain.contact.ContactStatus;
import edu.wsiiz.repairshop.foundation.ui.view.ListView.Filters;
import edu.wsiiz.repairshop.payments.domain.invoice.Invoice;
import edu.wsiiz.repairshop.payments.domain.invoice.InvoiceStatus;

import java.util.function.Consumer;

public class InvoiceFilters extends Filters<Invoice> {

    static final String STATUS = "status";
     TextField clientField = new TextField(i18n("client"));
     DatePicker fromDate = new DatePicker(i18n("From"));
     DatePicker toDate = new DatePicker(i18n("To"));
     ComboBox<InvoiceStatus> statusBox = new ComboBox<>(i18n("Status"));

    private final Button applyButton = new Button(i18n("Apply"));
    private final Button clearButton = new Button(i18n("Clear"));

    private Consumer<InvoiceFilterValues> listener;

    public InvoiceFilters(Runnable onSearch) {
        super(onSearch);
        setupFilters();
        statusBox.setItems(InvoiceStatus.values());
        statusBox.setClearButtonVisible(true);

        clientField.setPlaceholder("Client name...");
        fromDate.setPlaceholder("Start date");
        toDate.setPlaceholder("End date");

        applyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clearButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        applyButton.addClickListener(e -> fireFilterEvent());
        clearButton.addClickListener(e -> clearFilters());

        getContent().add(clientField, fromDate, toDate, statusBox, applyButton, clearButton);

    }

    public void setFilterListener(Consumer<InvoiceFilterValues> listener) {
        this.listener = listener;
    }

    private void fireFilterEvent() {
        if (listener != null) {
            listener.accept(new InvoiceFilterValues(
                    clientField.getValue(),
                    fromDate.getValue(),
                    toDate.getValue(),
                    statusBox.getValue()
            ));
        }
    }

    private void clearFilters() {
        clientField.clear();
        fromDate.clear();
        toDate.clear();
        statusBox.clear();
        fireFilterEvent();
    }


    @Override
    protected void onReset() {

    }

    public record InvoiceFilterValues(
            String clientName,
            java.time.LocalDate from,
            java.time.LocalDate to,
            InvoiceStatus status
    ) {}
    @Override
    protected void setupFilters() {

        statusBox.setItems(InvoiceStatus.values());
        statusBox.setItemLabelGenerator(this::i18n);

        HorizontalLayout filtersLayout = new HorizontalLayout(statusBox);
        filtersLayout.setWidthFull();

        getContent().add(filtersLayout);
    }
}
