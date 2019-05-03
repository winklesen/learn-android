package com.samuelbernard147.moviecataloguelocalstorage.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.samuelbernard147.moviecataloguelocalstorage.Fragment.FavMovieFragment;
import com.samuelbernard147.moviecataloguelocalstorage.Fragment.FavTvShowFragment;
import com.samuelbernard147.moviecataloguelocalstorage.Fragment.FavouriteFragment;
import com.samuelbernard147.moviecataloguelocalstorage.Fragment.MainFragment;
import com.samuelbernard147.moviecataloguelocalstorage.Fragment.MainMovieFragment;
import com.samuelbernard147.moviecataloguelocalstorage.Fragment.MainTvShowFragment;

public class TabFragmentAdapter extends FragmentPagerAdapter {
    public static final String TYPE_MOVIE = "type_movie";
    public static final String TYPE_FAVOURITE = "type_fav";

    //    Type untuk menampung fragment Movie dan Favourite
    public static final String TYPE_CONTAINER = "type_content";
    private String type;

    //    Title Tab
    private String[] title = new String[]{
            "MOVIE",
            "TV SHOW"
    };

    public TabFragmentAdapter(FragmentManager fm, String type) {
        super(fm);
        this.type = type;
    }

    /*
     * MainMovie dan MainTv ditampung kedalam Main Fragment
     * FavMovie dan FavTv ditampung kedalam Favourite Fragment
     * Kedua fragment Main dan Favourite ditampung kedalam viewpager di MainActivity
     */
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (type.equals(TYPE_CONTAINER)) {
            switch (position) {
                case 0:
                    fragment = new MainFragment();
                    break;
                case 1:
                    fragment = new FavouriteFragment();
                    break;
                default:
            }
        } else if (type.equals(TYPE_MOVIE)) {
            switch (position) {
                case 0:
                    fragment = new MainMovieFragment();
                    break;
                case 1:
                    fragment = new MainTvShowFragment();
                    break;
                default:
            }

        } else if (type.equals(TYPE_FAVOURITE)) {
            switch (position) {
                case 0:
                    fragment = new FavMovieFragment();
                    break;
                case 1:
                    fragment = new FavTvShowFragment();
                    break;
                default:
            }
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
