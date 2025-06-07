package edu.wsiiz.repairshop.storage.ui.resource;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import edu.wsiiz.repairshop.storage.application.ResourceService;
import edu.wsiiz.repairshop.storage.domain.storage.*;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;

import java.util.List;
import java.util.function.Consumer;

public class ResourceForm extends BaseForm<Resource> {

    TextField nameField = new TextField("Nazwa zasobu");
    ComboBox<Storage> storageComboBox = new ComboBox<>("Magazyn");
    ComboBox<Manufacturer> manufacturerComboBox = new ComboBox<>("Producent");
    ComboBox<ResourceType> unitComboBox = new ComboBox<>("Jednostka");
    NumberField netPriceField = new NumberField("Cena netto");
    DatePicker productionDateField = new DatePicker("Data produkcji");
    DatePicker expirationDateField = new DatePicker("Data ważności");


    public ResourceForm(Mode mode, Resource item, ResourceService resourceService, List<Manufacturer> manufacturers, List<Storage> storages, Consumer<Resource> afterSave) {
        super(mode,
                () -> Resource.builder().quantity(0).build(),
                () -> resourceService.get(item.getResourceId()),
                resourceService::save,
                afterSave);

        binder.forField(nameField)
                .asRequired("Nazwa zasobu jest wymagana!")
                .bind(Resource::getName, Resource::setName);
        binder.forField(manufacturerComboBox)
                .bind(Resource::getManufacturer, Resource::setManufacturer);
        binder.forField(storageComboBox)
                .bind(Resource::getStorage, Resource::setStorage);
        binder.forField(unitComboBox)
                .asRequired("Jednostka zasobu jest wymagana!")
                .bind(Resource::getUnit, Resource::setUnit);
        binder.forField(netPriceField)
                .asRequired("Jednostka zasobu jest wymagana!")
                .bind(Resource::getNetPrice, Resource::setNetPrice);
        binder.forField(productionDateField)
                .asRequired("Data produkcji jest wymagana!")
                .bind(Resource::getProductionDate, Resource::setProductionDate);
        binder.forField(expirationDateField)
                .asRequired("Data wazności jest wymagana!")
                .bind(Resource::getExpirationDate, Resource::setExpirationDate);

        manufacturerComboBox.setItems(manufacturers);
        manufacturerComboBox.setItemLabelGenerator(Manufacturer::getName);

        storageComboBox.setItems(storages);
        storageComboBox.setItemLabelGenerator(Storage::getAddress);

        unitComboBox.setItems(ResourceType.values());
        unitComboBox.setItemLabelGenerator(ResourceType::name);

        netPriceField.setMin(0);
        netPriceField.isRequired();
        netPriceField.setStep(0.01);
        netPriceField.setStepButtonsVisible(true);

        productionDateField.isRequired();
        expirationDateField.isRequired();

        layout.add(
            nameField,
            storageComboBox,
            manufacturerComboBox,
            unitComboBox,
            netPriceField,
            productionDateField,
            expirationDateField
        );
        binder.readBean(item);
    }

    @Override
    public void setupFields() {
        nameField.setPlaceholder("Wprowadź nazwę zasobu...");
        nameField.setWidthFull();

        storageComboBox.setWidthFull();
        storageComboBox.setPlaceholder("Wybierz magazyn...");

        manufacturerComboBox.setWidthFull();
        manufacturerComboBox.setPlaceholder("Wybierz producenta...");

        unitComboBox.setWidthFull();
        unitComboBox.setPlaceholder("Wybierz jednostkę...");

        netPriceField.setWidthFull();
        netPriceField.setPlaceholder("Wprowadź cenę...");

        productionDateField.setWidthFull();
        productionDateField.setPlaceholder("Wybierz datę produkcji...");

        expirationDateField.setWidthFull();
        expirationDateField.setPlaceholder("Wybierz datę przydatności...");
    }
}
