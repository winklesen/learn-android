package com.samuelbernard147.moviecatalogue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    TextView txtTitle, txtDescription, txtRating, txtRelease, txtDuration;
    ImageView imgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        txtTitle = findViewById(R.id.tv_title);
        txtDescription = findViewById(R.id.tv_desc);
        txtRating = findViewById(R.id.tv_rating);
        txtRelease = findViewById(R.id.tv_rilis);
        txtDuration = findViewById(R.id.tv_durasi);
        imgPoster = findViewById(R.id.img_poster);

//        Get parcelable
        Movie selectedMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        txtTitle.setText(selectedMovie.getJudul());
        txtDescription.setText(selectedMovie.getDeskripsi());
        txtRating.setText(selectedMovie.getRating());
        txtRelease.setText(selectedMovie.getTglRilis());
        txtDuration.setText(String.valueOf(selectedMovie.getDurasi()));
        Glide.with(this).load(selectedMovie.getPoster()).into(imgPoster);

    }
}
