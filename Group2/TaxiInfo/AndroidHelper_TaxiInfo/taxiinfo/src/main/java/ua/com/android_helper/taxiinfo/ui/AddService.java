package ua.com.android_helper.taxiinfo.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ua.com.android_helper.taxiinfo.R;


/**
 * Created by andreyholovko on 3/22/14.
 */
public class AddService extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addservice, container, false);
        Button button = (Button) view.findViewById(R.id.btn_newcity);
        button.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_newcity:
               DialogFragment dialogAddCity = new AddCityDialog();
                dialogAddCity.show(getFragmentManager(), "dialogCall");
                break;
        }
    }
}
