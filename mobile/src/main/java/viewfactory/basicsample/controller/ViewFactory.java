package viewfactory.basicsample.controller;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

public interface ViewFactory extends Parcelable {
  View createView(Context context, ViewGroup parent);
}
