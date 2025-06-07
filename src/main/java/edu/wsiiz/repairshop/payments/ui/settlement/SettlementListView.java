package edu.wsiiz.repairshop.payments.ui.settlement;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.payments.application.SettlementService;
import edu.wsiiz.repairshop.payments.domain.settlement.Settlement;
import jakarta.annotation.security.RolesAllowed;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.Consumer;

/**
 * Widok listy rozliczeń. Pozwala na wyświetlenie, filtrowanie,
 * tworzenie, edycję i usuwanie obiektów Settlement.
 */
@Route("settlements")
@PageTitle("Rozliczenia")
@RolesAllowed("ADMIN")
public class SettlementListView extends ListView<Settlement> {

    // Serwis, który obsługuje operacje CRUD na encji Settlement
    private final SettlementService settlementService;

    /**
     * Konstruktor wstrzykuje SettlementService i konfiguruje widok.
     * - Ustawia filtry (SettlementFilters)
     * - Buduje układ (nagłówek, grid, przyciski) wywołując setupLayout()
     */
    public SettlementListView(SettlementService settlementService) {
        this.settlementService = settlementService;

        // Dodajemy filtr, który przy zmianie parametrów wywoła refreshGrid()
        setFilters(new SettlementFilters(this::refreshGrid));
        // setupLayout() wywoła: setupGrid(), doda przyciski CRUD, grid, itp.
        setupLayout();
    }

    /**
     * Tutaj definiujemy kolumny, które mają się pokazać w tabeli:
     * - ID rozliczenia
     * - Data płatności
     * - Metoda płatności (z tłumaczeniem przez i18n)
     * - Flaga przypomnienia („Tak”/„Nie”)
     *
     * Metoda jest wywoływana w ramach setupLayout() dostarczonego przez ListView.
     */
    @Override
    protected void setupGrid() {
        grid.addColumn(Settlement::getId)
                .setHeader(i18n("id"))
                .setSortable(true);

        grid.addColumn(Settlement::getPaymentDate)
                .setHeader(i18n("paymentDate"))
                .setSortable(true);

        grid.addColumn(s -> i18n(s.getPaymentMethod().name()))
                .setHeader(i18n("paymentMethod"))
                .setSortable(true);

        grid.addColumn(s -> s.isReminderSent() ? i18n("yes") : i18n("no"))
                .setHeader(i18n("reminderSent"))
                .setSortable(true);
    }

    /**
     * Zwraca funkcję, która dostarczy formularz do tworzenia/edycji rozliczenia.
     * ListView automatycznie:
     * - Włoży ten form do Dialogu
     * - Przekazuje tryb (Mode.CREATE lub Mode.EDIT)
     * - Przekazuje callback onSave, który po zapisie uruchomi refreshGrid()
     */
    @Override
    protected TriFunction<Settlement, Mode, Consumer<Settlement>, BaseForm<Settlement>> detailsFormSupplier() {
        return (settlement, mode, onSave) ->
                new SettlementForm(mode, settlement, settlementService, onSave);
    }

    /**
     * Obsługa usuwania rekordu. ListView wywoła tę metodę,
     * gdy użytkownik potwierdzi chęć usunięcia w dialogu.
     * Po skasowaniu odświeżamy grid.
     */
    @Override
    protected void onDelete(Settlement settlement) {
        settlementService.delete(settlement.getId());
        refreshGrid();
    }
}