package com.samuelbernard147.smartagriculture;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.samuelbernard147.smartagriculture.Fragment.DatePickerFragment;
import com.samuelbernard147.smartagriculture.Fragment.OptionDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener, LoaderManager.LoaderCallbacks<ArrayList<RiwayatItems>> {
    int year, month, date;
    ImageButton btnPilihTanggal;
    SwipeRefreshLayout mainSwipeRefresh;
    RecyclerView recyclerView;
    RiwayatAdapter adapter;
    Bundle bundle;

    static final String EXTRAS_AUTHOR = "EXTRAS_AUTHOR";
    static final String EXTRAS_DAY = "EXTRAS_DAY";
    static final String EXTRAS_MONTH = "EXTRAS_MONTH";
    static final String EXTRAS_QUANTITY = "EXTRAS_QUANTITY";

    final String DATE_PICKER_TAG = "DatePicker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new RiwayatAdapter();
        adapter.notifyDataSetChanged();

        recyclerView = findViewById(R.id.rv_main);
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
        getLoaderManager().initLoader(0, bundle, this);

        mainSwipeRefresh= findViewById(R.id.swipeRefreshMain);
        // Mengeset properti warna yang berputar pada SwipeRefreshLayout
        mainSwipeRefresh.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
        mainSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("Swipe Refresh Home","Berjalan");
                // Handler digunakan untuk menjalankan jeda selama 2 detik
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Berhenti berputar/refreshing
                        mainSwipeRefresh.setRefreshing(false);
                        getLoaderManager().restartLoader(0, bundle, MainActivity.this);
                    }
                },2000); //4000 millisecond = 4 detik
            }
        });

//        Rubah title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Riwayat");
        }
//        Buat back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.clearData();
        getLoaderManager().destroyLoader(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.author_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_author:
                OptionDialogFragment mOptionDialogFragment = new OptionDialogFragment();
                FragmentManager mFragmentManager = getSupportFragmentManager();
                mOptionDialogFragment.show(mFragmentManager, OptionDialogFragment.class.getSimpleName());
                break;
        }
        return super.onOptionsItemSelected(item);
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
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, month, dayOfMonth);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        tvDate.setText(dateFormat.format(calendar.getTime()));
//        String selectedDate = dateFormat.format(calendar.getTime());
        this.year = year;
        this.month = month;
        this.date = dayOfMonth;
        String author = "agriculture_1";

        bundle = new Bundle();
        bundle.putString(EXTRAS_AUTHOR, author);
        bundle.putInt(EXTRAS_DAY, dayOfMonth);
        bundle.putInt(EXTRAS_MONTH, month + 1);
        getLoaderManager().restartLoader(0, bundle, MainActivity.this);
        Log.e("OnDialogSet", "TEST" + month);
//        Log.e("OnDialogSet","TEST"+month);
    }

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
        return new MyAsyncTaskLoader(this, author, selectedDay, selectedMonth);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<RiwayatItems>> loader, ArrayList<RiwayatItems> data) {
        adapter.setData(data);
        stopLoader(0);
    }

    void stopLoader(int id) {
        getLoaderManager().destroyLoader(id);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<RiwayatItems>> loader) {
        adapter.setData(null);
    }

    /*
    Kode yang akan dijalankan ketika option dialog dipilih ok
     */
    OptionDialogFragment.OnOptionDialogListener optionDialogListener = new OptionDialogFragment.OnOptionDialogListener() {
        @Override
        public void onOptionChosen(String text) {
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    };
}