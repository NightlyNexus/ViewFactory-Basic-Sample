package viewfactory.basicsample.controller;

import android.content.Context;
import android.view.ViewGroup;
import java.util.ArrayList;

public final class Stacker {
  private final Context context;
  private final ViewGroup container;
  private final int index;
  private final ArrayList<Controller.SavedState> stack;
  private Controller current = null;

  public Stacker(Context context, ViewGroup container, int index,
      ArrayList<Controller.SavedState> stack) {
    this.context = context;
    this.container = container;
    this.index = index;
    this.stack = stack;
    current = null;
  }

  public void push(Controller controller) {
    removeAndPush();
    add(controller);
  }

  public void replace(Controller controller) {
    remove();
    add(controller);
  }

  public boolean pop() {
    if (stack.isEmpty()) {
      return false;
    }
    container.removeViewAt(index);
    Controller.SavedState savedState = stack.remove(stack.size() - 1);
    add(Controller.from(savedState));
    return true;
  }

  public boolean isStackEmpty() {
    return stack.isEmpty();
  }

  public Controller.SavedState getCurrentState() {
    return current.save();
  }

  public ArrayList<Controller.SavedState> getStack() {
    return stack;
  }

  private void add(Controller controller) {
    current = controller;
    container.addView(controller.createView(context, container), index);
  }

  private void remove() {
    if (current != null) {
      container.removeViewAt(index);
    }
  }

  private void removeAndPush() {
    if (current != null) {
      container.removeViewAt(index);
      stack.add(current.save());
    }
  }
}
