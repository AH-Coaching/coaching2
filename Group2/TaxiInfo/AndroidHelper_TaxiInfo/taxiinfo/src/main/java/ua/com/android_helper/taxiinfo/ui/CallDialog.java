package ua.com.android_helper.taxiinfo.ui;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import ua.com.android_helper.taxiinfo.R;
import ua.com.android_helper.taxiinfo.adapters.PhoneCursorAdapter;
import ua.com.android_helper.taxiinfo.db.SQLiteContract;

/**
 * Created by andreyholovko on 3/13/14.
 */
public class CallDialog extends DialogFragment implements AdapterView.OnItemClickListener , LoaderManager.LoaderCallbacks<Cursor> {
    private ListView listView;
    private PhoneCursorAdapter _adapter;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog,null);
        Bundle bundle = getArguments();
        getDialog().setTitle(bundle.getString(SQLiteContract.Taxiname.COLUMN_TAXI_NAME));
        listView = (ListView)view.findViewById(R.id.dlg_call);
        getActivity().getSupportLoaderManager().initLoader(2,bundle,this);
        _adapter = new PhoneCursorAdapter(getActivity(),null);
        listView.setAdapter(_adapter);
        listView.setOnItemClickListener(this);
        Button button = (Button)view.findViewById(R.id.dialog_cancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  getDialog().cancel();

            }
        });
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
       String st=_adapter.getItem((_adapter.getItemPosition(l)));
        st= st.replace("(","").replace(")","").replace(" ","").replace("-","");
       Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+st));
       startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        getActivity().getSupportLoaderManager().restartLoader(2, bundle, this);

        //Registraciya broadcasta
        //      getActivity().registerReceiver(listReceiver, new IntentFilter("ua.com.android_helper.taxiinfo.ui.dataAdded"));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getSupportLoaderManager().destroyLoader(2);
//        getActivity().unregisterReceiver(listReceiver);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle args) {
        if (args!=null){
            String selection = SQLiteContract.Details.COLUMN_DETAILS_SERVICE_ID + " = ?";
            String[] selectionArgs = new String[]{String.valueOf(args.getLong(SQLiteContract.Taxiname._ID))};
            CursorLoader cursorLoader = new CursorLoader(getActivity(), SQLiteContract.Details.CONTENT_URI, null, selection, selectionArgs, null);
            return cursorLoader;

        }else {
            return new CursorLoader(getActivity(), SQLiteContract.Details.CONTENT_URI,null,null,null,null);
        }
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
}
