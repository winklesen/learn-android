package com.samuelbernard147.myunittesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements MainView{
    TextView tvResult;
    EditText edtWidth, edtLength, edtHeight;
    Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = findViewById(R.id.tv_result);
        edtHeight = findViewById(R.id.edt_height);
        edtLength = findViewById(R.id.edt_length);
        edtWidth = findViewById(R.id.edt_width);
        btnCalculate = findViewById(R.id.btn_calculate);
        final MainPresenter presenter = new MainPresenter(this);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String length = edtLength.getText().toString().trim();
                String height = edtLength.getText().toString().trim();
                String width = edtLength.getText().toString().trim();

                boolean isEmptyFields = false;

                if (TextUtils.isEmpty(length)) {
                    isEmptyFields = true;
                    edtLength.setError("Field ini tidak boleh kosong");
                }
                if (TextUtils.isEmpty(width)) {
                    isEmptyFields = true;
                    edtWidth.setError("Field ini tidak boleh kosong");
                }
                if (TextUtils.isEmpty(height)) {
                    isEmptyFields = true;
                    edtHeight.setError("Field ini tidak boleh kosong");
                }
                if (!isEmptyFields) {
                    double l = Double.parseDouble(length);
                    double w = Double.parseDouble(width);
                    double h = Double.parseDouble(height);

                    presenter.calculateVolume(l,w,h);
                }
            }
        });
    }

    @Override
    public void showVolume(MainModel model){
        tvResult.setText(String.valueOf(model.getVolume()));
    }
}
