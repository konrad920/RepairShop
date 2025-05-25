package edu.wsiiz.repairshop.storage.ui.storage;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.storage.domain.storage.Storage;
import edu.wsiiz.repairshop.storage.domain.storage.StorageRepository;

import java.util.List;

/**
 * Widok GUI dla zarządzania magazynami.
 */
@PageTitle("Magazyny")
@Route("storage")
public class StorageListView extends VerticalLayout {

    private final StorageRepository storageRepository;
    private final Grid<Storage> grid = new Grid<>(Storage.class);
    private final Button addButton = new Button("Dodaj Magazyn");
    private final Button refreshButton = new Button("Odśwież");

    public StorageListView(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;

        setSizeFull();
        configureGrid();
        setupButtons();

        // Layout zawierający interfejs do zarządzania
        HorizontalLayout toolbar = new HorizontalLayout(addButton, refreshButton);
        toolbar.setPadding(false);

        add(toolbar, grid);
        refreshGrid();
    }

    /**
     * Konfiguracja tabeli Grid wyświetlającej dane magazynów.
     */
    private void configureGrid() {
        grid.setSizeFull();

        // Kolumny tabeli
        grid.removeAllColumns();
        grid.addColumn(Storage::getStorageId).setHeader("ID Magazynu");
        grid.addColumn(Storage::getAddress).setHeader("Adres");

        // Akcja po kliknięciu
        grid.asSingleSelect().addValueChangeListener(event -> {
            Storage selected = event.getValue();
            if (selected != null) {
                showStorageDetails(selected);
            }
        });
    }

    /**
     * Konfiguracja przycisków GUI.
     */
    private void setupButtons() {
        addButton.addClickListener(event -> createNewStorage());
        refreshButton.addClickListener(event -> refreshGrid());
    }

    /**
     * Odświeża dane w tabeli.
     */
    private void refreshGrid() {
        List<Storage> storages = storageRepository.findAll();
        grid.setItems(storages);
    }

    /**
     * Otwiera nowy formularz do tworzenia magazynu.
     */
    private void createNewStorage() {
        // Wyświetlenie formularza tworzenia nowego magazynu (do zaimplementowania)
        System.out.println("Tworzenie nowego magazynu...");
    }

    /**
     * Wyświetla szczegóły wybranego magazynu.
     *
     * @param selected wybrany magazyn
     */
    private void showStorageDetails(Storage selected) {
        // Obsługa wyświetlania szczegółów magazynu (do zaimplementowania)
        System.out.printf("ID Magazynu: %d, Adres: %s%n", selected.getStorageId(), selected.getAddress());
    }
}