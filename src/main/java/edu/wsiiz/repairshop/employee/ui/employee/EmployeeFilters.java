package edu.wsiiz.repairshop.employee.ui.employee;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import edu.wsiiz.repairshop.employee.domain.employee.Employee;

import edu.wsiiz.repairshop.employee.domain.employee.Positions;
import edu.wsiiz.repairshop.foundation.ui.view.ListView.Filters;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.val;



import java.util.ArrayList;
import java.util.List;

public class EmployeeFilters extends Filters<Employee> {

  static final String POSITION = "position";

  ComboBox<Positions> position = new ComboBox<>(i18n(POSITION));

  public EmployeeFilters(Runnable onSearch) {
    super(onSearch);
    setupFilters();
  }

  @Override
  protected void setupFilters() {

    position.setItems(Positions.values());
    position.setItemLabelGenerator(this::i18n);

    HorizontalLayout filtersLayout = new HorizontalLayout(position);
    filtersLayout.setWidthFull();

    getContent().add(filtersLayout);
  }

  @Override
  protected void onReset() {

    position.clear();

    triggerSearch();
  }

  @Override
  public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

    List<Predicate> predicates = new ArrayList<>();

    val stat = position.getValue();
    if (stat != null) {
      predicates.add(cb.in(root.get(POSITION)).value(stat));
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }

}