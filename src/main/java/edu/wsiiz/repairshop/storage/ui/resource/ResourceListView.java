package edu.wsiiz.repairshop.storage.ui.resource;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.foundation.ui.component.MessageDialog;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.storage.application.ResourceService;
import edu.wsiiz.repairshop.storage.domain.storage.*;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.function.Consumer;

@PageTitle("Zasoby")
@Route("resource")
public class ResourceListView extends ListView<Resource> {

    private final ResourceRepository resourceRepository;
    private final ResourceService resourceService;
    private final ManufacturerRepository manufacturerRepository;
    private final StorageRepository storageRepository;

    public ResourceListView(ResourceRepository resourceRepository, ResourceService resourceService, ManufacturerRepository manufacturerRepository, StorageRepository storageRepository) {
        this.resourceRepository = resourceRepository;
        this.resourceService = resourceService;
        this.manufacturerRepository = manufacturerRepository;
        this.storageRepository = storageRepository;

        setFilters(new ResourceFilters(this::refreshGrid, manufacturerRepository.findAll(), storageRepository.findAll()));
        setTitleText(i18n("title"));
        setupLayout();
    }

    @Override
    protected TriFunction<Resource, Mode, Consumer<Resource>, BaseForm<Resource>> detailsFormSupplier() {
        return (item, mode, afterSave) -> new ResourceForm(mode, item, resourceService, manufacturerRepository.findAll(), storageRepository.findAll(), afterSave);
    }

    @Override
    protected void setupGrid() {
        grid.addColumn("resourceId", Resource::getResourceId).setHeader("ID Zasobu");
        grid.addColumn("name", Resource::getName).setHeader("Nazwa");
        grid.addColumn("unit", resource -> resource.getUnit().name()).setHeader("Jednostka").setSortable(true);
        grid.addColumn("manufacturer", resource -> Optional.ofNullable(resource.getManufacturer()).map(Manufacturer::getName).orElse(null)).setHeader("Producent");
        grid.addColumn("netPrice", Resource::getNetPrice).setHeader("Cena netto");
        grid.addColumn("productionDate", Resource::getProductionDate).setHeader("Data produkcji");
        grid.addColumn("expirationDate", Resource::getExpirationDate).setHeader("Data ważności");
        grid.addColumn("quantity", Resource::getQuantity).setHeader("Ilość");
        grid.addColumn("storage", resource -> Optional.ofNullable(resource.getStorage()).map(Storage::getAddress).orElse(null)).setHeader("Magazyn");
        grid.setItems(query -> resourceRepository.findAll(getFilters(), PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("name"))).stream());
    }

    @Override
    protected void onDelete(Resource item) {
        MessageDialog.question()
                .withTitle("confirmation")
                .withMessage("onDelete")
                .withNoButton(() -> {
                })
                .withYesButton(() -> {
                    resourceRepository.delete(item);
                    refreshGrid();
                })
                .show();
    }
}