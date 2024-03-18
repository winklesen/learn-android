package com.samuelbernard.latihan_retrofit;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.samuelbernard.latihan_retrofit.model.Student;
import com.samuelbernard.latihan_retrofit.rest.ApiClient;
import com.samuelbernard.latihan_retrofit.rest.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btnLoadData)
    Button btnLoadData;
    @BindView(R.id.tvSubuh)
    TextView tvSubuh;
    @BindView(R.id.tvZuhur)
    TextView tvZuhur;
    @BindView(R.id.tvAshar)
    TextView tvAshar;
    @BindView(R.id.tvMagrhib)
    TextView tvMagrhib;
    @BindView(R.id.tvIsya)
    TextView tvIsya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btnLoadData)
    public void actionLoadData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Student> call = apiService.getAllStudent();
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {

            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {

            }
        });
    }

    private void loadData(List<Student> students){
        Student data = students.get(2);

            tvSubuh.setText(data.getId());
            tvZuhur.setText(data.getEmail());
            tvAshar.setText(data.getPassword());
            tvMagrhib.setText(data.getUsername());

    }
}