package edu.wsiiz.repairshop.auth.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.wsiiz.repairshop.auth.application.UserService;
import edu.wsiiz.repairshop.auth.domain.user.Role;
import edu.wsiiz.repairshop.auth.domain.user.User;
import edu.wsiiz.repairshop.auth.domain.user.UserRole;

import java.util.Collections;

@Route("register")
@PageTitle("Rejestracja")
public class RegisterView extends VerticalLayout {

    public RegisterView(UserService userService) {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        H1 title = new H1("Rejestracja");

        TextField usernameField = new TextField("Login");
        PasswordField passwordField = new PasswordField("Hasło");

        ComboBox<UserRole> roleComboBox = new ComboBox<>("Rola");
        roleComboBox.setItems(UserRole.values());
        roleComboBox.setValue(UserRole.CUSTOMER); // domyślnie

        Button registerButton = new Button("Zarejestruj");

        registerButton.addClickListener(e -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            UserRole roleName = roleComboBox.getValue();

            if (username.isEmpty() || password.isEmpty() || roleName == null) {
                Notification.show("Uzupełnij wszystkie pola", 3000, Notification.Position.MIDDLE);
                return;
            }

            try {
                Role role = userService.findOrCreateRole(roleName);
                User user = new User(username, password, Collections.singleton(role));
                userService.save(user);

                Notification.show("Rejestracja zakończona sukcesem!", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().navigate("login");

            } catch (Exception ex) {
                Notification.show("Błąd: " + ex.getMessage(), 5000, Notification.Position.MIDDLE);
            }
        });

        add(title, usernameField, passwordField, roleComboBox, registerButton);
    }
}
