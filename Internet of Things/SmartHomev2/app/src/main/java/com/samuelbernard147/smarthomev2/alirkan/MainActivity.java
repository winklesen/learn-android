package com.samuelbernard147.smarthomev2.alirkan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.samuelbernard147.smarthomev2.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView card1, card2, card3, card4;
    ImageButton ibtnHumidity, ibtnFlush, ibtnInfo, ibtnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ibtnHumidity = findViewById(R.id.ibtn_kelembaban);
        ibtnFlush = findViewById(R.id.ibtn_penyiraman);
        ibtnInfo = findViewById(R.id.ibtn_about);
        ibtnHelp = findViewById(R.id.ibtn_help);

        ibtnHumidity.setOnClickListener(this);
        ibtnFlush.setOnClickListener(this);
        ibtnInfo.setOnClickListener(this);
        ibtnHelp.setOnClickListener(this);

        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fadein);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);

        card1.startAnimation(animFadeIn);
        card2.startAnimation(animFadeIn);
        card3.startAnimation(animFadeIn);
        card4.startAnimation(animFadeIn);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Alirkan");
        }
    }

    //    Untuk back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_kelembaban:
                HumidityActivity humidityActivity = new HumidityActivity();
                pindahActivity(humidityActivity);
                break;
            case R.id.ibtn_penyiraman:
                FlushActivity flushActivity = new FlushActivity();
                pindahActivity(flushActivity);
                break;
            case R.id.ibtn_about:
                AboutActivity aboutActivity = new AboutActivity();
                pindahActivity(aboutActivity);
                break;
            case R.id.ibtn_help:
                HelpActivity helpActivity = new HelpActivity();
                pindahActivity(helpActivity);
                break;
        }
    }

    void pindahActivity(Context context) {
        Intent i = new Intent(MainActivity.this, context.getClass());
        startActivity(i);
    }
}
