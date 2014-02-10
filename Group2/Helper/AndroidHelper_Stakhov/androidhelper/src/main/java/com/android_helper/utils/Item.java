package com.android_helper.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andriistakhov on 09.02.14.
 */
public class Item implements Parcelable {

    private String title;
    private int id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


}
