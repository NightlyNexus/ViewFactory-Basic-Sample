package viewfactory.basicsample;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import viewfactory.basicsample.controller.ViewFactory;

import static viewfactory.basicsample.SampleActivity.getWelcomeOnEditorActionListener;

final class WelcomeViewInflater implements ViewFactory, Parcelable {
  WelcomeViewInflater() {
  }

  WelcomeViewInflater(Parcel in) {
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
  }

  @Override public View createView(Context context, ViewGroup parent) {
    TextView.OnEditorActionListener welcomeOnEditorActionListener =
        getWelcomeOnEditorActionListener(context);
    TextView welcome =
        (TextView) LayoutInflater.from(context).inflate(R.layout.welcome, parent, false);
    welcome.setOnEditorActionListener(welcomeOnEditorActionListener);
    return welcome;
  }

  @Override public int describeContents() {
    return 0;
  }

  public static final Creator<WelcomeViewInflater> CREATOR = new Creator<WelcomeViewInflater>() {
    @Override public WelcomeViewInflater createFromParcel(Parcel in) {
      return new WelcomeViewInflater(in);
    }

    @Override public WelcomeViewInflater[] newArray(int size) {
      return new WelcomeViewInflater[size];
    }
  };
}
