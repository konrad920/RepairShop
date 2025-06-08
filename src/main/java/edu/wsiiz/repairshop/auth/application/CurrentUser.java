package edu.wsiiz.repairshop.auth.application;

import com.vaadin.flow.server.VaadinSession;
import edu.wsiiz.repairshop.auth.domain.user.User;

public class CurrentUser {

    private static final String CURRENT_USER_KEY = "currentUser";

    public static void set(User user) {
        VaadinSession.getCurrent().setAttribute(CURRENT_USER_KEY, user);
    }

    public static User get() {
        return (User) VaadinSession.getCurrent().getAttribute(CURRENT_USER_KEY);
    }

    public static void logout() {
        VaadinSession.getCurrent().setAttribute(CURRENT_USER_KEY, null);
    }
}
