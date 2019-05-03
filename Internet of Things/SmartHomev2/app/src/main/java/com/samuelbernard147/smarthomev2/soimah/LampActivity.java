package com.samuelbernard147.smarthomev2.soimah;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.samuelbernard147.smarthomev2.R;

import java.util.ArrayList;


public class LampActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<ArrayList<LampuItems>> {
    ProgressBar progressBarLamp;
    RecyclerView recyclerView;
//    SwipeRefreshLayout swipeRefreshLayoutLamp;
    LampuAdapter adapter;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soimah_lamp);
        adapter = new LampuAdapter(this);
        adapter.notifyDataSetChanged();

        recyclerView = findViewById(R.id.rv_lamp);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        progressBarLamp = findViewById(R.id.pb_lamp);
        progressBarLamp.setVisibility(View.VISIBLE);

//      Inisiasi dari Loader, dimasukkan ke dalam onCreate
        getLoaderManager().initLoader(1, bundle, this);

//        Rubah title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Ruangan");
        }

//        Buat back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private boolean cekStatus(int i) {
        boolean status = false;
        if (i == 1) {
            status = true;
        }
        return status;
    }

    @NonNull
    @Override
    public Loader<ArrayList<LampuItems>> onCreateLoader(int id, @Nullable Bundle args) {
        return new LampuLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<LampuItems>> loader, ArrayList<LampuItems> data) {
        adapter.setData(data);
        stopLoader(1);

        progressBarLamp.setVisibility(View.INVISIBLE);
    }

    void stopLoader(int id) {
        getLoaderManager().destroyLoader(id);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<LampuItems>> loader) {
        adapter.setData(null);
    }

    @Override
    public void onClick(View v) {

    }
}
