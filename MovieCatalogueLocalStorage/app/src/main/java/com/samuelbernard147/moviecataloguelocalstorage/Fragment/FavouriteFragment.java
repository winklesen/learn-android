package com.samuelbernard147.moviecataloguelocalstorage.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samuelbernard147.moviecataloguelocalstorage.R;
import com.samuelbernard147.moviecataloguelocalstorage.Adapter.TabFragmentAdapter;

public class FavouriteFragment extends Fragment {
    TabLayout mTabLayout;
    ViewPager mViewPager;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = view.findViewById(R.id.tl_fav);
        mViewPager = view.findViewById(R.id.vp_fav);

//        Set viewpager kedalam tablayout
        mViewPager.setAdapter(new TabFragmentAdapter(getChildFragmentManager(), TabFragmentAdapter.TYPE_FAVOURITE));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
