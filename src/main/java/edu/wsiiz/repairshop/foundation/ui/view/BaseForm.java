package edu.wsiiz.repairshop.foundation.ui.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Top;
import edu.wsiiz.repairshop.foundation.ui.i18n.I18nAware;
import java.lang.reflect.ParameterizedType;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import lombok.val;

public abstract class BaseForm<M> extends FormLayout implements I18nAware {

  protected Mode mode;

  final Supplier<M> factory;
  final Supplier<M> reader;
  protected final UnaryOperator<M> writer;
  protected final Consumer<M> afterSave;

  protected M model;

  protected VerticalLayout layout;

  protected Binder<M> binder;

  Button saveBtn = new Button(i18n(BaseForm.class, "saveBtn"));
  Button closeBtn = new Button(i18n(BaseForm.class, "closeBtn"));

  public BaseForm(Mode mode, Supplier<M> factory, Supplier<M> reader, UnaryOperator<M> writer, Consumer<M> afterSave) {

    this.mode = mode;
    this.factory = factory;
    this.reader = reader;
    this.writer = writer;
    this.afterSave = afterSave;
    this.binder = new BeanValidationBinder<>(modelClass());
    this.layout = new VerticalLayout();
    layout.setWidthFull();
    layout.setSpacing(false);
    layout.setPadding(false);
    setColspan(layout, 2);
  }

  @SuppressWarnings("unchecked")
  private Class<M> modelClass() {
    return (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }

  public final void init(Dialog dialog) {

    if (!mode.isAdd()) {
      this.model = reader.get();
      if (model == null) {
        this.mode = Mode.ADD;
      }
    }

    if (mode.isAdd()) {
      this.model = factory.get();
    }

    setupFields();

    val buttonBar = new HorizontalLayout();
    buttonBar.setWidthFull();
    buttonBar.addClassNames(Top.MEDIUM);
    buttonBar.setJustifyContentMode(mode.isView() ? JustifyContentMode.START : JustifyContentMode.BETWEEN);

    closeBtn.addClickListener(e -> dialog.close());
    buttonBar.add(closeBtn);

    if (!mode.isView()) {
      saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
      saveBtn.addClickListener(this::onSave);
      buttonBar.add(saveBtn);
    }

    layout.add(buttonBar);
    add(layout);

    bindFields();

    binder.readBean(model);

    setReadOnlyAndSize(layout);
  }

  private void setReadOnlyAndSize(Component component) {
    component.getChildren().forEach(ch -> {
      if (ch instanceof HasValue<?, ?> hv) {
        if (mode.isView()) {
          hv.setReadOnly(true);
        }
        if (ch instanceof HasSize hs) {
          hs.setWidthFull();
        }
      } else {
        setReadOnlyAndSize(ch);
      }
    });
  }

  protected abstract void setupFields();

  protected void bindFields() {
    binder.bindInstanceFields(this);
  }

  protected void onSave(ClickEvent<Button> event) {
    try {
      binder.writeBean(model);
    } catch (ValidationException e) {
      showNotifation(i18n(BaseForm.class, "validationErrors"));
      return;
    }
    this.model = writer.apply(model);
    showNotifation(i18n(BaseForm.class, "writeSuccess"));
    if (afterSave != null) {
      afterSave.accept(model);
    }
  }

  protected void showNotifation(String text) {
    Notification.show(text, 2_000, Position.MIDDLE);
  }

}
