package edu.wsiiz.repairshop.payments.ui.settlement;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import edu.wsiiz.repairshop.foundation.ui.view.ListView.Filters;
import edu.wsiiz.repairshop.payments.domain.settlement.PaymentMethod;
import edu.wsiiz.repairshop.payments.domain.settlement.Settlement;

import java.time.LocalDate;
import java.util.function.Consumer;

/**
 * Klasa definiuje zestaw filtrów do widoku SettlementListView.
 * Dziedziczy po Filters<Settlement>, co zapewnia obsługę układu i przycisku reset.
 */
public class SettlementFilters extends Filters<Settlement> {

    // Data początkowa do filtrowania (pole "From")
    private final DatePicker fromDate = new DatePicker("From");

    // Data końcowa do filtrowania (pole "To")
    private final DatePicker toDate = new DatePicker("To");

    // Wybór metody płatności (Enum PaymentMethod)
    private final ComboBox<PaymentMethod> methodBox = new ComboBox<>("Payment Method");

    // Checkbox: czy wysłano przypomnienie
    private final Checkbox reminderSent = new Checkbox("Reminder Sent");

    // Przycisk zastosowania filtrów
    private final Button applyButton = new Button("Apply");

    // Przycisk wyczyszczenia wszystkich filtrów
    private final Button clearButton = new Button("Clear");

    // Listener, który przyjmuje obiekt SettlementFilterValues
    private Consumer<SettlementFilterValues> listener;

    /**
     * @param onSearch - Runnable wywoływany po naciśnięciu wbudowanego przycisku reset.
     *                  Jest przekazywany z ListView, by odświeżyć grid.
     */
    public SettlementFilters(Runnable onSearch) {
        super(onSearch);
        setupFilters();

        // Ustawiamy wszystkie dostępne wartości enuma w ComboBoxie
        methodBox.setItems(PaymentMethod.values());
        methodBox.setClearButtonVisible(true);

        // Placeholdery, aby użytkownik wiedział, co wpisać
        fromDate.setPlaceholder("Start date");
        toDate.setPlaceholder("End date");

        // Stylizujemy przyciski: Apply ma wygląd primary, Clear ma tertiary
        applyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clearButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        // Kliknięcie Apply wywołuje fireFilterEvent(), Clear czyści wszystkie pola i też uruchamia fireFilterEvent()
        applyButton.addClickListener(e -> fireFilterEvent());
        clearButton.addClickListener(e -> clearFilters());

        // Dodajemy komponenty do layoutu filtrów (pionowy układ)
        getContent().add(fromDate, toDate, methodBox, reminderSent, applyButton, clearButton);
    }

    /**
     * Rejestruje listener, który otrzyma obiekt z wartościami filtrów.
     * W ListView ustawiamy, że po otrzymaniu wartości ma się odświeżyć grid (applyFilters).
     */
    public void setFilterListener(Consumer<SettlementFilterValues> listener) {
        this.listener = listener;
    }

    /**
     * Tworzy obiekt SettlementFilterValues z aktualnymi wartościami pól
     * i przekazuje go do listenera (np. do metody applyFilters w SettlementListView).
     */
    private void fireFilterEvent() {
        if (listener != null) {
            listener.accept(new SettlementFilterValues(
                    fromDate.getValue(),
                    toDate.getValue(),
                    methodBox.getValue(),
                    reminderSent.getValue()
            ));
        }
    }

    /** Czyści wszystkie pola formularza i ponownie wywołuje fireFilterEvent() */
    private void clearFilters() {
        fromDate.clear();
        toDate.clear();
        methodBox.clear();
        reminderSent.clear();
        fireFilterEvent();
    }

    @Override
    protected void setupFilters() {
    }

    @Override
    protected void onReset() {
        // Domyślne zachowanie przy „Reset All” jest już realizowane przez clearFilters(),
        // bo Filters.invokeReset() i tak wywoła onReset(), a potem run() z onSearch.
    }

    /**
     * Rekord przechowujący wartości wszystkich filtrów w jednym obiekcie.
     * Ułatwia przekazanie ich do repozytorium czy serwisu.
     */
    public record SettlementFilterValues(
            LocalDate from,
            LocalDate to,
            PaymentMethod method,
            Boolean reminderSent
    ) {}
}