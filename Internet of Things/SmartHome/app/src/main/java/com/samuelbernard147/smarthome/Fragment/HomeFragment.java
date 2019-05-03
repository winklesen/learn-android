package com.samuelbernard147.smarthome.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.samuelbernard147.smarthome.R;

import org.json.JSONArray;


import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private Switch switchTeras, switchUtama, switchTamu, switchKeluarga, switchDapur, switchMandi, switchAnak;
    SwipeRefreshLayout mainSwipeRefresh;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switchTeras = view.findViewById(R.id.switch_lamp_teras);
        switchUtama = view.findViewById(R.id.switch_lamp_utama);
        switchTamu = view.findViewById(R.id.switch_lamp_tamu);
        switchKeluarga = view.findViewById(R.id.switch_lamp_keluarga);
        switchDapur = view.findViewById(R.id.switch_lamp_dapur);
        switchMandi = view.findViewById(R.id.switch_lamp_mandi);
        switchAnak = view.findViewById(R.id.switch_lamp_anak);

        getLampStatus();

        mainSwipeRefresh = view.findViewById(R.id.refresh_lamp);
        // Mengeset properti warna yang berputar pada SwipeRefreshLayout
        mainSwipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
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
                        getLampStatus();
                    }
                }, 2000); //4000 millisecond = 4 detik
            }
        });

        //      attach a listener to check for changes in state
        switchUtama.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                postStatus(isChecked, 2);
            }
        });
        switchTeras.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                postStatus(isChecked, 1);
            }
        });
        switchTamu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                postStatus(isChecked, 3);
            }
        });
        switchKeluarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                postStatus(isChecked, 4);
            }
        });
        switchDapur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                postStatus(isChecked, 5);
            }
        });
        switchMandi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                postStatus(isChecked, 6);
            }
        });
        switchAnak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                postStatus(isChecked, 7);
            }
        });
    }

    private void getLampStatus() {
        Log.d("GetLampStatus", "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://pameran.hopecoding.com/public/api/get";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d("GetLampStatus", result);
                try {
                    JSONArray listObject = new JSONArray(result);
                    int statusUtama = listObject.getJSONObject(0).getInt("status");
                    int statusAnak = listObject.getJSONObject(1).getInt("status");
                    int statusDapur = listObject.getJSONObject(2).getInt("status");
                    int statusKeluarga = listObject.getJSONObject(3).getInt("status");
                    int statusMandi = listObject.getJSONObject(4).getInt("status");
                    int statusTamu = listObject.getJSONObject(5).getInt("status");
                    int statusTeras = listObject.getJSONObject(6).getInt("status");

                    switchUtama.setChecked(cekStatus(statusUtama));
                    switchAnak.setChecked(cekStatus(statusAnak));
                    switchDapur.setChecked(cekStatus(statusDapur));
                    switchKeluarga.setChecked(cekStatus(statusKeluarga));
                    switchMandi.setChecked(cekStatus(statusMandi));
                    switchTamu.setChecked(cekStatus(statusTamu));
                    switchTeras.setChecked(cekStatus(statusTeras));
                } catch (Exception e) {
                    Log.d("GetLampStatus", "EROR");
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

    private void postLampStatus(final int id, final int status) {
        Log.d("postLampStatus", "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://pameran.hopecoding.com/public/ubahlampu";

        RequestParams param = new RequestParams();
        param.put("id", id);
        param.put("status", status);

        client.get(getActivity(), url, param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("POST", "BERHASIL");
                Log.e("POST", "Id = " + id + "Status = " + status);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("POST", "GAGAL");
            }
        });
    }

    void postStatus(Boolean status, int id) {
        if (status) {
            postLampStatus(id, 1);
        } else {
            postLampStatus(id, 0);
        }
    }

    private boolean cekStatus(int i) {
        boolean status = false;
        if (i == 1) {
            status = true;
        }
        return status;
    }
}