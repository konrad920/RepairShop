package edu.wsiiz.repairshop.audit.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.audit.application.AuditService;
import edu.wsiiz.repairshop.audit.domain.AuditLog;
import edu.wsiiz.repairshop.auth.domain.user.UserRole;
import edu.wsiiz.repairshop.auth.security.Secured;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Route("admin/audit")
@PageTitle("Log audytowy")
@RolesAllowed("ADMIN")
@Secured(roles = {UserRole.ADMIN})
public class AuditLogView extends VerticalLayout {

    private final AuditService auditService;

    public AuditLogView(AuditService auditService) {
        this.auditService = auditService;

        Grid<AuditLog> grid = new Grid<>(AuditLog.class);
        grid.setColumns("timestamp", "username", "action");

        List<AuditLog> logs = auditService.getAll();
        grid.setItems(logs);

        add(grid);
    }
}