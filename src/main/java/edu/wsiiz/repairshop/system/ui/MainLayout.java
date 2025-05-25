package edu.wsiiz.repairshop.system.ui;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.End;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding.Vertical;
import edu.wsiiz.repairshop.foundation.ui.i18n.I18nAware;
import edu.wsiiz.repairshop.foundation.ui.menu.MenuItemInfo;
import lombok.RequiredArgsConstructor;
import lombok.val;


/**
 * The main view is a top-level placeholder for other views.
 */
@Layout
@AnonymousAllowed
@RequiredArgsConstructor
public class MainLayout extends AppLayout implements I18nAware {

  final MenuItemsProvider menuItemsProvider;

  @Override
  protected void onAttach(AttachEvent event) {
    super.onAttach(event);
    addToNavbar(createHeaderContent());
  }

  private Component createHeaderContent() {

    Header header = new Header();

    header.addClassNames(BoxSizing.BORDER, Display.FLEX, FlexDirection.COLUMN, Width.FULL);

    Div layout = new Div();

    layout.addClassNames(Display.FLEX, Padding.Horizontal.LARGE);

    layout.add(createHeader());

    Nav nav = new Nav();
    nav.addClassNames(Display.FLEX, Overflow.AUTO, Padding.Horizontal.LARGE, Vertical.SMALL);

    UnorderedList list = new UnorderedList();
    list.addClassNames(Display.FLEX, Gap.SMALL, ListStyleType.NONE, Margin.NONE, Padding.NONE);
    nav.add(list);

    val menuItems = menuItemsProvider.getMenuItems();

    for (MenuItemInfo menuItem : menuItems) {
      list.add(menuItem);
    }

    header.add(layout, nav);

    return header;
  }

  private Component createHeader() {
    val banner = new HorizontalLayout(new H1("Serwis Samochodowy"));
    banner.addClassNames(Margin.Vertical.MEDIUM, End.AUTO, FontSize.LARGE, "banner");
    return banner;
  }

}
