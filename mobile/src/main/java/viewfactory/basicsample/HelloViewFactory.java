package viewfactory.basicsample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import viewfactory.basicsample.controller.ViewFactory;

public final class HelloViewFactory implements ViewFactory, Parcelable {
  private final String name;
  private int count;

  public HelloViewFactory(String name) {
    this.name = name;
    count = 0;
  }

  protected HelloViewFactory(Parcel in) {
    name = in.readString();
    count = in.readInt();
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeInt(count);
  }

  @SuppressLint("SetTextI18n") @Override public View createView(Context context, ViewGroup parent) {
    ViewGroup root =
        (ViewGroup) LayoutInflater.from(context).inflate(R.layout.hello, parent, false);
    TextView hello = (TextView) root.getChildAt(0);
    View incrementer = root.getChildAt(1);
    final TextView countDisplay = (TextView) root.getChildAt(2);
    hello.setText(context.getString(R.string.hello, name));
    countDisplay.setText(Integer.toString(count));
    incrementer.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        count = Integer.valueOf(countDisplay.getText().toString()) + 1;
        countDisplay.setText(Integer.toString(count));
      }
    });
    return root;
  }

  @Override public int describeContents() {
    return 0;
  }

  public static final Creator<HelloViewFactory> CREATOR = new Creator<HelloViewFactory>() {
    @Override public HelloViewFactory createFromParcel(Parcel in) {
      return new HelloViewFactory(in);
    }

    @Override public HelloViewFactory[] newArray(int size) {
      return new HelloViewFactory[size];
    }
  };
}
