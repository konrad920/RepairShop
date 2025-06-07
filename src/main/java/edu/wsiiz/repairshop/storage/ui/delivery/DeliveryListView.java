package edu.wsiiz.repairshop.storage.ui.delivery;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.foundation.ui.component.MessageDialog;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.storage.application.DeliveryService;
import edu.wsiiz.repairshop.storage.domain.storage.Delivery;
import edu.wsiiz.repairshop.storage.domain.storage.DeliveryPart;
import edu.wsiiz.repairshop.storage.domain.storage.DeliveryRepository;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.function.Consumer;

@PageTitle("Dostawy")
@Route("delivery")
public class DeliveryListView extends ListView<Delivery> {

    private final DeliveryService deliveryService;
    private final DeliveryRepository deliveryRepository;

    @Setter
    @Getter
    private Filters<DeliveryPart> deliveryPartFilters;

    public DeliveryListView(DeliveryService deliveryService, DeliveryRepository deliveryRepository) {
        this.deliveryService = deliveryService;
        this.deliveryRepository = deliveryRepository;

        setFilters(new DeliveryFilters(this::refreshGrid));
        setTitleText(i18n("title"));
        setupLayout();
    }

    @Override
    protected TriFunction<Delivery, Mode, Consumer<Delivery>, BaseForm<Delivery>> detailsFormSupplier() {
        return (item, mode, afterSave) -> new DeliveryForm(mode, item, deliveryService, afterSave);
    }

    @Override
    protected void addAdditionalActions(HorizontalLayout actions) {
        Button goToDeliveryPartGrid = new Button(LineAwesomeIcon.LIST_SOLID.create());
        goToDeliveryPartGrid.addThemeVariants(ButtonVariant.LUMO_ICON);
        goToDeliveryPartGrid.addClickListener(e -> runOnSelected(this::onMoveToDeliveryPartGrid));

        actions.add(goToDeliveryPartGrid);
    }

    private void onMoveToDeliveryPartGrid(Delivery item) {
        UI.getCurrent().navigate("/deliveryPart/" + item.getDeliveryId());
    }

    @Override
    protected void setupGrid() {
        grid.addColumn("deliveryId", Delivery::getDeliveryId).setHeader("ID dostawy");
        grid.addColumn("deliveryDate", Delivery::getDeliveryDate).setHeader("Data dostawy");
        grid.setItems(query -> deliveryRepository.findAll(getFilters(), PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("deliveryDate"))).stream());
    }

    @Override
    protected void onDelete(Delivery item) {
        MessageDialog.question()
                .withTitle(i18n("confirmation"))
                .withMessage(i18n("onDelete"))
                .withNoButton(() -> {
                })
                .withYesButton(() -> {
                    deliveryRepository.delete(item);
                    refreshGrid();
                })
                .show();
    }
}