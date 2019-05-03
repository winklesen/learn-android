package com.samuelbernard147.smartagriculture;

import android.app.LoaderManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.samuelbernard147.smartagriculture.Fragment.DatePickerFragment;
import com.samuelbernard147.smartagriculture.Fragment.OptionDialogFragment;
import com.samuelbernard147.smartagriculture.Job.GetHumidityJobService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener, LoaderManager.LoaderCallbacks<ArrayList<RiwayatItems>> {
    TextView tvHumidity, tvFlush;
    Button btnHistory;
    ProgressBar progressBar;
    SwipeRefreshLayout homeSwipeRefresh;
    private int jobId = 10;
    BottomSheetBehavior sheetBehavior;
    LinearLayout layoutBottomSheet;

    int year, month, date;
    ImageButton btnPilihTanggal;
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
        setContentView(R.layout.activity_home);
        tvHumidity = findViewById(R.id.tv_jmlkelembaban);
        tvFlush = findViewById(R.id.tv_jmlsiram);

        btnHistory = findViewById(R.id.btn_riwayat);
        btnHistory.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.circle_progress_bar);
//        progressBar.setProgress(65);

        layoutBottomSheet = findViewById(R.id.bottom_sheet);

        startJob();
        getCurrentStatus();
//        Rubah title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Alirkan");
        }

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        homeSwipeRefresh = findViewById(R.id.swipeRefreshHome);
        // Mengeset properti warna yang berputar pada SwipeRefreshLayout
        homeSwipeRefresh.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
        homeSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("Swipe Refresh Home","Berjalan");
                // Handler digunakan untuk menjalankan jeda selama 5 detik
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Berhenti berputar/refreshing
                        homeSwipeRefresh.setRefreshing(false);
                        getCurrentStatus();
                    }
                },1000); //4000 millisecond = 4 detik
            }
        });
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

    }

    private void getCurrentStatus() {
        Log.d("GetCurrentStatus", "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://agriculture.hopecoding.com/api/kelambapan";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d("GetCurrentStatus", result);
//                int normalHumidity = 50;
                try {
                    JSONObject responseObject = new JSONObject(result);
                    String currentFlush = responseObject.getString("Jumlah_penyiraman");
                    String currentHumidity = responseObject.getString("kelembapan");
                    tvHumidity.setText(currentHumidity);
                    tvFlush.setText(currentFlush+"x Penyiraman");
                    progressBar.setProgress(Integer.parseInt(currentHumidity));
//                    if (currentHumidity <= normalHumidity){
//
//                    }
                } catch (Exception e) {
                    // ketika terjadi error, maka jobFinished diset dengan parameter true. Yang artinya job perlu di reschedule
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // ketika proses gagal, maka jobFinished diset dengan parameter true. Yang artinya job perlu di reschedule
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_riwayat:
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
//                Intent i = new Intent(this, MainActivity.class);
//                startActivity(i);
//                Toast.makeText(this, "Riwayat Siram", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_date:
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
                break;
        }
    }

    private void startJob () {
        ComponentName mServiceComponent = new ComponentName(this, GetHumidityJobService.class);

        JobInfo.Builder builder = new JobInfo.Builder(jobId, mServiceComponent);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setRequiresDeviceIdle(false);
        builder.setRequiresCharging(false);
        builder.setPeriodic(1000);
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
//        Toast.makeText(this, "Job Service started", Toast.LENGTH_SHORT).show();
    }
    private void cancelJob () {
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancel(jobId);
//        Toast.makeText(this, "Job Service canceled", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.clearData();
        getLoaderManager().destroyLoader(0);
    }

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
        getLoaderManager().restartLoader(0, bundle, HomeActivity.this);
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
