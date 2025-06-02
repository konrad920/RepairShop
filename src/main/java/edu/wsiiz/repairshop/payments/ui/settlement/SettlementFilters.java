package edu.wsiiz.repairshop.payments.ui.settlement;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import edu.wsiiz.repairshop.foundation.ui.view.ListView.Filters;
import edu.wsiiz.repairshop.payments.domain.settlement.PaymentMethod;
import edu.wsiiz.repairshop.payments.domain.settlement.Settlement;

import java.time.LocalDate;
import java.util.function.Consumer;

public class SettlementFilters extends Filters<Settlement> {

    private final DatePicker fromDate = new DatePicker("From Payment Date");
    private final DatePicker toDate = new DatePicker("To Payment Date");
    private final ComboBox<PaymentMethod> paymentMethodCombo = new ComboBox<>("Payment Method");

    private final Button applyButton = new Button("Apply");
    private final Button clearButton = new Button("Clear");

    private Consumer<SettlementFilterValues> listener;

    public SettlementFilters(Runnable onSearch) {
        super(onSearch);

        paymentMethodCombo.setItems(PaymentMethod.values());
        paymentMethodCombo.setClearButtonVisible(true);

        applyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clearButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        applyButton.addClickListener(e -> fireFilterEvent());
        clearButton.addClickListener(e -> clearFilters());

        HorizontalLayout layout = new HorizontalLayout(fromDate, toDate, paymentMethodCombo, applyButton, clearButton);
        getContent().add(layout);
    }

    public void setFilterListener(Consumer<SettlementFilterValues> listener) {
        this.listener = listener;
    }

    private void fireFilterEvent() {
        if (listener != null) {
            listener.accept(new SettlementFilterValues(
                    fromDate.getValue(),
                    toDate.getValue(),
                    paymentMethodCombo.getValue()
            ));
        }
    }

    private void clearFilters() {
        fromDate.clear();
        toDate.clear();
        paymentMethodCombo.clear();
        fireFilterEvent();
    }

    @Override
    protected void setupFilters() {}

    @Override
    protected void onReset() {}

    public record SettlementFilterValues(
            LocalDate fromPaymentDate,
            LocalDate toPaymentDate,
            PaymentMethod paymentMethod
    ) {}
}
