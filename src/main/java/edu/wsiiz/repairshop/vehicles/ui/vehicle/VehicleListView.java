package edu.wsiiz.repairshop.vehicles.ui.vehicle;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.vehicles.domain.vehicle.Vehicle;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Route("/vehicle-list")
@PageTitle("Kartoteka pojazdów")
public class VehicleListView extends VerticalLayout {

    private final Grid<Vehicle> grid = new Grid<>(Vehicle.class);
    private final TextField vinFilter = new TextField("Filtr VIN");
    private final Button searchButton = new Button("Szukaj");
    private final RestTemplate restTemplate = new RestTemplate();

    public VehicleListView() {
        vinFilter.setPlaceholder("Podaj numer VIN");

        HorizontalLayout filters = new HorizontalLayout(vinFilter, searchButton);
        add(filters, grid);

        grid.setColumns("vin", "brand", "model", "vehicleType", "registrationNumber");

        searchButton.addClickListener(e -> updateList());
        updateList();
    }

    private void updateList() {
        String vin = vinFilter.getValue();
        String url = "http://localhost:8080/api/vehicles" + (vin.isEmpty() ? "" : "?vin=" + vin);
        List<Vehicle> vehicles = Arrays.asList(restTemplate.getForObject(url, Vehicle[].class));
        grid.setItems(vehicles);
    }
}
