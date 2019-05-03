package com.samuelbernard147.smarthomev2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.samuelbernard147.smarthomev2.alirkan.MainActivity;

public class DecisionActivity extends AppCompatActivity implements View.OnClickListener{
    CardView cardsoimah, cardalirkan;
    ImageButton ibtnsoimah,ibtnalirkan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision);
        cardsoimah = findViewById(R.id.cardsoimah);
        cardalirkan = findViewById(R.id.cardalirkan);
        ibtnsoimah = findViewById(R.id.ibtn_soimah);
        ibtnalirkan = findViewById(R.id.ibtn_alirkan);

        ibtnsoimah.setOnClickListener(this);
        ibtnalirkan.setOnClickListener(this);

        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);

        cardalirkan.startAnimation(animFadeIn);
        cardsoimah.startAnimation(animFadeIn);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Smart Home");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtn_alirkan:
                MainActivity mainAlirkan = new MainActivity();
                pindahActivity(mainAlirkan);
            break;
            case R.id.ibtn_soimah:
                com.samuelbernard147.smarthomev2.soimah.MainActivity mainSoimah = new com.samuelbernard147.smarthomev2.soimah.MainActivity();
                pindahActivity(mainSoimah);
                break;
        }
    }

    void pindahActivity(Context context) {
        Intent i = new Intent(DecisionActivity.this, context.getClass());
        startActivity(i);
    }
}
