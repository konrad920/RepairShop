package edu.wsiiz.repairshop.storage.ui.storage;

import com.vaadin.flow.component.textfield.TextField;
import edu.wsiiz.repairshop.storage.domain.storage.Storage;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.storage.domain.storage.StorageRepository;

import java.util.function.Consumer;

public class StorageForm extends BaseForm<Storage> {

    TextField addressField = new TextField("Adres magazynu");

    public StorageForm(Mode mode, Storage item, StorageRepository repository, Consumer<Storage> afterSave) {
        super(mode,
                () -> Storage.builder().build(),
                () -> repository.getReferenceById(item.getStorageId()),
                repository::save,
                afterSave);

        binder.forField(addressField)
                .asRequired("Adres magazynu jest wymagany!")
                .bind(Storage::getAddress, Storage::setAddress);

        layout.add(addressField);
        binder.readBean(item);
    }

    @Override
    public void setupFields() {
        addressField.setPlaceholder("Wprowadź adres magazynu...");
        addressField.setWidthFull();
    }
}