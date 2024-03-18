package com.samuelbernard147.mynavigationdrawer;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {
    static final String TAG = PageFragment.class.getSimpleName();
    public static final String EXTRAS  = "extras";

    public PageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.tv_fragment);

        if (getArguments() != null){
            String page = getArguments().getString(EXTRAS);
            textView.setText(page);

            Log.e(TAG, "onCreateView: halaman fragment " + page);
        }
    }

}
