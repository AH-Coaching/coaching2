package ua.com.androidhelper.ui.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Androidsmith on 20.02.14.
 */
public class Item implements Parcelable {

    private String title;
    private int id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
