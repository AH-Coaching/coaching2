package ua.com.android_helper.taxiinfo.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ua.com.android_helper.taxiinfo.R;
import ua.com.android_helper.taxiinfo.adapters.CityCursorAdapter;
import ua.com.android_helper.taxiinfo.adapters.CityCursorAdapterSpinner;
import ua.com.android_helper.taxiinfo.db.SQLiteContract;

/**
 * Created by andreyholovko on 3/23/14.
 */
public class AddCityDialog extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().setTitle(getString(R.string.label_addcity));
        View view = inflater.inflate(R.layout.fragment_addcity,null);



        Button button = (Button)view.findViewById(R.id.btn_cancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();

            }
        });

        final EditText editText = (EditText) view.findViewById(R.id.new_city_name);
        Button button2 = (Button)view.findViewById(R.id.btn_addcity);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> list=new ArrayList<String>();
                list.add(editText.getText().toString());
                Spinner spinner = (Spinner) getActivity().findViewById(R.id.city_choose);
                ArrayAdapter<String> adp1=new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1,list);
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adp1);
                getDialog().dismiss();
            }
        });
        return view;
    }

}
