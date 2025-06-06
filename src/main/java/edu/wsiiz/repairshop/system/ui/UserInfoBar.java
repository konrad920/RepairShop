package edu.wsiiz.repairshop.system.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import edu.wsiiz.repairshop.audit.application.AuditService;
import edu.wsiiz.repairshop.auth.application.CurrentUser;
import edu.wsiiz.repairshop.auth.domain.user.User;

public class UserInfoBar extends HorizontalLayout {
    private final AuditService auditService;



    public UserInfoBar(AuditService auditService) {
        this.auditService = auditService;
        setSpacing(true);
        setWidthFull();
        setJustifyContentMode(JustifyContentMode.END);
        setAlignItems(Alignment.CENTER);

        User user = CurrentUser.get();


        if (user != null) {
            Span username = new Span("Użytkownik: " + user.getUsername());
            Span role = new Span("Rola: " + user.getFirstRole());

            Button logoutButton = new Button("Wyloguj", e -> {
                auditService.log(user.getUsername(), "WYLOGOWANIE");
                CurrentUser.logout();
                UI.getCurrent().navigate("login");
                UI.getCurrent().getPage().reload();
            });

            add(username, role, logoutButton);
        } else {
            Button registerButton = new Button("Rejestracja", e -> UI.getCurrent().navigate("register"));
            add(registerButton);
            Button loginButton = new Button("Zaloguj", e -> UI.getCurrent().navigate("login"));
            add(loginButton);
        }
    }
}
