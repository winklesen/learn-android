package com.samuelbernard147.smartvillage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.samuelbernard147.smartvillage.Adapter.BottomNavAdapter;
import com.samuelbernard147.smartvillage.Adapter.TabFragmentAdapter;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPagerMain;
    BottomNavigationView navigation;
    MenuItem prevMenuItem;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_lamp:
                    viewPagerMain.setCurrentItem(0);
                    return true;
                case R.id.navigation_water:
                    viewPagerMain.setCurrentItem(1);
                    return true;
                case R.id.navigation_shop:
                    viewPagerMain.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_main);
        viewPagerMain = findViewById(R.id.vp_main);

//        Inisialisasi bottom nav beserta ViewPagernya
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPagerMain.setAdapter(new BottomNavAdapter(getSupportFragmentManager()));
        viewPagerMain.setCurrentItem(0);

        viewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int I) {
            }

            @Override
            public void onPageSelected(int position) {
                prevMenuItem = navigation.getMenu().getItem(position);
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                Log.d("Page", "onPageSelected: " + position);
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

}
