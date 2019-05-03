package com.samuelbernard147.soimah;


import android.app.LoaderManager;
import android.content.Loader;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.samuelbernard147.soimah.Fragment.DatePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener, LoaderManager.LoaderCallbacks<ArrayList<KartuItems>> {
    int year, month, date;
    ImageButton btnPilihTanggal;
    SwipeRefreshLayout DoorSwipeRefresh;
    RecyclerView recyclerView;
    KartuAdapter adapter;
    Bundle bundle;

    static final String EXTRAS_DAY = "EXTRAS_DAY";
    static final String EXTRAS_MONTH = "EXTRAS_MONTH";

    final String DATE_PICKER_TAG = "DatePicker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new KartuAdapter();
        adapter.notifyDataSetChanged();

        recyclerView = findViewById(R.id.rv_card);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
//        Buat divider
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

//        Buat ngambil tanggal hari ini
        btnPilihTanggal = findViewById(R.id.btn_date);
        btnPilihTanggal.setOnClickListener(this);

        final Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DATE);

        bundle = new Bundle();
        bundle.putInt(EXTRAS_MONTH, month + 1);
        bundle.putInt(EXTRAS_DAY, date);

//      Inisiasi dari Loader, dimasukkan ke dalam onCreate
        getLoaderManager().initLoader(0, bundle, MainActivity.this);

        DoorSwipeRefresh = findViewById(R.id.swipeRefreshDoor);
//      Mengeset properti warna yang berputar pada SwipeRefreshLayout
        DoorSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        DoorSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("Swipe Refresh Door", "Berjalan");
                // Handler digunakan untuk menjalankan jeda selama 2 detik
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Berhenti berputar/refreshing
                        DoorSwipeRefresh.setRefreshing(false);
                        getLoaderManager().restartLoader(0, bundle, MainActivity.this);
                    }
                }, 2000); //4000 millisecond = 4 detik
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.clearData();
        getLoaderManager().destroyLoader(0);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_date) {
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
        }
    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.date = dayOfMonth;

        bundle = new Bundle();
        bundle.putInt(EXTRAS_DAY, dayOfMonth);
        bundle.putInt(EXTRAS_MONTH, month + 1);
        getLoaderManager().restartLoader(0, bundle, MainActivity.this);
        Log.e("OnDialogSet", "TEST Berjalan" + month);
    }

    @NonNull
    @Override
    public Loader<ArrayList<KartuItems>> onCreateLoader(int id, @Nullable Bundle args) {
        int selectedDay = 0;
        int selectedMonth = 0;
        if (args != null) {
            selectedDay = args.getInt(EXTRAS_DAY);
            selectedMonth = args.getInt(EXTRAS_MONTH);
        }
        return new MyAsyncTaskLoader(MainActivity.this, selectedDay, selectedMonth);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<KartuItems>> loader, ArrayList<KartuItems> data) {
        adapter.setData(data);
        stopLoader(0);
    }

    void stopLoader(int id) {
        getLoaderManager().destroyLoader(id);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<KartuItems>> loader) {
        adapter.setData(null);
    }
}