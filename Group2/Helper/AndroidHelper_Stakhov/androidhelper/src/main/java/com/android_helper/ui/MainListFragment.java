package com.android_helper.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android_helper.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class MainListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String[] list = new String[]{"Test 1", "Test 2", "Test 3", "Test 4"};


    public MainListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, list);
        ListView listView = (ListView) inflater.inflate(R.layout.fragment_main_list, container, false);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return listView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getActivity(), DetailsActivity.class));
    }
}
