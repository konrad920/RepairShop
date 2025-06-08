package edu.wsiiz.repairshop.storage.ui.storage;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.foundation.ui.component.MessageDialog;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.storage.domain.storage.Storage;
import edu.wsiiz.repairshop.storage.domain.storage.StorageRepository;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Widok GUI dla zarządzania magazynami.
 */
@PageTitle("Magazyny")
@Route("storage")
public class StorageListView extends ListView<Storage> {

    private final StorageRepository storageRepository;

    public StorageListView(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;

        setFilters(new StorageFilters(this::refreshGrid));
//        setTitleText(i18n("title"));
        setupLayout();
    }

    @Override
    protected TriFunction<Storage, Mode, Consumer<Storage>, BaseForm<Storage>> detailsFormSupplier() {
        return (item, mode, afterSave) -> new StorageForm(mode, item, storageRepository, afterSave);
    }

    @Override
    protected void setupGrid() {
        grid.addColumn("storageId", Storage::getStorageId).setHeader("ID Magazynu");
        grid.addColumn("address", Storage::getAddress).setHeader("Adres");
        grid.setItems(query -> storageRepository.findAll(getFilters(), PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("address"))).stream());
    }

    @Override
    protected void onDelete(Storage item) {
        MessageDialog.question()
                .withTitle("confirmation")
                .withMessage("onDelete")
                .withNoButton(() -> {
                })
                .withYesButton(() -> {
                    storageRepository.delete(item);
                    refreshGrid();
                })
                .show();
    }
}