package edu.wsiiz.repairshop.storage.ui.storage;

import com.vaadin.flow.component.textfield.TextField;
import edu.wsiiz.repairshop.storage.domain.storage.Storage;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class StorageForm extends BaseForm<Storage> {

    TextField addressField = new TextField("Adres magazynu");

    public StorageForm(Mode mode, Storage item, UnaryOperator<Storage> writer, Consumer<Storage> afterSave) {
        super(mode,
                () -> new Storage(),
                () -> item,
                writer != null ? writer : storage -> storage,
                afterSave != null ? afterSave : s -> {});
    }

    @Override
    public void setupFields() {
        addressField.setPlaceholder("Wprowadź adres magazynu...");
        addressField.setWidthFull();
        addressField.setRequired(true);
        addressField.setErrorMessage("Adres magazynu jest wymagany!");

        layout.add(addressField);
    }
}