package edu.wsiiz.repairshop.storage.ui.resource;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import edu.wsiiz.repairshop.storage.domain.storage.Manufacturer;
import edu.wsiiz.repairshop.storage.domain.storage.Resource;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.storage.domain.storage.Storage;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class ResourceFilters extends ListView.Filters<Resource> {

    static final String MANUFACTURER_ID = "manufacturer_id";
    static final String STORAGE_ID = "storage_id";

    ComboBox<Manufacturer> manufactureField = new ComboBox<Manufacturer>("Producent");
    ComboBox<Storage> storageField = new ComboBox<Storage>("Magazyn");

    public ResourceFilters(Runnable onSearch, List<Manufacturer> manufacturerList, List<Storage> storageList) {
        super(onSearch);
        setupFilters();

        manufactureField.setItems(manufacturerList);
        manufactureField.setItemLabelGenerator(Manufacturer::getName);
        storageField.setItems(storageList);
        storageField.setItemLabelGenerator(Storage::getAddress);
    }

    @Override
    protected void setupFilters() {
        manufactureField.setPlaceholder("Wybierz producenta...");
        manufactureField.setWidthFull();
        storageField.setPlaceholder("Wybierz magazyn...");
        storageField.setWidthFull();

        HorizontalLayout filtersLayout = new HorizontalLayout(storageField, manufactureField);
        filtersLayout.setWidthFull();

        getContent().add(filtersLayout);
    }

    @Override
    protected void onReset() {
        storageField.clear();
        manufactureField.clear();
        triggerSearch();
    }

    @Override
    public Predicate toPredicate(Root<Resource> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        Manufacturer manufacturerValue = manufactureField.getValue();
        Storage storageValue = storageField.getValue();
        if (manufacturerValue != null) {
            predicates.add(cb.equal(cb.lower(root.get(MANUFACTURER_ID)), manufacturerValue.getManufacturerId()));
        }
        if (storageValue != null) {
            predicates.add(cb.equal(cb.lower(root.get(STORAGE_ID)), storageValue.getStorageId()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}