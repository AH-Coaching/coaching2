package ua.com.androidhelper.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.com.androidhelper.R;
import ua.com.androidhelper.ui.db.SQLiteContract;

/**
 * Created by Androidsmith on 21.02.14.
 */
public class AHCustomCursorAdapter extends CursorAdapter {
    public AHCustomCursorAdapter(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.custom_list, null, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView) view.findViewById(R.id.txt_item);
        textView.setText(cursor.getString(cursor.getColumnIndex(SQLiteContract.Items.COLUMN_ITEM_TEXT)));

    }
}
