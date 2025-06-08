package edu.wsiiz.repairshop.payments.ui.settlement;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.combobox.ComboBox;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.payments.application.SettlementService;
import edu.wsiiz.repairshop.payments.domain.settlement.PaymentMethod;
import edu.wsiiz.repairshop.payments.domain.settlement.Settlement;

import java.util.function.Consumer;

/**
 * Formularz do tworzenia/edycji Settlement.
 * Dziedziczy po BaseForm<Settlement> – dzięki temu mamy obsługę trybów CREATE/EDIT/VIEW,
 * walidację i przyciski Zapisz/Zamknij.
 */
public class SettlementForm extends BaseForm<Settlement> {

    DatePicker paymentDate = new DatePicker(i18n("paymentDate"));
    ComboBox<PaymentMethod> paymentMethod = new ComboBox<>(i18n("paymentMethod"));
    Checkbox reminderSent = new Checkbox(i18n("reminderSent"));
    DatePicker reminderDate = new DatePicker(i18n("reminderDate"));

    /**
     * @param mode      Tryb formularza (CREATE / EDIT / VIEW)
     * @param item      Obiekt Settlement (nowy lub ładowany z bazy)
     * @param service   Serwis obsługujący logikę zapisu
     * @param afterSave Akcja do wywołania po zapisaniu (np. odświeżenie grid)
     */
    public SettlementForm(Mode mode, Settlement item, SettlementService service, Consumer<Settlement> afterSave) {
        super(
                mode,
                () -> new Settlement(),                            // dostawca nowego obiektu
                () -> service.findById(item.getId())               // reader: pobranie z DB dla EDIT/VIEW
                        .orElseThrow(() -> new IllegalArgumentException("Settlement nie znaleziony")),
                service::save,                                     // writer: funkcja zapisu
                afterSave                                          // co robić po zapisie
        );
    }

    @Override
    public void setupFields() {
        // Ustawienia komponentów
        paymentMethod.setItems(PaymentMethod.values());
        paymentMethod.setItemLabelGenerator(this::i18n); // tłumaczenie enumów
        reminderDate.setEnabled(false);                   // domyślnie nieaktywne

        // Kiedy zaznaczymy "reminderSent", aktywujemy DatePicker
        reminderSent.addValueChangeListener(e -> {
            boolean sent = e.getValue();
            reminderDate.setEnabled(sent);
            if (!sent) {
                reminderDate.clear();
            }
        });

        // Dodajemy wszystkie pola do layoutu formularza
        layout.add(paymentDate, paymentMethod, reminderSent, reminderDate);
    }
}