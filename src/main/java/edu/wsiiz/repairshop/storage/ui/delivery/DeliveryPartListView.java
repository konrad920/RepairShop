package edu.wsiiz.repairshop.storage.ui.delivery;

import com.vaadin.flow.router.*;
import edu.wsiiz.repairshop.foundation.ui.component.MessageDialog;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.storage.application.DeliveryService;
import edu.wsiiz.repairshop.storage.domain.storage.*;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.function.Consumer;

@PageTitle("lista elementów dostawy")
@Route("deliveryPart/")
public class DeliveryPartListView extends ListView<DeliveryPart> implements HasUrlParameter<Long> {

    private final DeliveryService deliveryService;
    private final DeliveryPartRepository deliveryPartRepository;
    private Delivery delivery;

    public DeliveryPartListView(DeliveryService deliveryService, DeliveryPartRepository deliveryPartRepository) {
        this.deliveryService = deliveryService;
        this.deliveryPartRepository = deliveryPartRepository;
    }

    @Override
    public void setParameter(BeforeEvent event, Long deliveryId) {
        delivery = this.deliveryService.getDelivery(deliveryId);
        setTitleText("Liczba części dostawy dla ID dostawy: " + delivery.getDeliveryId());

        setFilters(new DeliveryPartFilters(this::refreshGrid, delivery));
        setupLayout();
    }

    @Override
    protected TriFunction<DeliveryPart, Mode, Consumer<DeliveryPart>, BaseForm<DeliveryPart>> detailsFormSupplier() {
        return (item, mode, afterSave) -> new DeliveryPartForm(mode, item, deliveryService, delivery, afterSave);
    }

    @Override
    protected void setupGrid() {
        grid.addColumn("deliveryPartId", DeliveryPart::getDeliveryPartId).setHeader("ID części dostawy");
        grid.addColumn("resource", deliveryPart -> Optional.ofNullable(deliveryPart.getResource()).map(Resource::getName).orElse(null)).setHeader("Nazwa zasobu");
        grid.addColumn("quantity", DeliveryPart::getQuantity).setHeader("Ilość w dostawie");
        grid.setItems(query -> deliveryPartRepository.findAll(getFilters(), PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("quantity"))).stream());
    }

    @Override
    protected void onDelete(DeliveryPart item) {
        MessageDialog.question()
                .withTitle(i18n("confirmation"))
                .withMessage(i18n("onDelete"))
                .withNoButton(() -> {
                })
                .withYesButton(() -> {
                    deliveryService.deleteDeliveryPart(item.getDeliveryPartId());
                    refreshGrid();
                })
                .show();
    }
}