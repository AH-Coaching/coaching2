package com.android_helper.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android_helper.R;
import com.android_helper.adapter.AHCustomCursorAdapter;
import com.android_helper.db.SQLiteContract;

/**
 * Created by andriistakhov on 10.11.13.
 */
public class ListItemFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private AHCustomCursorAdapter cursorAdapter;
    private AHListReceiver listReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView listView = (ListView) view.findViewById(R.id.list_items);
        String[] array = getArguments().getStringArray("list");


        getActivity().getSupportLoaderManager().initLoader(0, null, this);

        cursorAdapter = new AHCustomCursorAdapter(getActivity(), null);

        listView.setAdapter(cursorAdapter);

        listReceiver = new AHListReceiver();

    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().getSupportLoaderManager().restartLoader(0, null, this);

        getActivity().registerReceiver(listReceiver, new IntentFilter("com.ua.android_helper.androidhelper.dataAdded"));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getSupportLoaderManager().destroyLoader(0);
        getActivity().unregisterReceiver(listReceiver);
    }

    public void reload() {
//        Bundle args = new Bundle();
//        args.putInt(SQLiteContract.Items.COLUMN_ITEM_PARENT_ID, getArguments().getInt(SQLiteContract.Items.COLUMN_ITEM_PARENT_ID));
        getActivity().getSupportLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (args != null) {
            String selection = SQLiteContract.Items.COLUMN_ITEM_PARENT_ID + " = ?";
            String[] selectionArgs = new String[]{String.valueOf(args.getInt(SQLiteContract.Items.COLUMN_ITEM_PARENT_ID))};
            CursorLoader cursorLoader = new CursorLoader(getActivity(), SQLiteContract.Items.CONTENT_URI, null, selection, selectionArgs, null);
            return cursorLoader;
        } else {
            return new CursorLoader(getActivity(), SQLiteContract.Items.CONTENT_URI, null, null, null, null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (cursorAdapter != null) {
            cursorAdapter.swapCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (cursorAdapter != null) {
            cursorAdapter.swapCursor(null);
        }
    }

    public class AHListReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.ua.android_helper.androidhelper.dataAdded")) {
                Toast.makeText(context, "Need reload", Toast.LENGTH_LONG).show();
                reload();
            }
        }
    }
}