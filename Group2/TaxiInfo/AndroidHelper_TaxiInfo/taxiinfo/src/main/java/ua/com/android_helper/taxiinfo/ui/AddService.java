package ua.com.android_helper.taxiinfo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ua.com.android_helper.taxiinfo.R;
import ua.com.android_helper.taxiinfo.adapters.TaxinameCursorAdapter;

/**
 * Created by andreyholovko on 3/22/14.
 */
public class AddService extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_addservice,container,false);




        return view;
    }
}
