package ua.com.android_helper.taxiinfo.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.com.android_helper.taxiinfo.R;
import ua.com.android_helper.taxiinfo.db.SQLiteContract;

/**
 * Created by andreyholovko on 3/8/14.
 */
public class CityCursorAdapterSpinner extends CursorAdapter {


    public CityCursorAdapterSpinner(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.fragment_navigationdrawer_list, null, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
            TextView textView = (TextView) view.findViewById(R.id.txt_item);
            textView.setText(cursor.getString(cursor.getColumnIndex(SQLiteContract.City.COLUMN_CITY_NAME)));
        }

    @Override
    public long getItemId(int position) {
        Cursor cursor = getCursor();
        if (cursor != null && cursor.moveToPosition(position)) {
            return cursor.getInt(cursor.getColumnIndex(SQLiteContract.City._ID));

        }

            return 0;
    }

       @Override
    public String getItem(int position) {
        Cursor cursor = getCursor();
        if (cursor != null && cursor.moveToPosition(position)) {
            return cursor.getString(cursor.getColumnIndex(SQLiteContract.City.COLUMN_CITY_NAME));

        }
        return "";
        //return super.getItem(position);
    }
}
