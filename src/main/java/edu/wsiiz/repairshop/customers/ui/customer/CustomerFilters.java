package edu.wsiiz.repairshop.customers.ui.customer;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import edu.wsiiz.repairshop.communication.domain.contact.ContactStatus;
import edu.wsiiz.repairshop.customers.domain.customer.Customer;
import edu.wsiiz.repairshop.foundation.ui.view.ListView.Filters;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class CustomerFilters extends Filters<Customer> {

  static final String STATUS = "status";

  ComboBox<ContactStatus> status = new ComboBox<>(i18n(STATUS));

  public CustomerFilters(Runnable onSearch) {
    super(onSearch);
    setupFilters();
  }

  @Override
  protected void setupFilters() {

    status.setItems(ContactStatus.values());
    status.setItemLabelGenerator(this::i18n);

    HorizontalLayout filtersLayout = new HorizontalLayout(status);
    filtersLayout.setWidthFull();

    getContent().add(filtersLayout);
  }

  @Override
  protected void onReset() {

    status.clear();

    triggerSearch();
  }

  @Override
  public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

    List<Predicate> predicates = new ArrayList<>();

    val stat = status.getValue();
    if (stat != null) {
      predicates.add(cb.in(root.get(STATUS)).value(stat));
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }

}