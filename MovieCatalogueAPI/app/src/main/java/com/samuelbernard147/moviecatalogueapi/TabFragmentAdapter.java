package com.samuelbernard147.moviecatalogueapi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.samuelbernard147.moviecatalogueapi.Fragment.MovieFragment;
import com.samuelbernard147.moviecatalogueapi.Fragment.TvShowFragment;

public class TabFragmentAdapter extends FragmentPagerAdapter {
    //    Title Tab
    private String[] title = new String[]{
            "MOVIE",
            "TV SHOW"
    };

    TabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    //    Fungsi untuk berganti tab fragment
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new MovieFragment();
                break;
            case 1:
                fragment = new TvShowFragment();
                break;
            default:
                fragment = null;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return title.length;
    }
}
