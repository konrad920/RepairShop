package edu.wsiiz.repairshop.payments.ui.invoice;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.payments.application.InvoiceService;
import edu.wsiiz.repairshop.payments.domain.invoice.Invoice;
import edu.wsiiz.repairshop.payments.domain.invoice.InvoiceStatus;

import java.util.function.Consumer;

/**
 * Formularz do tworzenia i edycji faktur.
 * Dziedziczy po BaseForm<Invoice> – dzięki temu obsługuje tryby CREATE/EDIT/VIEW,
 * bindowanie pól oraz obsługę zapisu i zamykania formularza.
 */
public class InvoiceForm extends BaseForm<Invoice> {

    // Pola formularza
    TextField invoiceNumber = new TextField(i18n("invoiceNumber")); // Numer faktury
    ComboBox<InvoiceStatus> status = new ComboBox<>(i18n("status")); // Status faktury (PENDING, PAID, CANCELED)
    DatePicker issueDate = new DatePicker(i18n("issueDate")); // Data wystawienia

    /**
     * Konstruktor formularza
     *
     * @param mode Tryb działania (CREATE / EDIT)
     * @param item Obiekt faktury, który edytujemy (lub pusty przy CREATE)
     * @param service Serwis obsługujący logikę zapisu
     * @param afterSave Akcja do wykonania po zapisaniu (np. odświeżenie widoku)
     */
    public InvoiceForm(Mode mode, Invoice item, InvoiceService service, Consumer<Invoice> afterSave) {
        super(mode,
                () -> new Invoice(),                 // Dostawca nowego obiektu (dla CREATE)
                () -> service.findById(item.getId()) // Ponowne pobranie obiektu z bazy (dla EDIT)
                        .orElseThrow(() -> new IllegalArgumentException("Faktura nie znaleziona")),
                service::save,                       // Funkcja zapisu faktury
                afterSave);                          // Co zrobić po zapisie (np. reload grid)
    }

    /**
     * Budowanie pól formularza i ich konfiguracja
     */
    @Override
    public void setupFields() {
        // Lista statusów jako ComboBox
        status.setItems(InvoiceStatus.values());
        status.setItemLabelGenerator(this::i18n); // tłumaczenie nazw statusów

        // Dodanie wszystkich pól do layoutu formularza
        layout.add(invoiceNumber, status, issueDate);
    }
}