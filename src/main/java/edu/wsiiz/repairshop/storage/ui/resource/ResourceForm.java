package edu.wsiiz.repairshop.storage.ui.resource;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import edu.wsiiz.repairshop.storage.application.ResourceService;
import edu.wsiiz.repairshop.storage.domain.storage.*;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ResourceForm extends BaseForm<Resource> {

    TextField nameField = new TextField("Nazwa zasobu");
    ComboBox<Storage> storageComboBox = new ComboBox<>("Magazyn");
    ComboBox<Manufacturer> manufacturerComboBox = new ComboBox<>("Producent");


    public ResourceForm(Mode mode, Resource item, ResourceService resourceService, List<Manufacturer> manufacturers, List<Storage> storages, Consumer<Resource> afterSave) {
        super(mode,
                () -> Resource.builder().build(),
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

        manufacturerComboBox.setItems(manufacturers);
        manufacturerComboBox.setItemLabelGenerator(Manufacturer::getName);

        storageComboBox.setItems(storages);
        storageComboBox.setItemLabelGenerator(Storage::getAddress);

        layout.add(nameField, storageComboBox, manufacturerComboBox);
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
    }
}
