package ua.com.android_helper.taxiinfo.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import ua.com.android_helper.taxiinfo.R;


import ua.com.android_helper.taxiinfo.adapters.TaxinameCursorAdapter;
import ua.com.android_helper.taxiinfo.db.SQLiteContract;
import ua.com.android_helper.taxiinfo.utils.Keys;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class MainListFragment extends Fragment implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {


    DialogFragment dialogCall;
    private TaxinameCursorAdapter _adapter;
    private AHListReceiver listReceiver;

    public MainListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        getActivity().getSupportLoaderManager().initLoader(1, bundle, this);

        ListView listView = (ListView) inflater.inflate(R.layout.fragment_main, container, false);

        _adapter = new TaxinameCursorAdapter(getActivity(), null);


        //Example how add the header
        // LinearLayout header = (LinearLayout)inflater.inflate(R.layout.header_list,null,false);
        /*if (listView != null) {
            listView.addHeaderView(header);
        }*/
        listView.setAdapter(_adapter);
        listView.setOnItemClickListener(this);
        return listView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
/*
        Bundle bundle = getArguments();
        if (bundle != null) {
            _id = bundle.getInt(Keys.KEY_ID);
            _title = bundle.getString(Keys.KEY_TITLE);
            ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(_title);
        }*/

    /*    if (_id == 0) {
            _adapter.filllist(Arrays.asList(list));
        }else if (_id == 1) {
            _adapter.filllist(Arrays.asList(list2));
        }else if (_id == 2) {
            _adapter.filllist(Arrays.asList(list3));
        } else {
            _adapter.filllist(Arrays.asList(list4));
        }*/
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        dialogCall = new CallDialog();
        Bundle bundle = new Bundle();
        bundle.putLong(SQLiteContract.Taxiname._ID,_adapter.getItemId(i));
        bundle.putString(SQLiteContract.Taxiname.COLUMN_TAXI_NAME,_adapter.getItem(i));
        dialogCall.setArguments(bundle);
        dialogCall.show(getFragmentManager(), "dialogCall");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle args) {
        if (args != null) {

         String selection = SQLiteContract.Taxiname.COLUMN_CITY_ID + " = ?";
          String[] selectionArgs = new String[]{String.valueOf(args.getLong(SQLiteContract.Taxiname.COLUMN_CITY_ID))};
            CursorLoader cursorLoader = new CursorLoader(getActivity(), SQLiteContract.Taxiname.CONTENT_URI, null, selection, selectionArgs, null);
            return cursorLoader;

        } else {
            return new CursorLoader(getActivity(), SQLiteContract.Taxiname.CONTENT_URI, null, null, null, null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        getActivity().getSupportLoaderManager().restartLoader(1, bundle, this);

        //Registraciya broadcasta
  //      getActivity().registerReceiver(listReceiver, new IntentFilter("ua.com.android_helper.taxiinfo.ui.dataAdded"));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getSupportLoaderManager().destroyLoader(1);
//        getActivity().unregisterReceiver(listReceiver);
    }

    public void reload() {
        Bundle args = new Bundle();
//        args.putInt(SQLiteContract.Items.COLUMN_ITEM_PARENT_ID, getArguments().getInt(SQLiteContract.Items.COLUMN_ITEM_PARENT_ID));
        args.putInt(SQLiteContract.City.COLUMN_CITY_ID, getArguments().getInt(SQLiteContract.City.COLUMN_CITY_ID));

        getActivity().getSupportLoaderManager().restartLoader(1, null, this);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (_adapter != null) {
            _adapter.swapCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        if (_adapter != null) {
            _adapter.swapCursor(null);
        }
    }

    public class AHListReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("ua.com.android_helper.taxiinfo.ui.dataAdded")) {
                Toast.makeText(context, "Need reload", Toast.LENGTH_LONG).show();
                reload();
            }
        }
    }
}