package edu.wsiiz.repairshop.system.ui;

import edu.wsiiz.repairshop.communication.ui.contact.ContactListView;
import edu.wsiiz.repairshop.employee.ui.employee.EmployeeListView;
import edu.wsiiz.repairshop.foundation.ui.menu.MenuItemInfo;
import edu.wsiiz.repairshop.payments.ui.invoice.InvoiceListView;
import edu.wsiiz.repairshop.payments.ui.settlement.SettlementListView;
import edu.wsiiz.repairshop.vehicles.ui.vehicle.VehicleListView;
import java.util.ArrayList;
import java.util.List;
import lombok.val;
import org.springframework.stereotype.Component;
import org.vaadin.lineawesome.LineAwesomeIcon;

@Component
public class MenuItemsProvider {

  public List<MenuItemInfo> getMenuItems() {
    val items = new ArrayList<MenuItemInfo>();
    items.add(new MenuItemInfo("Witamy", LineAwesomeIcon.HOME_SOLID.create(), WelcomeView.class));
    items.add(new MenuItemInfo("Kontakty z klientami", LineAwesomeIcon.HEADSET_SOLID.create(), ContactListView.class));
    items.add(new MenuItemInfo("Kartoteka pojazdów", LineAwesomeIcon.CAR_SOLID.create(), VehicleListView.class));
    items.add(new MenuItemInfo("Faktury", LineAwesomeIcon.BOOKMARK.create(), InvoiceListView.class));
    items.add(new MenuItemInfo("Rozliczenia", LineAwesomeIcon.MONEY_BILL_SOLID.create(), SettlementListView.class));
    items.add(new MenuItemInfo("Pracownicy", LineAwesomeIcon.USER_SOLID.create(), EmployeeListView.class));
    return items;
  }
}
