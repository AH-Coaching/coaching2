package com.android_helper.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android_helper.R;
import com.android_helper.adapter.AHCustomListAdapter;
import com.android_helper.utils.Keys;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class MainListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String[] list = new String[]{"Test 1", "Test 2", "Test 3", "Test 4"};
    private String[] list2 = new String[]{"Test 2", "Test 3", "Test 4", "Test 5"};
    private String[] list3;
    private String[] list4;


    private int _id;
    private String _title;
    private AHCustomListAdapter _adapter;


    public MainListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        list3 = getActivity().getResources().getStringArray(R.array.list1);
        list4 = getActivity().getResources().getStringArray(R.array.list2);
        _adapter = new AHCustomListAdapter(new ArrayList<String>(), getActivity());
        ListView listView = (ListView) inflater.inflate(R.layout.fragment_main_list, container, false);
        LinearLayout header = (LinearLayout) inflater.inflate(R.layout.header_list, null, false);

        listView.addHeaderView(header);
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
            _adapter.fillList(Arrays.asList(list));
        } else if (_id == 1) {
            _adapter.fillList(Arrays.asList(list2));
        } else if (_id == 2) {
            _adapter.fillList(Arrays.asList(list3));
        } else {
            _adapter.fillList(Arrays.asList(list4));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getActivity(), DetailsActivity.class));
    }
}
