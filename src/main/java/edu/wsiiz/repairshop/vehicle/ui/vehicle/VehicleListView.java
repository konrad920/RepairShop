package edu.wsiiz.repairshop.vehicle.ui.vehicle;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.foundation.ui.component.MessageDialog;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.vehicle.domain.Vehicle;
import org.vehiclefile.application.VehicleService;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@PageTitle("Lista pojazdów")
@Route("vehicle-list")
public class VehicleListView extends ListView<Vehicle> {

  final VehicleService vehicleService;


  public VehicleListView(VehicleService vehicleService) {
    this.vehicleService = vehicleService;
    setFilters(new VehicleFilters(this::refreshGrid));
    setTitleText("Lista pojazdów");
    setupLayout();
  }


  @Override
  protected void setupGrid() {
    grid.addColumn("VIN", v -> v.getVin().value());
    grid.addColumn("Rejestracja", v -> v.getRegistrationNumber().value());
    grid.addColumn("Marka", Vehicle::getMake);
    grid.addColumn("Model", Vehicle::getModel);
    grid.addColumn("Typ", v -> v.getType().name());

    grid.setItems(
            query ->
                    vehicleService.listVehicles(null, null, null, null).stream(),
            query -> {
              int count = vehicleService.listVehicles(null, null, null, null).size();
              countField.setText(String.valueOf(count));
              return count;
            });
  }

  @Override
  protected void onDelete(Vehicle item) {
    MessageDialog.question()
            .withTitle("Potwierdzenie")
            .withMessage("Czy na pewno chcesz usunąć pojazd?")
            .withNoButton(() -> {
            })
            .withYesButton(() -> {
              vehicleService.deleteVehicle(item.id());
              refreshGrid();
            })
            .show();
  }
}
