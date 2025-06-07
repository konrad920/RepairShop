package edu.wsiiz.repairshop.storage.ui.manufacturer;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.foundation.ui.component.MessageDialog;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.storage.domain.storage.*;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.function.Consumer;

@PageTitle("Producenci")
@Route("manufacturer")
public class ManufacturerListView extends ListView<Manufacturer> {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerListView(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;

        setFilters(new ManufacturerFilters(this::refreshGrid));
        setTitleText(i18n("title"));
        setupLayout();
    }

    @Override
    protected TriFunction<Manufacturer, Mode, Consumer<Manufacturer>, BaseForm<Manufacturer>> detailsFormSupplier() {
        return (item, mode, afterSave) -> new ManufacturerForm(mode, item, manufacturerRepository, afterSave);
    }

    @Override
    protected void setupGrid() {
        grid.addColumn("manufacturerId", Manufacturer::getManufacturerId).setHeader("ID Producenta");
        grid.addColumn("name", Manufacturer::getName).setHeader("Nazwa");
        grid.setItems(query -> manufacturerRepository.findAll(getFilters(), PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("name"))).stream());
    }

    @Override
    protected void onDelete(Manufacturer item) {
        MessageDialog.question()
                .withTitle("confirmation")
                .withMessage("onDelete")
                .withNoButton(() -> {
                })
                .withYesButton(() -> {
                    manufacturerRepository.delete(item);
                    refreshGrid();
                })
                .show();
    }
}
