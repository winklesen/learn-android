package com.samuelbernard147.smartvillage.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.samuelbernard147.smartvillage.Fragment.ContainerWater;
import com.samuelbernard147.smartvillage.Fragment.LampFragment;
import com.samuelbernard147.smartvillage.Fragment.ShopFragment;

public class BottomNavAdapter extends FragmentPagerAdapter {
    //    Title Tab
    private String[] title = new String[]{
            "Street Lamp",
            "Water",
            "Shop"
    };

    public BottomNavAdapter(FragmentManager fm) {
        super(fm);
    }

    /*
     * Lamp, Shop dan Container ditampung kedalam Main Activity
     * Irrigation dan Agriculture ditampung kedalam Container Fragment
     */
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new LampFragment();
                break;
            case 1:
                fragment = new ContainerWater();
                break;
            case 2:
                fragment = new ShopFragment();
                break;
            default:
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