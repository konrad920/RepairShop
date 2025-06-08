package edu.wsiiz.repairshop.storage.ui.delivery;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.NumberField;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.storage.application.DeliveryService;
import edu.wsiiz.repairshop.storage.domain.storage.*;

import java.util.function.Consumer;

public class DeliveryPartForm extends BaseForm<DeliveryPart> {

    NumberField quantityField = new NumberField("Ilość części");
    ComboBox<Resource> resourceComboBox = new ComboBox<>("Zasób");

    public DeliveryPartForm(Mode mode, DeliveryPart item, DeliveryService service, Delivery delivery, Consumer<DeliveryPart> afterSave) {
        super(mode,
                () -> DeliveryPart.builder().delivery(delivery).build(),
                () -> service.getDeliveryPart(item.getDeliveryPartId()),
                service::save,
                afterSave);

        binder.forField(quantityField)
                .withConverter(
                        Double::intValue,
                        Integer::doubleValue,
                        "Wprowadź poprawną liczbę całkowitą")
                .bind(DeliveryPart::getQuantity, DeliveryPart::setQuantity);
        binder.forField(resourceComboBox)
                .bind(DeliveryPart::getResource, DeliveryPart::setResource);

        resourceComboBox.setItems(service.getAllResources());
        resourceComboBox.setItemLabelGenerator(Resource::getName);

        layout.add(quantityField, resourceComboBox);
        binder.readBean(item);
    }

    @Override
    public void setupFields() {
        quantityField.setPlaceholder("Wprowadź ilość części dostawy...");
        quantityField.isRequired();
        quantityField.setWidthFull();

        resourceComboBox.setPlaceholder("Wybierz zasób...");
        resourceComboBox.isRequired();
        resourceComboBox.setWidthFull();
    }
}
