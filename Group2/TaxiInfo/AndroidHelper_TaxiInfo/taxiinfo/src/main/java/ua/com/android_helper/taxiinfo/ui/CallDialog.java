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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import ua.com.android_helper.taxiinfo.R;
import ua.com.android_helper.taxiinfo.adapters.PhoneCursorAdapter;
import ua.com.android_helper.taxiinfo.db.SQLiteContract;

/**
 * Created by andreyholovko on 3/13/14.
 */
public class CallDialog extends DialogFragment implements AdapterView.OnItemClickListener , LoaderManager.LoaderCallbacks<Cursor> {
    private String[] list = new String[]{"Test2", "Test3", "Test4", "Test5", "Test6"};
    private ListView listView;
    private PhoneCursorAdapter _adapter;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Here we are");
        View view = inflater.inflate(R.layout.fragment_dialog,null);
        listView = (ListView)view.findViewById(R.id.dlg_call);
        getActivity().getSupportLoaderManager().initLoader(2,null,this);
        _adapter = new PhoneCursorAdapter(getActivity(),null);
     //   ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,android.R.id.text1,list);
        listView.setAdapter(_adapter);
        listView.setOnItemClickListener(this);
        Button button = (Button)view.findViewById(R.id.dialog_cancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"04455658595"));
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle args) {
        if (args!=null){
            String selection = SQLiteContract.Details.COLUMN_DETAILS_SERVICE_ID + " = ?";
            String[] selectionArgs = new String[]{String.valueOf(args.getInt(SQLiteContract.Details.COLUMN_DETAILS_SERVICE_ID))};
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
