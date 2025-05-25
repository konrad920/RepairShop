package edu.wsiiz.repairshop.storage.ui.storage;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import edu.wsiiz.repairshop.storage.domain.storage.Storage;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class StorageFilters extends ListView.Filters<Storage> {

    static final String ADDRESS = "address";

    TextField addressField = new TextField("Adres");

    public StorageFilters(Runnable onSearch) {
        super(onSearch);
        setupFilters();
    }

    @Override
    protected void setupFilters() {
        addressField.setPlaceholder("Wpisz adres magazynu...");
        addressField.setWidthFull();

        HorizontalLayout filtersLayout = new HorizontalLayout(addressField);
        filtersLayout.setWidthFull();

        getContent().add(filtersLayout);
    }

    @Override
    protected void onReset() {
        addressField.clear();
        triggerSearch();
    }

    @Override
    public Predicate toPredicate(Root<Storage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        String addressValue = addressField.getValue();
        if (addressValue != null && !addressValue.trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get(ADDRESS)), "%" + addressValue.toLowerCase() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}