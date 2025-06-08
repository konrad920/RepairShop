package edu.wsiiz.repairshop.payments.ui.invoice;

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
        grid.addColumn("invoiceNumber", Invoice::getId);
        grid.addColumn("status", Invoice::getStatus);
        grid.addColumn("issueDate", Invoice::getIssueDate);
    }

    @Override
    protected TriFunction<Invoice, Mode, Consumer<Invoice>, BaseForm<Invoice>> detailsFormSupplier() {
        return (invoice, mode, onSave) -> new InvoiceForm(mode, invoice, invoiceService, onSave);
    }


    @Override
    protected void onDelete(Invoice invoice) {
        invoiceService.delete(invoice.getId());
        refreshGrid();
    }
}
