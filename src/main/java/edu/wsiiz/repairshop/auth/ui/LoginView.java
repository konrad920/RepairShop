package edu.wsiiz.repairshop.auth.ui;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.UI;
import edu.wsiiz.repairshop.auth.application.CurrentUser;
import edu.wsiiz.repairshop.auth.application.UserService;
import edu.wsiiz.repairshop.auth.domain.User;


@Route("login")
@PageTitle("Logowanie")
public class LoginView extends VerticalLayout {
    public LoginView(UserService userService) {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        TextField usernameField = new TextField("Login");
        PasswordField passwordField = new PasswordField("Hasło");
        Button loginButton = new Button("Zaloguj");

        loginButton.addClickListener(event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();

            User user = userService.findByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                CurrentUser.set(user); // zapisujemy do sesji
                UI.getCurrent().navigate(""); // przekieruj na stronę główną
            } else {
                Notification.show("Nieprawidłowy login lub hasło", 3000, Notification.Position.MIDDLE);
            }
        });

        add(usernameField, passwordField, loginButton);
    }
}