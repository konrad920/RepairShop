package edu.wsiiz.repairshop.payments.ui.settlement;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import edu.wsiiz.repairshop.payments.domain.settlement.PaymentMethod;
import edu.wsiiz.repairshop.payments.domain.settlement.Reminder;
import edu.wsiiz.repairshop.payments.domain.settlement.Settlement;

import java.util.function.Consumer;

public class SettlementForm extends FormLayout {

    private final DatePicker paymentDate = new DatePicker("Payment Date");
    private final ComboBox<PaymentMethod> paymentMethodCombo = new ComboBox<>("Payment Method");
    private final TextField reminderEmail = new TextField("Reminder Email");
    private final TextArea reminderMessage = new TextArea("Reminder Message");
    private final DatePicker reminderSentDate = new DatePicker("Reminder Sent Date");

    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");

    private final Binder<Settlement> binder = new Binder<>(Settlement.class);

    private Consumer<Settlement> onSave;
    private Runnable onCancel;

    public SettlementForm() {
        setWidthFull();
        paymentMethodCombo.setItems(PaymentMethod.values());

        reminderMessage.setHeight("100px");

        add(paymentDate, paymentMethodCombo, reminderEmail, reminderMessage, reminderSentDate, saveButton, cancelButton);

        binder.forField(paymentDate)
                .asRequired("Payment date is required")
                .bind(Settlement::getPaymentDate, Settlement::setPaymentDate);

        binder.forField(paymentMethodCombo)
                .asRequired("Payment method is required")
                .bind(Settlement::getPaymentMethod, Settlement::setPaymentMethod);

        // Bind embedded Reminder fields with null checks
        binder.forField(reminderEmail)
                .bind(
                        settlement -> {
                            Reminder r = settlement.getReminder();
                            return r != null ? r.getEmail() : null;
                        },
                        (settlement, value) -> {
                            if (settlement.getReminder() == null) {
                                settlement.setReminder(new Reminder());
                            }
                            settlement.getReminder().setEmail(value);
                        });

        binder.forField(reminderMessage)
                .bind(
                        settlement -> {
                            Reminder r = settlement.getReminder();
                            return r != null ? r.getMessage() : null;
                        },
                        (settlement, value) -> {
                            if (settlement.getReminder() == null) {
                                settlement.setReminder(new Reminder());
                            }
                            settlement.getReminder().setMessage(value);
                        });

        binder.forField(reminderSentDate)
                .bind(
                        settlement -> {
                            Reminder r = settlement.getReminder();
                            return r != null ? r.getSentDate() : null;
                        },
                        (settlement, value) -> {
                            if (settlement.getReminder() == null) {
                                settlement.setReminder(new Reminder());
                            }
                            settlement.getReminder().setSentDate(value);
                        });

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        saveButton.addClickListener(e -> save());
        cancelButton.addClickListener(e -> {
            if (onCancel != null) onCancel.run();
        });
    }

    private void save() {
        if (binder.validate().isOk()) {
            if (onSave != null) {
                onSave.accept(binder.getBean());
            }
        } else {
            Notification.show("Please fix the errors before saving.");
        }
    }

    public void setSettlement(Settlement settlement) {
        binder.setBean(settlement);
    }

    public void setOnSave(Consumer<Settlement> onSave) {
        this.onSave = onSave;
    }

    public void setOnCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }
}

