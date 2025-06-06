package edu.wsiiz.repairshop.auth.security;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
public class VaadinSecurityListenerConfig implements VaadinServiceInitListener {
    private final SecurityRouteFilter securityRouteFilter;

    @Autowired
    public VaadinSecurityListenerConfig(SecurityRouteFilter securityRouteFilter) {
        this.securityRouteFilter = securityRouteFilter;
    }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent ->
                uiEvent.getUI().addBeforeEnterListener(this.securityRouteFilter)
        );
    }
}
