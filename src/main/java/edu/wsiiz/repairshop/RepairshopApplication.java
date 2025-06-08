package edu.wsiiz.repairshop;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(value = "repairshop")
public class RepairshopApplication implements AppShellConfigurator {

  public static void main(String[] args) {
    SpringApplication.run(RepairshopApplication.class, args);
  }

	
}
