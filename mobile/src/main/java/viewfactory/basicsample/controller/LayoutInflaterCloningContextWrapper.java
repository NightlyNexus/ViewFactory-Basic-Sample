package viewfactory.basicsample.controller;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;

public class LayoutInflaterCloningContextWrapper extends ContextWrapper {
  private LayoutInflater inflater;

  public LayoutInflaterCloningContextWrapper(Context base) {
    super(base);
    inflater = null;
  }

  @Override public Object getSystemService(String name) {
    if (LAYOUT_INFLATER_SERVICE.equals(name)) {
      if (inflater == null) {
        inflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
      }
      return inflater;
    }
    return super.getSystemService(name);
  }
}
