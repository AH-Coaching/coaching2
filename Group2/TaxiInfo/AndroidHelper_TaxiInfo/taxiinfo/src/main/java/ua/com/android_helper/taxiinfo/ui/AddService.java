package ua.com.android_helper.taxiinfo.ui;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import ua.com.android_helper.taxiinfo.R;
import ua.com.android_helper.taxiinfo.adapters.CityCursorAdapterSpinner;
import ua.com.android_helper.taxiinfo.db.SQLiteContract;


/**
 * Created by andreyholovko on 3/22/14.
 */
public class AddService extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private CityCursorAdapterSpinner cursorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addservice, container, false);

        getActivity().getSupportLoaderManager().initLoader(3, null, this);

        Spinner s2 = (Spinner)view.findViewById(R.id.city_choose);
        Cursor cur = getActivity().getContentResolver().query(SQLiteContract.City.CONTENT_URI, null, null, null,null);


        SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                cur, new String[] {SQLiteContract.City.COLUMN_CITY_NAME}, new int[] {android.R.id.text1}); // The "text1" view defined in
        // the XML template

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter2);

        cursorAdapter = new CityCursorAdapterSpinner(getActivity(), null);
//        Spinner spinner = (Spinner) view.findViewById(R.id.city_choose);
//
//        spinner.setAdapter(cursorAdapter);


        Button button = (Button) view.findViewById(R.id.btn_newcity);
        button.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_newcity:
                DialogFragment dialogAddCity = new AddCityDialog();
                dialogAddCity.show(getFragmentManager(), "dialogCall");
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle args) {
        if (args != null) {
            String selection = SQLiteContract.City.COLUMN_CITY_ID + " = ?";
            String[] selectionArgs = new String[]{String.valueOf(args.getInt(SQLiteContract.City.COLUMN_CITY_ID))};
            CursorLoader cursorLoader = new CursorLoader(getActivity(), SQLiteContract.City.CONTENT_URI, null, selection, selectionArgs, null);

            return cursorLoader;

        } else {


            return new CursorLoader(getActivity(), SQLiteContract.City.CONTENT_URI, null, null, null, null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (cursorAdapter != null) {
            cursorAdapter.swapCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        if (cursorAdapter != null) {
            cursorAdapter.swapCursor(null);
        }
    }
}
