package ua.com.androidhelper.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ua.com.androidhelper.R;

/**
 * Created by Androidsmith on 16.02.14.
 */
public class AHCustomListView extends BaseAdapter {

   private List<String> _list;
   private LayoutInflater _inflater;
   private int _chooselayout;

    public AHCustomListView(List<String> list, Context context, int chooselay) {
        _list = list;
        _inflater = LayoutInflater.from(context);
        _chooselayout=chooselay;
    }

    public void filllist(List<String> list){
    _list.clear();
    _list.addAll(list);
     notifyDataSetChanged();
    };
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
    public View getView(int position, View convertview, ViewGroup viewGroup) {
        RelativeLayout view = (RelativeLayout)convertview;
        if(view==null){
          if (_chooselayout == 2)
          {
            view = (RelativeLayout)_inflater.inflate(R.layout.custom_list_sec,null,false);
          }else{
              view = (RelativeLayout)_inflater.inflate(R.layout.custom_list,null,false);
          }

        }
        TextView textView = (TextView) view.findViewById(R.id.txt_item);
        textView.setText(getItem(position));
        return view;
    }
}