package edu.wsiiz.repairshop.payments.ui.invoice;

//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import edu.wsiiz.repairshop.communication.domain.contact.Contact;
//import edu.wsiiz.repairshop.communication.ui.contact.ContactForm;
//import edu.wsiiz.repairshop.foundation.ui.component.MessageDialog;
//import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
//import edu.wsiiz.repairshop.foundation.ui.view.ListView;
//import edu.wsiiz.repairshop.foundation.ui.view.Mode;
//import edu.wsiiz.repairshop.payments.application.InvoiceService;
//import edu.wsiiz.repairshop.payments.domain.invoice.Invoice;
//import edu.wsiiz.repairshop.payments.domain.invoice.InvoiceRepository;
//import jakarta.annotation.security.RolesAllowed;
//import org.apache.commons.lang3.function.TriFunction;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//
//import java.util.function.Consumer;
//
//@PageTitle("Lista faktur")
//@Route("invoice-list")
//public class InvoiceListView extends ListView<Invoice> {
//
//    final InvoiceRepository invoiceRepository;
//    final InvoiceService invoiceService;
//
//    public InvoiceListView(InvoiceRepository invoiceRepository, InvoiceService invoiceService) {
//        this.invoiceRepository = invoiceRepository;
//        this.invoiceService = invoiceService;
//        setFilters(new InvoiceFilters(this::refreshGrid));
//        setTitleText(i18n("title"));
//        setupLayout();
//    }
//
//    @Override
//    protected void setupGrid() {
//grid.addColumn("invoiceNumber", Invoice::getId);
//grid.addColumn("status", Invoice::getStatus);
//grid.addColumn("issueDate", Invoice::getIssueDate);
//
//grid.setItems(invoiceRepository.findAll());
//
//        grid.setItems(
//                query ->
//                        invoiceRepository
//                                .findAll(getFilters(), PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("plannedDate")))
//                                .stream(),
//                query -> {
//                    int count = (int) invoiceRepository.count(getFilters());
//                    countField.setText(String.valueOf(count));
//                    return count;
//                });
//    }
//
//    @Override
//    protected void onDelete(Invoice item) {
//        MessageDialog.question()
//                .withTitle(i18n("confirmation"))
//                .withMessage(i18n("onDelete"))
//                .withNoButton(() -> {
//                })
//                .withYesButton(() -> {
//                    invoiceService.remove(item);
//                    refreshGrid();
//                })
//                .show();
//    }
//}

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.foundation.ui.view.BaseForm;
import edu.wsiiz.repairshop.foundation.ui.view.ListView;
import edu.wsiiz.repairshop.foundation.ui.view.Mode;
import edu.wsiiz.repairshop.payments.application.InvoiceService;
import edu.wsiiz.repairshop.payments.domain.invoice.Invoice;
import jakarta.annotation.security.RolesAllowed;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.Consumer;

@Route("invoices")
@PageTitle("Invoices")
@RolesAllowed("ADMIN")
public class InvoiceListView extends ListView<Invoice> {


    private final InvoiceService invoiceService;

    public InvoiceListView(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;

        setFilters(new InvoiceFilters(this::refreshGrid));
        setupLayout();
    }


    @Override
    protected void setupGrid() {
        grid.addColumn(Invoice::getCustomer).setHeader("Client");
        grid.addColumn(Invoice::getIssueDate).setHeader("Date");
        grid.addColumn(Invoice::getStatus).setHeader("Status");
        grid.addColumn(Invoice::getTotalGrossAmount).setHeader("Amount");
    }

    @Override
    protected TriFunction<Invoice, Mode, Consumer<Invoice>, BaseForm<Invoice>> detailsFormSupplier() {
        // Dostarcza formularz faktury (InvoiceForm) do użycia w trybie CREATE lub EDIT.
        // Parametry:
        // - invoice: obiekt do edycji (lub pusty przy tworzeniu)
        // - mode: tryb działania (CREATE / EDIT)
        // - onSave: callback wywoływany po zapisaniu danych (np. odświeżenie listy)
        return (invoice, mode, onSave) -> new InvoiceForm(mode, invoice, invoiceService, onSave);
    }


    @Override
    protected void onDelete(Invoice invoice) {
        invoiceService.delete(invoice.getId());
        refreshGrid();
    }
}
