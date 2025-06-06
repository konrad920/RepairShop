package edu.wsiiz.repairshop.system.ui;

import edu.wsiiz.repairshop.audit.ui.AuditLogView;
import edu.wsiiz.repairshop.auth.application.CurrentUser;
import edu.wsiiz.repairshop.auth.domain.user.User;
import edu.wsiiz.repairshop.auth.domain.user.UserRole;
import edu.wsiiz.repairshop.auth.ui.LoginView;
import edu.wsiiz.repairshop.communication.ui.contact.ContactListView;
import edu.wsiiz.repairshop.foundation.ui.menu.MenuItemInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.val;
import org.springframework.stereotype.Component;
import org.vaadin.lineawesome.LineAwesomeIcon;

@Component
public class MenuItemsProvider {

  public List<MenuItemInfo> getMenuItems() {
    val items = new ArrayList<MenuItemInfo>();

    User user = CurrentUser.get();

    // Widok domyślny (dla wszystkich zalogowanych)
    items.add(new MenuItemInfo("Strona główna", LineAwesomeIcon.HOME_SOLID.create(), WelcomeView.class));

    if (user != null) {
      boolean isAdmin = user.getRole().getName() == UserRole.ADMIN;
      boolean isEmployee = user.getRole().getName() == UserRole.EMPLOYEE;

      // Widok kontaktów: dla pracownika i admina
      if (isAdmin || isEmployee) {
        items.add(new MenuItemInfo("Kontakty z klientami", LineAwesomeIcon.HEADSET_SOLID.create(), ContactListView.class));
      }

      // Widok audytu: tylko dla admina
      if (isAdmin) {
        items.add(new MenuItemInfo("Log audytu", LineAwesomeIcon.CLIPBOARD_LIST_SOLID.create(), AuditLogView.class));
      }
    }
    return items;
  }
}
