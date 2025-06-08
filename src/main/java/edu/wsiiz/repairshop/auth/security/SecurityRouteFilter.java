package edu.wsiiz.repairshop.auth.security;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.VaadinSession;
import edu.wsiiz.repairshop.auth.application.CurrentUser;
import edu.wsiiz.repairshop.auth.domain.user.User;
import edu.wsiiz.repairshop.auth.domain.user.UserRole;
import edu.wsiiz.repairshop.auth.ui.LoginView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecurityRouteFilter implements BeforeEnterListener {
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Class<?> navigationTarget = event.getNavigationTarget();

        Secured secured = navigationTarget.getAnnotation(Secured.class);
        if (secured == null) {
            return;
        }

//        User user = VaadinSession.getCurrent().getAttribute(User.class);
        User user = CurrentUser.get();
        if (user == null) {
            event.forwardTo(LoginView.class);
            return;
        }

        UserRole userRole = user.getRole().getName();
        List<UserRole> allowedRoles = List.of(secured.roles());

        if (!allowedRoles.contains(userRole)) {
            event.forwardTo("/");
        }
    }
}
