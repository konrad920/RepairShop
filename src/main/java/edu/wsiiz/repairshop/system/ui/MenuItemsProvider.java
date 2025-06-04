package edu.wsiiz.repairshop.system.ui;

import edu.wsiiz.repairshop.communication.ui.contact.ContactListView;
import edu.wsiiz.repairshop.foundation.ui.menu.MenuItemInfo;

import java.util.ArrayList;
import java.util.List;

import edu.wsiiz.repairshop.storage.ui.resource.ResourceListView;
import edu.wsiiz.repairshop.storage.ui.storage.StorageListView;
import lombok.val;
import org.springframework.stereotype.Component;
import org.vaadin.lineawesome.LineAwesomeIcon;

@Component
public class MenuItemsProvider {

    public List<MenuItemInfo> getMenuItems() {
        val items = new ArrayList<MenuItemInfo>();
        items.add(new MenuItemInfo("Witamy", LineAwesomeIcon.HOME_SOLID.create(), WelcomeView.class));
        items.add(new MenuItemInfo("Kontakty z klientami", LineAwesomeIcon.HEADSET_SOLID.create(), ContactListView.class));
        items.add(new MenuItemInfo("Magazyn", LineAwesomeIcon.WAREHOUSE_SOLID.create(), StorageListView.class));
        items.add(new MenuItemInfo("Zasoby", LineAwesomeIcon.BOX_SOLID.create(), ResourceListView.class));

        return items;
    }

}
