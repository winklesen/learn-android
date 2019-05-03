package com.samuelbernard147.smarthomev2.soimah;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.samuelbernard147.smarthomev2.soimah.Fragment.DatePickerFragment;
import com.samuelbernard147.smarthomev2.R;

import java.util.ArrayList;
import java.util.Calendar;

public class DoorLockActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<ArrayList<KartuItems>> {
    int year, month, date;
    ImageButton btnPilihTanggal;
    SwipeRefreshLayout DoorSwipeRefresh;
    ProgressBar progressBarDoorLock;
    RecyclerView recyclerView;
    KartuAdapter adapter;
    Bundle bundle;

    static final String EXTRAS_DAY = "EXTRAS_DAY";
    static final String EXTRAS_MONTH = "EXTRAS_MONTH";
    static final String EXTRAS_YEAR = "EXTRAS_YEAR";

    final String DATE_PICKER_TAG = "DatePicker";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soimah_door_lock);
        adapter = new KartuAdapter();
        adapter.notifyDataSetChanged();

        progressBarDoorLock = findViewById(R.id.pb_doorlock);
        progressBarDoorLock.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.rv_card);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
//        Buat divider
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
//        Buat ngambil tanggal hari ini
        btnPilihTanggal = findViewById(R.id.btn_date);
        btnPilihTanggal.setOnClickListener(this);

        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DATE);

        bundle = new Bundle();
        bundle.putInt(EXTRAS_MONTH, month + 1);
        bundle.putInt(EXTRAS_DAY, date);

//      Inisiasi dari Loader, dimasukkan ke dalam onCreate
        getLoaderManager().initLoader(0, bundle, this);

        DoorSwipeRefresh = findViewById(R.id.swipeRefreshDoor);
//      Mengeset properti warna yang berputar pada SwipeRefreshLayout
        DoorSwipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
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
                        getLoaderManager().restartLoader(0, bundle, DoorLockActivity.this);
                    }
                }, 2000); //4000 millisecond = 4 detik
            }
        });

//        Rubah title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Door Lock");
        }

//        Buat back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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
            showDatePicker();
        }
    }

    private void showDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRAS_YEAR, year);
        args.putInt(EXTRAS_MONTH, month);
        args.putInt(EXTRAS_DAY, date);
        datePickerFragment.setArguments(args);
//        Callback
        datePickerFragment.setCallBack(onDialogSet);
        datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
    }

    DatePickerDialog.OnDateSetListener onDialogSet = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            bundle = new Bundle();
            bundle.putInt(EXTRAS_DAY, dayOfMonth);
            bundle.putInt(EXTRAS_MONTH, monthOfYear + 1);
            getLoaderManager().restartLoader(0, bundle, DoorLockActivity.this);
            Log.e("OnDialogSet", "TEST Berjalan" + monthOfYear);
        }
    };

    @NonNull
    @Override
    public Loader<ArrayList<KartuItems>> onCreateLoader(int id, @Nullable Bundle args) {
        int selectedDay = 0;
        int selectedMonth = 0;
        if (args != null) {
            selectedDay = args.getInt(EXTRAS_DAY);
            selectedMonth = args.getInt(EXTRAS_MONTH);
        }
        return new RiwayatLoader(this, selectedDay, selectedMonth);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<KartuItems>> loader, ArrayList<KartuItems> data) {
        adapter.setData(data);
        stopLoader(0);
        progressBarDoorLock.setVisibility(View.INVISIBLE);
    }

    void stopLoader(int id) {
        getLoaderManager().destroyLoader(id);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<KartuItems>> loader) {
        adapter.setData(null);
    }

}