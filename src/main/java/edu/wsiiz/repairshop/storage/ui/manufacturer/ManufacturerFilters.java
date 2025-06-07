package edu.wsiiz.repairshop.storage.ui.manufacturer;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import edu.wsiiz.repairshop.storage.domain.storage.Manufacturer;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class ManufacturerFilters extends ListView.Filters<Manufacturer> {

    static final String NAME = "name";

    TextField nameField = new TextField("Nazwa");

    public ManufacturerFilters(Runnable onSearch) {
        super(onSearch);
        setupFilters();
    }

    @Override
    protected void setupFilters() {
        nameField.setPlaceholder("Wpisz nazwę magazynu...");
        nameField.setWidthFull();

        HorizontalLayout filtersLayout = new HorizontalLayout(nameField);
        filtersLayout.setWidthFull();

        getContent().add(filtersLayout);
    }

    @Override
    protected void onReset() {
        nameField.clear();
        triggerSearch();
    }

    @Override
    public Predicate toPredicate(Root<Manufacturer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        String nameValue = nameField.getValue();
        if (nameValue != null && !nameValue.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get(NAME)), "%" + nameValue.toLowerCase() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}