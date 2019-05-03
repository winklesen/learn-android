package com.samuelbernard147.moviecatalogueuiux;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.samuelbernard147.moviecatalogueuiux.Fragment.MovieFragment;
import com.samuelbernard147.moviecatalogueuiux.Fragment.TvShowFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            if (getSupportActionBar() != null){
                getSupportActionBar().setTitle("Movie Catalogue");
            }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new MovieFragment());
    }

//  Pemanggilan Fragment
    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
        }
        return false;
    }

//    Pemanggilan menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    Fungsi ketika menu dipilih
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.change_language){
            Intent i= new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

//   Fungsi item Bottom Nav
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.nav_movie:
                    fragment = new MovieFragment();
                    break;
                case R.id.nav_tvshow:
                    fragment = new TvShowFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };
}