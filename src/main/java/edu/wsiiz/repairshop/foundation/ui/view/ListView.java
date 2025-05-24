package edu.wsiiz.repairshop.foundation.ui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.Border;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import edu.wsiiz.repairshop.foundation.ui.component.Grid;
import edu.wsiiz.repairshop.foundation.ui.i18n.I18nAware;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.data.jpa.domain.Specification;
import org.vaadin.lineawesome.LineAwesomeIcon;

public abstract class ListView<T> extends BaseView {

  protected final Grid<T> grid;
  protected Span countField = new Span();

  @Setter
  @Getter
  private Filters<T> filters;

  protected ListView() {
    this.grid = new Grid<>(this.getClass());
    this.getContent().setHeightFull();
  }

  protected void setupLayout() {
    VerticalLayout mainLayout = viewLayout;
    mainLayout.add(createFilterComponent(), createToolbar(), createGridComponent());
    mainLayout.setSizeFull();
    mainLayout.setPadding(false);
    mainLayout.setSpacing(false);
    mainLayout.setAlignItems(Alignment.CENTER);
  }

  protected Component createFilterComponent() {

    VerticalLayout filterLayout = new VerticalLayout();

    filterLayout.setWidthFull();
    filterLayout.setSpacing(false);
    filterLayout.addClassNames(Border.ALL, Padding.SMALL);

    filterLayout.add(filters);

    Button resetBtn = new Button(VaadinIcon.CLOSE_SMALL.create());
    resetBtn.getElement().getStyle().set("margin-left", "auto");
    resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    resetBtn.addClickListener(e -> filters.onReset());

    Button searchBtn = new Button(LineAwesomeIcon.SEARCH_SOLID.create());
    searchBtn.addClickListener(e -> filters.triggerSearch());

    HorizontalLayout toolbar = new HorizontalLayout();
    toolbar.setWidthFull();
    toolbar.addClassNames(Padding.SMALL);
    toolbar.setMargin(false);
    toolbar.setPadding(false);

    val countLabel = new Span("Liczba elementów spełniających kryteria:");
    HorizontalLayout actions = new HorizontalLayout(countLabel, countField, resetBtn, searchBtn);
    actions.setWidthFull();
    actions.setAlignItems(Alignment.END);
    actions.setSpacing(true);

    toolbar.add(actions);

    filterLayout.add(toolbar);

    return filterLayout;
  }

  public abstract static class Filters<T> extends Composite<VerticalLayout> implements Specification<T>, I18nAware {

    protected final Runnable onSearch;

    protected Filters(Runnable onSearch) {
      this.onSearch = onSearch;
      this.getContent().setPadding(false);
    }

    protected abstract void setupFilters();

    protected abstract void onReset();

    protected void triggerSearch() {
      onSearch.run();
    }

    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
      return criteriaBuilder.conjunction();
    }

  }

  protected Component createToolbar() {

    HorizontalLayout toolbar = new HorizontalLayout();
    toolbar.setWidthFull();
    toolbar.addClassNames(Padding.SMALL);
    toolbar.setMargin(false);
    toolbar.setPadding(false);

    HorizontalLayout actions = new HorizontalLayout();
    addAdditionalActions(actions);
    addStandardActions(actions);

    toolbar.add(actions);

    return toolbar;
  }

  protected void addStandardActions(HorizontalLayout actions) {
    actions.setWidthFull();
    actions.setAlignItems(Alignment.END);

    Button previewBtn = new Button(LineAwesomeIcon.SEARCH_PLUS_SOLID.create());
    previewBtn.addClickListener(e -> runOnSelected(this::onPreview));
    previewBtn.getElement().getStyle().set("margin-left", "auto");

    Button editBtn = new Button(LineAwesomeIcon.EDIT.create());
    editBtn.addClickListener(e -> runOnSelected(this::onEdit));

    Button addBtn = new Button(LineAwesomeIcon.PLUS_SOLID.create());
    addBtn.addClickListener(e -> onAdd());

    Button deleteBtn = new Button(LineAwesomeIcon.ERASER_SOLID.create());
    deleteBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
    deleteBtn.addClickListener(e -> runOnSelected(this::onDelete));

    actions.add(previewBtn, editBtn, addBtn, deleteBtn);
  }

  protected void addAdditionalActions(HorizontalLayout actions) {
  }

  protected void runOnSelected(Consumer<T> action) {
    grid.getSelectedItems().stream()
        .findFirst()
        .ifPresentOrElse(action, () -> showNotifation(i18n(ListView.class, "noItem")));
  }

  private void showDetailsDialog(T item, Mode mode) {
    val dialog = new Dialog();
    dialog.setMinWidth("40em");
    val cf = detailsFormSupplier().apply(item, mode, m -> {
      dialog.close();
      if (mode.isEdit()) {
        grid.getDataProvider().refreshItem(m);
      } else if (mode.isAdd()) {
        grid.getDataProvider().refreshAll();
      }
    });
    cf.init(dialog);
    dialog.add(cf);
    dialog.open();
  }

  protected void onAdd() {
    showDetailsDialog(null, Mode.ADD);
  }

  protected void onEdit(T item) {
    showDetailsDialog(item, Mode.EDIT);
  }

  protected void onPreview(T item) {
    showDetailsDialog(item, Mode.VIEW);
  }

  protected TriFunction<T, Mode, Consumer<T>, BaseForm<T>> detailsFormSupplier() {
    return null;
  }

  protected void onDelete(T item) {
  }

  protected abstract void setupGrid();

  protected Component createGridComponent() {
    grid.setSizeFull();
    setupGrid();
    return grid;
  }

  protected void refreshGrid() {
    grid.getDataProvider().refreshAll();
    grid.deselectAll();
  }

}