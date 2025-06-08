package edu.wsiiz.repairshop.storage.ui.delivery;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.storage.domain.storage.Delivery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DeliveryFilters extends ListView.Filters<Delivery> {

    static final String NAME = "deliveryDate";

    DatePicker deliveryDateFromField = new DatePicker(i18n("deliveryDateFrom"));
    DatePicker deliveryDateToField = new DatePicker(i18n("deliveryDateTo"));

    public DeliveryFilters(Runnable onSearch) {
        super(onSearch);
        setupFilters();
    }

    @Override
    protected void setupFilters() {
        deliveryDateFromField.setPlaceholder("Wyznacz datę od...");
        deliveryDateFromField.setWidthFull();
        deliveryDateToField.setPlaceholder("Wyznacz datę do...");
        deliveryDateToField.setWidthFull();

        HorizontalLayout filtersLayout = new HorizontalLayout(deliveryDateFromField, deliveryDateToField);
        filtersLayout.setWidthFull();

        getContent().add(filtersLayout);
    }

    @Override
    protected void onReset() {
        deliveryDateFromField.clear();
        deliveryDateToField.clear();
        triggerSearch();
    }

    @Override
    public Predicate toPredicate(Root<Delivery> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        LocalDate deliveryDateFromValue = deliveryDateFromField.getValue();
        LocalDate deliveryDateToValue = deliveryDateToField.getValue();
        if (deliveryDateFromValue != null && deliveryDateToValue != null) {
            predicates.add(cb.between(root.get(NAME), deliveryDateFromValue, deliveryDateToValue));
        } else if (deliveryDateFromValue != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(NAME), deliveryDateFromValue));
        } else if (deliveryDateToValue != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(NAME), deliveryDateToValue));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}