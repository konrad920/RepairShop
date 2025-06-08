package edu.wsiiz.repairshop.auth.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.audit.application.AuditService;
import edu.wsiiz.repairshop.auth.application.CurrentUser;
import edu.wsiiz.repairshop.auth.application.UserService;
import edu.wsiiz.repairshop.auth.domain.user.User;
import edu.wsiiz.repairshop.foundation.ui.component.MessageDialog;

@Route("login")
@PageTitle("Logowanie")
public class LoginView extends VerticalLayout {

    public LoginView(UserService userService, AuditService auditService) {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        H1 title = new H1("Logowanie do systemu");

        TextField usernameField = new TextField("Login");
        PasswordField passwordField = new PasswordField("Hasło");
        Button loginButton = new Button("Zaloguj");

        loginButton.addClickListener(event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();

            User user = userService.findByUsername(username);

            if (user != null && user.getPassword().equals(password)) {
                CurrentUser.set(user);

                auditService.log(user.getUsername(), "LOGOWANIE");

                MessageDialog.success()
                        .withTitle("Zalogowano")
                        .withMessage("Witaj, " + user.getUsername())
                        .withOkButton(() -> {
                            UI ui = UI.getCurrent();
                            ui.navigate("");
                            ui.getPage().executeJs("setTimeout(() => window.location.reload(), 50)");
                        })
                        .show();
            } else {
                MessageDialog.error()
                        .withTitle("Błąd logowania")
                        .withMessage("Nieprawidłowy login lub hasło.")
                        .withOkButton(null)
                        .show();
            }
        });

        add(title, usernameField, passwordField, loginButton);
    }
}
