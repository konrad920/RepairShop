package edu.wsiiz.repairshop.foundation.ui.menu;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility.*;

public class MenuItemInfo extends ListItem {

  public MenuItemInfo(String menuTitle, Component icon, Class<? extends Component> view) {

    RouterLink link = new RouterLink();
    link.setRoute(view);
    link.add(icon);
    Span text = new Span(menuTitle);
    link.add(text);
    link.setHighlightCondition(HighlightConditions.sameLocation());

    link.addClassNames(Display.FLEX, Gap.XSMALL, Height.MEDIUM, AlignItems.CENTER, Padding.Horizontal.SMALL,
        TextColor.BODY);
    text.addClassNames(FontWeight.MEDIUM, FontSize.MEDIUM, Whitespace.NOWRAP);

    add(link);
  }

}