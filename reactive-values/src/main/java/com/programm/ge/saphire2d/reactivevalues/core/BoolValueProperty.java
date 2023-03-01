package com.programm.ge.saphire2d.reactivevalues.core;

public class BoolValueProperty extends BoolProperty {

  private boolean value;

  public BoolValueProperty()
  {
    this(false);
  }

  public BoolValueProperty(boolean value)
  {
    this.value = value;
  }

  @Override
  public Boolean get()
  {
    return value;
  }

  @Override
  protected void setDirectly(Boolean value)
  {
    this.value = value;
  }

}
