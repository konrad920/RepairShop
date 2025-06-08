package edu.wsiiz.repairshop.system.ui;

import com.vaadin.flow.component.html.ListItem;
import edu.wsiiz.repairshop.communication.ui.contact.ContactListView;

import edu.wsiiz.repairshop.employee.ui.employee.EmployeeListView;
import edu.wsiiz.repairshop.customers.ui.customer.CustomerListView;
import edu.wsiiz.repairshop.foundation.ui.menu.MenuItemInfo;
import edu.wsiiz.repairshop.payments.ui.invoice.InvoiceListView;
import edu.wsiiz.repairshop.payments.ui.settlement.SettlementListView;
import edu.wsiiz.repairshop.vehicles.ui.vehicle.VehicleListView;
import java.util.ArrayList;
import java.util.List;

import edu.wsiiz.repairshop.foundation.ui.menu.MenuItemInfo;
import edu.wsiiz.repairshop.foundation.ui.menu.SubMenuItemInfo;
import edu.wsiiz.repairshop.storage.ui.delivery.DeliveryListView;
import edu.wsiiz.repairshop.storage.ui.manufacturer.ManufacturerListView;
import edu.wsiiz.repairshop.storage.ui.resource.ResourceListView;
import edu.wsiiz.repairshop.storage.ui.storage.StorageListView;
import lombok.val;
import org.springframework.stereotype.Component;
import org.vaadin.lineawesome.LineAwesomeIcon;

@Component
public class MenuItemsProvider {

    public List<ListItem> getMenuItems() {
        val items = new ArrayList<ListItem>();
        items.add(new MenuItemInfo("Witamy", LineAwesomeIcon.HOME_SOLID.create(), WelcomeView.class));
        items.add(new MenuItemInfo("Kontakty z klientami", LineAwesomeIcon.HEADSET_SOLID.create(), ContactListView.class));
      items.add(new MenuItemInfo("Kartoteka pojazdów", LineAwesomeIcon.CAR_SOLID.create(), VehicleListView.class));
      items.add(new MenuItemInfo("Faktury", LineAwesomeIcon.BOOKMARK.create(), InvoiceListView.class));
      items.add(new MenuItemInfo("Rozliczenia", LineAwesomeIcon.MONEY_BILL_SOLID.create(), SettlementListView.class));
      items.add(new MenuItemInfo("Pracownicy", LineAwesomeIcon.USER_SOLID.create(), EmployeeListView.class));
      items.add(new MenuItemInfo("Klienci", LineAwesomeIcon.HEADSET_SOLID.create(), CustomerListView.class));

        val subItems = List.of(
            new MenuItemInfo("Producenci", LineAwesomeIcon.COLUMNS_SOLID.create(), ManufacturerListView.class),
            new MenuItemInfo("Magazyn", LineAwesomeIcon.WAREHOUSE_SOLID.create(), StorageListView.class),
            new MenuItemInfo("Zasoby", LineAwesomeIcon.BOX_SOLID.create(), ResourceListView.class),
            new MenuItemInfo("Dostawy", LineAwesomeIcon.BOX_SOLID.create(), DeliveryListView.class)
        );

        items.add(new SubMenuItemInfo("Zarządzanie Magazynem", LineAwesomeIcon.WAREHOUSE_SOLID.create(), subItems));

        return items;
    }

}
