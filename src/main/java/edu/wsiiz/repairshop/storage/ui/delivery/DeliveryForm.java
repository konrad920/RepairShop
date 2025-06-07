package edu.wsiiz.repairshop.storage.ui.delivery;

import com.vaadin.flow.component.datepicker.DatePicker;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.storage.application.DeliveryService;
import edu.wsiiz.repairshop.storage.domain.storage.Delivery;
import edu.wsiiz.repairshop.storage.domain.storage.DeliveryPart;

import java.util.function.Consumer;

public class DeliveryForm extends BaseForm<Delivery> {

    DatePicker deliveryDateField = new DatePicker(i18n("deliveryDate"));

    public DeliveryForm(Mode mode, Delivery item, DeliveryService service, Consumer<Delivery> afterSave) {
        super(mode,
                () -> Delivery.builder().build(),
                () -> service.getDelivery(item.getDeliveryId()),
                service::save,
                afterSave);


        binder.forField(deliveryDateField)
                .bind(Delivery::getDeliveryDate, Delivery::setDeliveryDate);

        layout.add(deliveryDateField);
        binder.readBean(item);
    }

    @Override
    public void setupFields() {
        deliveryDateField.setPlaceholder("Wprowadź datę dostawy...");
        deliveryDateField.isRequired();
        deliveryDateField.setWidthFull();
    }
}
