package com.samuelbernard147.smartvillage.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samuelbernard147.smartvillage.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class IrrigationFragment extends Fragment {


    public IrrigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_irrigation, container, false);
    }

}
