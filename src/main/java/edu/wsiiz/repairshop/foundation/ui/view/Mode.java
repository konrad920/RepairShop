package edu.wsiiz.repairshop.foundation.ui.view;

public enum Mode {

  ADD,
  EDIT,
  VIEW;

  public boolean isAdd() {
    return this == ADD;
  }

  public boolean isEdit() {
    return this == EDIT;
  }

  public boolean isView() {
    return this == VIEW;
  }

}
