package ua.com.android_helper.taxiinfo.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.com.android_helper.taxiinfo.R;
import ua.com.android_helper.taxiinfo.db.SQLiteContract;

/**
 * Created by andreyholovko on 3/8/14.
 */
public class PhoneCursorAdapter extends CursorAdapter {

    public PhoneCursorAdapter(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(cursor.getString(cursor.getColumnIndex(SQLiteContract.Details.COLUMN_DETAILS_NUMBER_VALUE)));

    }

    @Override
    public long getItemId(int position) {
        Cursor cursor = getCursor();
        if (cursor != null && cursor.moveToPosition(position)) {
            return cursor.getInt(cursor.getColumnIndex(SQLiteContract.Details._ID));

        }

        return 0;
    }

    @Override
    public String getItem(int position) {
        Cursor cursor = getCursor();
        if (cursor != null && cursor.moveToPosition(position)) {

            return cursor.getString(cursor.getColumnIndex(SQLiteContract.Details.COLUMN_DETAILS_NUMBER_VALUE));

        }
        return "";
        //return super.getItem(position);
    }

    public int getItemPosition(long id) {

        Cursor cursor = getCursor();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                if (cursor.getLong(cursor.getColumnIndex(SQLiteContract.Details._ID)) == id) {
                    return cursor.getPosition();

                }
                cursor.moveToNext();
            }
        }
        return 0;
    }
}
