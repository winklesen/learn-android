package com.samuelbernard147.alirkan;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.samuelbernard147.alirkan.Fragment.DatePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class HumidityActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener, LoaderManager.LoaderCallbacks<ArrayList<RiwayatItems>> {
    int year, month, date;
    ImageButton btnPilihTanggal;
    SwipeRefreshLayout mainSwipeRefresh;
    ProgressBar progressBarHumidity;
    RecyclerView recyclerView;
    RiwayatAdapter adapter;
    Bundle bundle;

    static final String EXTRAS_AUTHOR = "EXTRAS_AUTHOR";
    static final String EXTRAS_DAY = "EXTRAS_DAY";
    static final String EXTRAS_MONTH = "EXTRAS_MONTH";

    final String DATE_PICKER_TAG = "DatePicker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);
        progressBarHumidity = findViewById(R.id.pb_humidity);
        progressBarHumidity.setVisibility(View.VISIBLE);

        adapter = new RiwayatAdapter();
        adapter.notifyDataSetChanged();

        recyclerView = findViewById(R.id.rv_humidity);
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
        bundle.putString(EXTRAS_AUTHOR, "agriculture_1");
        bundle.putInt(EXTRAS_MONTH, month + 1);
        bundle.putInt(EXTRAS_DAY, date);

//      Inisiasi dari Loader, dimasukkan ke dalam onCreate
        getLoaderManager().initLoader(1, bundle, this);

//        Untuk Refresh
        mainSwipeRefresh = findViewById(R.id.swipeRefreshhumidity);
        // Mengeset properti warna yang berputar pada SwipeRefreshLayout
        mainSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        mainSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("Swipe Refresh Home", "Berjalan");
                // Handler digunakan untuk menjalankan jeda selama 2 detik
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Berhenti berputar/refreshing
                        mainSwipeRefresh.setRefreshing(false);
                        getLoaderManager().restartLoader(1, bundle, HumidityActivity.this);
                    }
                }, 2000); //4000 millisecond = 4 detik
            }
        });

//        Rubah title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Riwayat Kelembaban");
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
    protected void onDestroy() {
        super.onDestroy();
        adapter.clearData();
        getLoaderManager().destroyLoader(1);
    }

    //    Fungsi ketika button tanggal di klik
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_date) {
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
        }
    }

    //    Fungsi ketika memilih tanggal dari dialog
    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.date = dayOfMonth;
        String author = "agriculture_1";

        bundle = new Bundle();
        bundle.putString(EXTRAS_AUTHOR, author);
        bundle.putInt(EXTRAS_DAY, dayOfMonth);
        bundle.putInt(EXTRAS_MONTH, month + 1);
        getLoaderManager().restartLoader(1, bundle, HumidityActivity.this);
        Log.e("OnDialogSet", "TEST" + month);
//        Log.e("OnDialogSet","TEST"+month);
    }

    //    Fungsi ketika loader dibuat
    @Override
    public Loader<ArrayList<RiwayatItems>> onCreateLoader(int id, Bundle args) {
        String author = "";
        int selectedDay = 0;
        int selectedMonth = 0;
        if (args != null) {
            author = args.getString(EXTRAS_AUTHOR);
            selectedDay = args.getInt(EXTRAS_DAY);
            selectedMonth = args.getInt(EXTRAS_MONTH);
        }
        return new RiwayatLoader(this, author, selectedDay, selectedMonth, 100);
    }

    //    Fungsi ketika data loader selesai dimuat
    @Override
    public void onLoadFinished(Loader<ArrayList<RiwayatItems>> loader, ArrayList<RiwayatItems> data) {
        adapter.setData(data);
        stopLoader(1);
        progressBarHumidity.setVisibility(View.INVISIBLE);
    }

    void stopLoader(int id) {
        getLoaderManager().destroyLoader(id);
    }

    //    Fungsi untuk mereset loader
    @Override
    public void onLoaderReset(Loader<ArrayList<RiwayatItems>> loader) {
        adapter.setData(null);
    }
}