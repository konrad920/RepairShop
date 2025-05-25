package edu.wsiiz.repairshop.payments.ui.settlement;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.data.binder.Binder;
import edu.wsiiz.repairshop.payments.domain.invoice.Invoice;

import edu.wsiiz.repairshop.payments.domain.settlement.PaymentMethod;
import edu.wsiiz.repairshop.payments.domain.settlement.Settlement;

import java.util.List;
import java.util.function.Consumer;

public class SettlementForm extends Composite<FormLayout> {

    private final ComboBox<Invoice> invoiceBox = new ComboBox<>("Invoice");
    private final DatePicker datePicker = new DatePicker("Date");
    private final BigDecimalField amountField = new BigDecimalField("Amount");
    private final ComboBox<PaymentMethod> methodBox = new ComboBox<>("Payment Method");

    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");

    private final Binder<Settlement> binder = new Binder<>(Settlement.class);
    private Settlement settlement;

    private Consumer<Settlement> onSave;
    private Runnable onCancel;

    public SettlementForm(List<Invoice> invoices) {
        invoiceBox.setItems(invoices);
        invoiceBox.setItemLabelGenerator(invoice -> "Invoice #" + invoice.getId());
        methodBox.setItems(PaymentMethod.values());

        getContent().add(invoiceBox, datePicker, amountField, methodBox, createButtons());

        binder.bindInstanceFields(this);
    }

    private HorizontalLayout createButtons() {
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        saveButton.addClickListener(e -> validateAndSave());
        cancelButton.addClickListener(e -> {
            if (onCancel != null) onCancel.run();
        });

        return new HorizontalLayout(saveButton, cancelButton);
    }

    private void validateAndSave() {
        if (binder.validate().isOk()) {
            if (onSave != null) {
                onSave.accept(binder.getBean());
            }
        } else {
            Notification.show("Please fix the errors before saving.", 3000, Notification.Position.MIDDLE);
        }
    }

    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
        binder.setBean(settlement);
    }

    public void setOnSave(Consumer<Settlement> onSave) {
        this.onSave = onSave;
    }

    public void setOnCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }
}
