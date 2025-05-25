package edu.wsiiz.repairshop.payments.ui.invoice;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import edu.wsiiz.repairshop.payments.domain.invoice.Invoice;
import edu.wsiiz.repairshop.payments.domain.invoice.InvoiceStatus;
import edu.wsiiz.repairshop.foundation.ui.view.ListView.Filters;


import java.util.function.Consumer;

public class InvoiceFilters extends Filters<Invoice> {
    private final TextField clientField = new TextField("Client");
    private final DatePicker fromDate = new DatePicker("From");
    private final DatePicker toDate = new DatePicker("To");
    private final ComboBox<InvoiceStatus> statusBox = new ComboBox<>("Status");

    private final Button applyButton = new Button("Apply");
    private final Button clearButton = new Button("Clear");

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

//    public InvoiceFilters(Runnable onSearch) {
//        super(onSearch);
//        setupFilters();
//    }

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
    protected void setupFilters() {

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
}
