package viewfactory.basicsample.controller;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

public final class Controller {
  public static Controller from(ViewFactory factory) {
    return new Controller(factory, null);
  }

  public static Controller from(SavedState savedState) {
    return new Controller(savedState.factory, savedState.state);
  }

  private final ViewFactory factory;
  private final SparseArray<Parcelable> state;
  private View view;

  private Controller(ViewFactory factory, SparseArray<Parcelable> state) {
    this.factory = factory;
    this.state = state;
    view = null;
  }

  public View getView() {
    return view;
  }

  public View createView(Context context, ViewGroup parent) {
    view = factory.createView(context, parent);
    view.setSaveFromParentEnabled(false);
    if (state != null) {
      view.restoreHierarchyState(state);
    }
    return view;
  }

  public SavedState save() {
    SparseArray<Parcelable> state = new SparseArray<>();
    view.saveHierarchyState(state);
    return new SavedState(factory, state);
  }

  public static final class SavedState implements Parcelable {
    final ViewFactory factory;
    final SparseArray<Parcelable> state;

    SavedState(ViewFactory factory, SparseArray<Parcelable> state) {
      this.factory = factory;
      this.state = state;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeParcelable(factory, flags);
      writeSparseArray(dest, state);
    }

    public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
      @Override public SavedState createFromParcel(Parcel in) {
        ClassLoader classLoader = SavedState.class.getClassLoader();
        ViewFactory factory = in.readParcelable(classLoader);
        SparseArray<Parcelable> state = readSparseArray(in, classLoader);
        return new SavedState(factory, state);
      }

      @Override public SavedState[] newArray(int size) {
        return new SavedState[size];
      }
    };

    @Override public int describeContents() {
      return 0;
    }

    static <T> SparseArray<T> readSparseArray(Parcel in, ClassLoader loader) {
      int size = in.readInt();
      if (size < 0) {
        return null;
      }
      SparseArray<T> sa = new SparseArray<>(size);
      while (size > 0) {
        int key = in.readInt();
        T value = (T) in.readValue(loader);
        sa.append(key, value);
        size--;
      }
      return sa;
    }

    static <T> void writeSparseArray(Parcel dest, SparseArray<T> val) {
      if (val == null) {
        dest.writeInt(-1);
        return;
      }
      int size = val.size();
      dest.writeInt(size);
      int i = 0;
      while (i < size) {
        dest.writeInt(val.keyAt(i));
        dest.writeValue(val.valueAt(i));
        i++;
      }
    }
  }
}

