package com.samuelbernard147.smarthome.Fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.samuelbernard147.smarthome.KartuAdapter;
import com.samuelbernard147.smarthome.KartuItems;
import com.samuelbernard147.smarthome.RiwayatLoader;
import com.samuelbernard147.smarthome.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoorFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<ArrayList<KartuItems>> {
    int year, month, date;
    ImageButton btnPilihTanggal;
    SwipeRefreshLayout DoorSwipeRefresh;
    RecyclerView recyclerView;
    KartuAdapter adapter;
    Bundle bundle;

    static final String EXTRAS_DAY = "EXTRAS_DAY";
    static final String EXTRAS_MONTH = "EXTRAS_MONTH";
    static final String EXTRAS_YEAR = "EXTRAS_YEAR";

    final String DATE_PICKER_TAG = "DatePicker";

    public DoorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_door, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new KartuAdapter();
        adapter.notifyDataSetChanged();

        recyclerView = view.findViewById(R.id.rv_card);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
//        Buat divider
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
//        Buat ngambil tanggal hari ini
        btnPilihTanggal = view.findViewById(R.id.btn_date);
        btnPilihTanggal.setOnClickListener(this);

        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DATE);

        bundle = new Bundle();
        bundle.putInt(EXTRAS_MONTH, month + 1);
        bundle.putInt(EXTRAS_DAY, date);

//      Inisiasi dari Loader, dimasukkan ke dalam onCreate
        getLoaderManager().initLoader(0, bundle, DoorFragment.this);

        DoorSwipeRefresh = view.findViewById(R.id.swipeRefreshDoor);
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
                        getLoaderManager().restartLoader(0, bundle, DoorFragment.this);
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
        /**
         * Set Call back to capture selected date
         */
        datePickerFragment.setCallBack(onDialogSet);
        datePickerFragment.show(getFragmentManager(), DATE_PICKER_TAG);
    }

    DatePickerDialog.OnDateSetListener onDialogSet = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
            bundle = new Bundle();
            bundle.putInt(EXTRAS_DAY, dayOfMonth);
            bundle.putInt(EXTRAS_MONTH, monthOfYear + 1);
            getLoaderManager().restartLoader(0, bundle, DoorFragment.this);
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
        return new RiwayatLoader(getActivity(),selectedDay,selectedMonth);
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