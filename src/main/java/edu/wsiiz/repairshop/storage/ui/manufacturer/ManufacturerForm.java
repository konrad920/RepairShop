package edu.wsiiz.repairshop.storage.ui.manufacturer;

import com.vaadin.flow.component.textfield.TextField;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.storage.domain.storage.Manufacturer;
import edu.wsiiz.repairshop.storage.domain.storage.ManufacturerRepository;

import java.util.function.Consumer;

public class ManufacturerForm extends BaseForm<Manufacturer> {

    TextField nameField = new TextField("Nazwa producenta");

    public ManufacturerForm(Mode mode, Manufacturer item, ManufacturerRepository repository, Consumer<Manufacturer> afterSave) {
        super(mode,
                () -> Manufacturer.builder().build(),
                () -> repository.getReferenceById(item.getManufacturerId()),
                repository::save,
                afterSave);

        binder.forField(nameField)
                .asRequired("Nazwa producenta jest wymagana!")
                .bind(Manufacturer::getName, Manufacturer::setName);


        layout.add(nameField);
        binder.readBean(item);
    }

    @Override
    public void setupFields() {
        nameField.setPlaceholder("Wprowadź nazwę producenta...");
        nameField.setWidthFull();
    }
}
