package edu.wsiiz.repairshop.storage.ui.resource;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.foundation.ui.component.MessageDialog;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.storage.application.ResourceService;
import edu.wsiiz.repairshop.storage.domain.storage.*;
import lombok.val;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.vaadin.lineawesome.LineAwesomeIcon;

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
    protected void addAdditionalActions(HorizontalLayout actions) {
        super.addAdditionalActions(actions);

        Button reserveButton = new Button("Rezerwuj zasób", e -> openReservationDialog());
        reserveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        actions.add(reserveButton);
    }

    private void openReservationDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Rezerwacja zasobu");

        TextField resourceIdField = new TextField("ID zasobu");
        NumberField quantityField = new NumberField("Ilość do rezerwacji");
        quantityField.setMin(1);

        Button confirmButton = new Button("Potwierdź", e -> {
            try {
                Long resourceId = Long.parseLong(resourceIdField.getValue());
                int quantityToReserve = quantityField.getValue().intValue();

                handleReservation(resourceId, quantityToReserve);
                dialog.close();
            } catch (Exception ex) {
                Dialog errorDialog = new Dialog();
                errorDialog.setHeaderTitle("Błąd");
                errorDialog.add("Nieprawidłowe dane wejściowe.");

                Button closeButton = new Button("Zamknij", e1 -> errorDialog.close());
                closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                errorDialog.add(closeButton);

                errorDialog.open();
            }
        });
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancelButton = new Button("Anuluj", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        HorizontalLayout buttons = new HorizontalLayout(confirmButton, cancelButton);
        buttons.setWidthFull();

        dialog.add(resourceIdField, quantityField, buttons);
        dialog.open();
    }

    private void handleReservation(Long resourceId, int quantityToReserve) {
        Optional<Resource> resourceOptional = resourceRepository.findById(resourceId);

        if (resourceOptional.isPresent()) {
            Resource resource = resourceOptional.get();

            if (resource.getQuantity() >= quantityToReserve) {
                resourceService.updateResourceQuantity(resource, resource.getQuantity() - quantityToReserve);
                grid.getDataProvider().refreshItem(resource);

                Dialog successDialog = new Dialog();
                successDialog.setHeaderTitle("Sukces");
                successDialog.add("Zasób został pomyślnie zarezerwowany.");
                Button closeButton = new Button("Zamknij", e -> successDialog.close());
                successDialog.add(closeButton);
                successDialog.open();
            } else {
                Dialog errorDialog = new Dialog();
                errorDialog.setHeaderTitle("Błąd");
                errorDialog.add("Niewystarczająca ilość zasobów.");
                Button closeButton = new Button("Zamknij", e -> errorDialog.close());
                errorDialog.add(closeButton);
                errorDialog.open();
            }
        } else {
            Dialog notFoundDialog = new Dialog();
            notFoundDialog.setHeaderTitle("Błąd");
            notFoundDialog.add("Zasób o podanym ID nie istnieje.");
            Button closeButton = new Button("Zamknij", e -> notFoundDialog.close());
            notFoundDialog.add(closeButton);
            notFoundDialog.open();
        }
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