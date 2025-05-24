package edu.wsiiz.repairshop.foundation.ui.component;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import edu.wsiiz.repairshop.foundation.ui.i18n.I18nAware;
import java.util.LinkedHashMap;
import java.util.Map;

public class MessageDialog extends com.vaadin.flow.component.dialog.Dialog implements I18nAware {

  public enum DialogType {
    INFORMATION, WARNING, ERROR, SUCCESS, QUESTION
  }

  private String title;
  private String message;
  private final DialogType dialogType;

  private final Map<String, Runnable> buttons = new LinkedHashMap<>();

  public MessageDialog(DialogType dialogType) {
    this.dialogType = dialogType;
  }

  public static MessageDialog information() {
    return new MessageDialog(DialogType.INFORMATION);
  }

  public static MessageDialog warning() {
    return new MessageDialog(DialogType.WARNING);
  }

  public static MessageDialog success() {
    return new MessageDialog(DialogType.SUCCESS);
  }

  public static MessageDialog error() {
    return new MessageDialog(DialogType.ERROR);
  }

  public static MessageDialog question() {
    return new MessageDialog(DialogType.QUESTION);
  }

  public MessageDialog withTitle(String title) {
    this.title = title;
    return this;
  }

  public MessageDialog withMessage(String message) {
    this.message = message;
    return this;
  }

  public MessageDialog withYesButton(Runnable onYes) {
    this.buttons.put("Yes", onYes);
    return this;
  }

  public MessageDialog withNoButton(Runnable onNo) {
    this.buttons.put("No", onNo);
    return this;
  }

  public MessageDialog withOkButton(Runnable onOk) {
    this.buttons.put("Ok", onOk);
    return this;
  }

  public MessageDialog withCloseButton(Runnable onClose) {
    this.buttons.put("Close", onClose);
    return this;
  }

  public MessageDialog withCancelButton(Runnable onCancel) {
    this.buttons.put("Cancel", onCancel);
    return this;
  }

  public MessageDialog withApplyButton(Runnable onApply) {
    this.buttons.put("Apply", onApply);
    return this;
  }

  public void show() {
    H2 titleText = new H2(title);
    titleText.addClassName("dialog-title");

    HorizontalLayout header = new HorizontalLayout();
    header.add(titleText);
    header.setWidthFull();

    this.getHeader().add(header);

    Text text = new Text(message);
    this.add(text);

    HorizontalLayout layoutButtons = new HorizontalLayout();

    this.buttons.forEach((buttonName, buttonAction) -> {
      Button button = new Button(i18n(buttonName));
      button.addClickListener(e -> {
        if (buttonAction != null) {
          buttonAction.run();
        }
        this.close();
      });
      layoutButtons.add(button);
    });

    this.getFooter().add(layoutButtons);

    this.addClassName("dialog");
    this.addClassName("dialog-" + this.dialogType.toString().toLowerCase());
    this.open();
  }
}