package edu.wsiiz.repairshop.foundation.ui.menu;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.List;

public class SubMenuItemInfo extends ListItem {
    public SubMenuItemInfo(String menuTitle, Component icon, List<MenuItemInfo> subItems) {
        Span clickableTitle = new Span();
        clickableTitle.setText(menuTitle);
        clickableTitle.addClassName("submenu-title");
        clickableTitle.getStyle().set("cursor", "pointer");

        clickableTitle.addClassNames(
            LumoUtility.FontWeight.MEDIUM,
            LumoUtility.FontSize.MEDIUM,
            LumoUtility.Whitespace.NOWRAP
        );

        Button expandButton = new Button(new Icon(VaadinIcon.ANGLE_DOWN));
        expandButton.addClassName("expand-button");

        UnorderedList subItemList = new UnorderedList();
        subItemList.getStyle().set("list-style", "none");
        subItemList.getStyle().set("padding-left", "0");
        subItemList.getStyle().set("margin", "0");
        subItemList.setVisible(false);

        for (MenuItemInfo item : subItems) {
            subItemList.add(item);
        }

        clickableTitle.addClickListener(event -> subItemList.setVisible(!subItemList.isVisible()));

        ListItem header = new ListItem(icon, clickableTitle);

        header.addClassNames(
            LumoUtility.Display.FLEX,
            LumoUtility.Gap.XSMALL,
            LumoUtility.Height.MEDIUM,
            LumoUtility.AlignItems.CENTER,
            LumoUtility.Padding.Horizontal.SMALL,
            LumoUtility.TextColor.BODY
        );

        add(header, subItemList);
        addClassName("submenu-item");
    }
}