package edu.wsiiz.repairshop.foundation.ui.view;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import edu.wsiiz.repairshop.foundation.ui.i18n.I18nAware;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public abstract class BaseView extends Composite<VerticalLayout> implements I18nAware, BeforeEnterObserver {

  private final H1 headingTitle;
  protected final VerticalLayout viewLayout;

  protected BaseView() {

    val content = getContent();

    content.setWidthFull();
    content.getStyle().set("flex-grow", "1");
    content.addClassName(Padding.Horizontal.XLARGE);

    this.headingTitle = new H1();
    headingTitle.setWidthFull();
    content.add(headingTitle);

    this.viewLayout = new VerticalLayout();
    viewLayout.setWidthFull();
    viewLayout.setPadding(false);

    content.add(viewLayout);
  }

  @Override
  public void beforeEnter(BeforeEnterEvent event) {

  }

  protected void setTitleText(String text) {
    headingTitle.setText(text);
  }

  protected void showNotifation(String text) {
    Notification.show(text, 2_000, Position.MIDDLE);
  }

}
