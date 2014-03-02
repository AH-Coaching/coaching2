package ua.com.androidhelper.ui.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import ua.com.androidhelper.R;
import ua.com.androidhelper.ui.adapters.AHCustomListView;
import ua.com.androidhelper.ui.utils.Keys;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class MainListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String[] list = new String[]{"Happy New Year", "Second", "Third", "Fourth", "......."};
    private String[] list2 = new String[]{"Test2", "Test3", "Test4", "Test5", "Test6"};
    private String[] list3;
    private String[] list4;
    private int _id;
    private String _title;
    private AHCustomListView _adapter;

    public MainListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        list3 = getResources().getStringArray(R.array.list3);
        list4 = getResources().getStringArray(R.array.list4);
        _adapter = new AHCustomListView(new ArrayList<String>(), getActivity(),0);

        ListView listView = (ListView) inflater.inflate(R.layout.fragment_mainlist, container, false);
        //Example how add the header
        LinearLayout header = (LinearLayout)inflater.inflate(R.layout.header_list,null,false);
        /*if (listView != null) {
            listView.addHeaderView(header);
        }*/
        listView.setAdapter(_adapter);
        listView.setOnItemClickListener(this);
        return listView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            _id = bundle.getInt(Keys.KEY_ID);
            _title = bundle.getString(Keys.KEY_TITLE);
        }
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(_title);
        if (_id == 0) {
            _adapter.filllist(Arrays.asList(list));
        }else if (_id == 1) {
            _adapter.filllist(Arrays.asList(list2));
        }else if (_id == 2) {
            _adapter.filllist(Arrays.asList(list3));
        } else {
            _adapter.filllist(Arrays.asList(list4));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(getActivity(), DetailsActivity.class));
    }
}
