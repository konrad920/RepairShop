package edu.wsiiz.repairshop.storage.ui.delivery;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.storage.domain.storage.Delivery;
import edu.wsiiz.repairshop.storage.domain.storage.DeliveryPart;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DeliveryPartFilters extends ListView.Filters<DeliveryPart> {

    static final String NAME = "quantity";

    NumberField quantityFromField = new NumberField(i18n("quantityFrom"));
    NumberField quantityToField = new NumberField(i18n("quantityTo"));

    public DeliveryPartFilters(Runnable onSearch) {
        super(onSearch);
        setupFilters();
    }

    @Override
    protected void setupFilters() {
        quantityFromField.setPlaceholder("Większe od...");
        quantityFromField.setWidthFull();
        quantityToField.setPlaceholder("Mniejsze od...");
        quantityToField.setWidthFull();

        HorizontalLayout filtersLayout = new HorizontalLayout(quantityFromField, quantityToField);
        filtersLayout.setWidthFull();

        getContent().add(filtersLayout);
    }

    @Override
    protected void onReset() {
        quantityToField.clear();
        quantityFromField.clear();
        triggerSearch();
    }

    @Override
    public Predicate toPredicate(Root<DeliveryPart> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        Number quantityFromValue = quantityFromField.getValue();
        Number quantityToValue = quantityToField.getValue();

        if (quantityFromValue != null) {
            predicates.add(cb.gt(root.get(NAME), quantityFromValue));
        }

        if (quantityToValue != null) {
            predicates.add(cb.lt(root.get(NAME), quantityToValue));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}