package edu.wsiiz.repairshop.vehicle.ui.vehicle;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.function.Consumer;

public class VehicleFilters extends VerticalLayout {

  private final TextField vinField = new TextField("VIN");
  private final TextField registrationField = new TextField("Rejestracja");
  private final TextField ownerLastNameField = new TextField("Nazwisko właściciela");
  private final TextField policyNumberField = new TextField("Nr polisy");

  private final Consumer<Void> onSearchCallback;

  public VehicleFilters(Consumer<Void> onSearchCallback) {
    this.onSearchCallback = onSearchCallback;
    setupLayout();
  }

  private void setupLayout() {
    HorizontalLayout fields = new HorizontalLayout();
    fields.setSpacing(true);
    fields.add(vinField, registrationField, ownerLastNameField, policyNumberField);

    Button searchButton = new Button("Szukaj", e -> onSearchCallback.accept(null));

    add(fields, searchButton);
  }

  public String getVin() {
    return vinField.getValue();
  }

  public String getRegistration() {
    return registrationField.getValue();
  }

  public String getOwnerLastName() {
    return ownerLastNameField.getValue();
  }

  public String getPolicyNumber() {
    return policyNumberField.getValue();
  }
}
