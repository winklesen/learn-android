package com.samuelbernard147.moviecatalogueuiux;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    TextView txtTitle, txtDescription, txtRating, txtRelease, txtDuration, txtGenre, txtWriter, txtDirector, txtStar;
    ImageView imgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Movie Catalogue");
        }

        txtTitle = findViewById(R.id.tv_title);
        txtDescription = findViewById(R.id.tv_desc);
        txtRating = findViewById(R.id.tv_rating);
        txtRelease = findViewById(R.id.tv_date);
        txtDuration = findViewById(R.id.tv_duration);
        imgPoster = findViewById(R.id.img_poster);
        txtGenre = findViewById(R.id.tv_genre);
        txtWriter = findViewById(R.id.tv_writer);
        txtDirector = findViewById(R.id.tv_director);
        txtStar = findViewById(R.id.tv_star);

        //        Get parcelable
        Movie selectedMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        txtTitle.setText(selectedMovie.getJudul());
        txtDescription.setText(selectedMovie.getDeskripsi());
        txtRating.setText(selectedMovie.getRating());
        txtRelease.setText(selectedMovie.getTglRilis());
        txtGenre.setText(selectedMovie.getGenre());
        txtDirector.setText(selectedMovie.getDirector());
        txtWriter.setText(selectedMovie.getWriter());
        txtStar.setText(selectedMovie.getStar());
        String durasi = String.format(getResources().getString(R.string.minute), selectedMovie.getDurasi());
        txtDuration.setText(durasi);

//        Image
        String contentDescription = "Poster " +  selectedMovie.getJudul();
        imgPoster.setContentDescription(contentDescription);
        Glide.with(this).load(selectedMovie.getPoster()).into(imgPoster);
    }

}
