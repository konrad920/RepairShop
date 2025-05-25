package edu.wsiiz.repairshop.payments.ui.settlement;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import edu.wsiiz.repairshop.payments.domain.settlement.PaymentMethod;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;

import java.time.LocalDate;

public class SettlementFilters  extends Composite<HorizontalLayout> {

    private final TextField invoiceNumberField = new TextField("Invoice Number");
    private final DatePicker fromDate = new DatePicker("From");
    private final DatePicker toDate = new DatePicker("To");
    private final ComboBox<PaymentMethod> paymentMethodBox = new ComboBox<>("Payment Method");

    private final Button applyButton = new Button("Apply");
    private final Button clearButton = new Button("Clear");

    private MessagePassingQueue.Consumer<FilterValues> listener;

    public SettlementFilters() {
        invoiceNumberField.setPlaceholder("e.g. INV-2024-01");
        fromDate.setPlaceholder("Start date");
        toDate.setPlaceholder("End date");
        paymentMethodBox.setItems(PaymentMethod.values());
        paymentMethodBox.setClearButtonVisible(true);

        applyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clearButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        applyButton.addClickListener(e -> fireEvent());
        clearButton.addClickListener(e -> clearFilters());

        getContent().add(invoiceNumberField, fromDate, toDate, paymentMethodBox, applyButton, clearButton);
    }

    public void setFilterListener(MessagePassingQueue.Consumer<FilterValues> listener) {
        this.listener = listener;
    }

    private void fireEvent() {
        if (listener != null) {
            listener.accept(new FilterValues(
                    invoiceNumberField.getValue(),
                    fromDate.getValue(),
                    toDate.getValue(),
                    paymentMethodBox.getValue()
            ));
        }
    }

    private void clearFilters() {
        invoiceNumberField.clear();
        fromDate.clear();
        toDate.clear();
        paymentMethodBox.clear();
        fireEvent();
    }

    public record FilterValues(
            String invoiceNumber,
            LocalDate from,
            LocalDate to,
            PaymentMethod paymentMethod
    ) {}
}


