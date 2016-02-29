package viewfactory.basicsample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import java.util.ArrayList;
import viewfactory.basicsample.controller.Controller;
import viewfactory.basicsample.controller.Stacker;

public final class SampleActivity extends Activity {
  static final String WELCOME_ON_EDITOR_ACTION_LISTENER =
      "viewfactory.basicsample.WELCOME_ON_EDITOR_ACTION_LISTENER";
  private static final String KEY_STACK = "KEY_STACK";
  private static final String KEY_CURRENT_STATE = "KEY_CURRENT_STATE";

  private Stacker stacker;
  private OnEditorActionListener welcomeOnEditorActionListener;

  @SuppressWarnings({ "ResourceType", "WrongConstant" }) // Explicitly doing a custom service.
  static OnEditorActionListener getWelcomeOnEditorActionListener(Context context) {
    return (OnEditorActionListener) context.getSystemService(WELCOME_ON_EDITOR_ACTION_LISTENER);
  }

  @Override public Object getSystemService(String name) {
    if (WELCOME_ON_EDITOR_ACTION_LISTENER.equals(name)) {
      return welcomeOnEditorActionListener;
    }
    Object service = super.getSystemService(name);
    if (service != null) {
      return service;
    }
    return getApplication().getSystemService(name);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
    welcomeOnEditorActionListener = new OnEditorActionListener() {
      @Override public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        stacker.push(Controller.from(new HelloViewFactory(view.getText().toString())));
        return true;
      }
    };
    ArrayList<Controller.SavedState> stack;
    Controller current;
    if (savedInstanceState == null) {
      stack = new ArrayList<>(1);
      current = Controller.from(new WelcomeViewInflater());
    } else {
      stack = savedInstanceState.getParcelableArrayList(KEY_STACK);
      Controller.SavedState currentSavedState = savedInstanceState.getParcelable(KEY_CURRENT_STATE);
      current = Controller.from(currentSavedState);
    }
    stacker = new Stacker(this, root, 0, stack);
    stacker.push(current);
  }

  @Override public void onBackPressed() {
    if (!stacker.pop()) {
      super.onBackPressed();
    }
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    outState.putParcelableArrayList(KEY_STACK, stacker.getStack());
    outState.putParcelable(KEY_CURRENT_STATE, stacker.getCurrentState());
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
  }
}
