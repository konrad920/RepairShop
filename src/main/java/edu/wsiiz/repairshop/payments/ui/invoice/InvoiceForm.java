package edu.wsiiz.repairshop.payments.ui.invoice;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.binder.Binder;
import edu.wsiiz.repairshop.payments.domain.invoice.Invoice;
import edu.wsiiz.repairshop.payments.domain.invoice.InvoiceStatus;


import java.util.function.Consumer;


public class InvoiceForm extends Composite<FormLayout> {

    private final TextField clientField = new TextField("Client");
    private final DatePicker issueDate = new DatePicker("Issue Date");
    private final ComboBox<InvoiceStatus> statusBox = new ComboBox<>("Status");
    private final BigDecimalField amountField = new BigDecimalField("Amount");

    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");

    private final Binder<Invoice> binder = new Binder<>(Invoice.class);
    private Invoice invoice;

    private Consumer<Invoice> onSave;
    private Runnable onCancel;

    public InvoiceForm() {
        statusBox.setItems(InvoiceStatus.values());

        getContent().add(clientField, issueDate, statusBox, amountField, createButtons());

        binder.bindInstanceFields(this);
        binder.setBean(new Invoice());
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
            Notification.show("Please fix errors before saving.", 3000, Notification.Position.MIDDLE);
        }
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
        binder.readBean(invoice);
    }

    public void setOnSave(Consumer<Invoice> onSave) {
        this.onSave = onSave;
    }

    public void setOnCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }
}
