package edu.wsiiz.repairshop.vehicle.ui.vehicle;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.vehicle.domain.Vehicle;
import org.vehiclefile.application.VehicleService;

import java.util.function.Consumer;

public class VehicleForm extends BaseForm<Vehicle> {

  private final VehicleService vehicleService;
  private final Consumer<Vehicle> afterSave;

  private final TextField vinField = new TextField("VIN");
  private final TextField registrationField = new TextField("Numer rejestracyjny");
  private final TextField brandField = new TextField("Marka");
  private final TextField modelField = new TextField("Model");

  public VehicleForm(Mode mode, Vehicle vehicle, VehicleService vehicleService, Consumer<Vehicle> afterSave) {
    super(mode);
    this.vehicleService = vehicleService;
    this.afterSave = afterSave;

    add(createFormLayout());
    setEntity(vehicle);
  }

  private FormLayout createFormLayout() {
    FormLayout layout = new FormLayout();
    layout.add(vinField, registrationField, brandField, modelField);

    Button saveButton = new Button("Zapisz", event -> save());
    Button cancelButton = new Button("Anuluj", event -> fireCancelEvent());
    HorizontalLayout buttons = new HorizontalLayout(saveButton, cancelButton);

    layout.add(buttons);
    return layout;
  }

  protected void setupFields(){};

  @Override
  protected void loadEntity(Vehicle vehicle) {
    vinField.setValue(vehicle.getVin().value());
    registrationField.setValue(vehicle.getRegistrationNumber().value());
    brandField.setValue(vehicle.getMake());
    modelField.setValue(vehicle.getModel());
  }

  @Override
  protected void saveEntity(Vehicle vehicle) {
    // Tu należałoby stworzyć nową instancję Vehicle lub zaktualizować istniejącą, zależnie od trybu
    // Placeholder – do zaimplementowania z użyciem mapowania do agregatu
    afterSave.accept(vehicle);
  }
}
