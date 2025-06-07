package edu.wsiiz.repairshop.payments.ui.settlement;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.payments.application.SettlementService;
import edu.wsiiz.repairshop.payments.domain.settlement.Settlement;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Route("settlements")
@PageTitle("Settlements")
@RolesAllowed("ADMIN")
public class SettlementListView extends VerticalLayout {

    private final SettlementService settlementService;
    private final Grid<Settlement> grid = new Grid<>(Settlement.class);
    private final SettlementFilters filters;
    private final SettlementForm form = new SettlementForm();

    public SettlementListView(SettlementService settlementService) {
        this.settlementService = settlementService;

        filters = new SettlementFilters(this::refreshGrid);
        filters.setFilterListener(this::applyFilters);

        form.setVisible(false);
        form.setOnSave(this::saveSettlement);
        form.setOnCancel(() -> form.setVisible(false));

        Button addNewBtn = new Button("Add New Settlement");
        addNewBtn.addClickListener(e -> {
            form.setSettlement(new Settlement());
            form.setVisible(true);
        });

        grid.setColumns("paymentDate", "paymentMethod", "reminderSent", "reminderDate");
        grid.asSingleSelect().addValueChangeListener(e -> {
            if (e.getValue() != null) {
                form.setSettlement(e.getValue());
                form.setVisible(true);
            } else {
                form.setVisible(false);
            }
        });

        add(filters, addNewBtn, grid, form);

        refreshGrid();
    }

    private void refreshGrid() {
        List<Settlement> all = settlementService.findAll();
        grid.setItems(all);
    }

    private void applyFilters(SettlementFilters.SettlementFilterValues filterValues) {
        // filtruje dane z serwisu według parametrów z filtra

        List<Settlement> filtered = settlementService.findAll().stream()
                .filter(s -> {
                    boolean matches = true;
                    if (filterValues.fromPaymentDate() != null) {
                        matches &= !s.getPaymentDate().isBefore(filterValues.fromPaymentDate());
                    }
                    if (filterValues.toPaymentDate() != null) {
                        matches &= !s.getPaymentDate().isAfter(filterValues.toPaymentDate());
                    }
                    if (filterValues.paymentMethod() != null) {
                        matches &= s.getPaymentMethod() == filterValues.paymentMethod();
                    }
                    return matches;
                })
                .toList();
        grid.setItems(filtered);
    }

    private void saveSettlement(Settlement settlement) {
        settlementService.save(settlement);
        form.setVisible(false);
        refreshGrid();
    }
}
