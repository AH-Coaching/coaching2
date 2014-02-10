package com.android_helper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android_helper.R;

import java.util.List;

/**
 * Created by andriistakhov on 10.11.13.
 */
public class AHCustomListAdapter extends BaseAdapter {

    private final LayoutInflater _inflater;
    private List<String> _list;

    public AHCustomListAdapter(List<String> list, Context context) {
        _list = list;
        _inflater = LayoutInflater.from(context);
    }

    public void fillList(List<String> list) {
        _list.clear();
        _list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return _list.size();
    }

    @Override
    public String getItem(int position) {
        return _list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout view = (RelativeLayout) convertView;
        if (view == null) {
            view = (RelativeLayout) _inflater.inflate(R.layout.custom_list, null, false);

        }
        TextView textView = (TextView) view.findViewById(R.id.txt_item);
        textView.setText(getItem(position));
        return view;
    }

}
