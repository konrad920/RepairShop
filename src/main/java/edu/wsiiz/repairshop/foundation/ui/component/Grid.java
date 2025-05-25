package edu.wsiiz.repairshop.foundation.ui.component;

import com.vaadin.flow.function.ValueProvider;
import edu.wsiiz.repairshop.foundation.ui.i18n.I18nAware;

public class Grid<T> extends com.vaadin.flow.component.grid.Grid<T> implements I18nAware {

  private final Class<?> ownerClass;

  public Grid(Class<?> ownerClass) {
    this.ownerClass = ownerClass;
  }

  public Column<T> addColumn(String columnId, ValueProvider<T, ?> valueProvider) {
    return this.addColumn(valueProvider)
            .setHeader(i18n(ownerClass, columnId))
            .setAutoWidth(true);
  }

}
