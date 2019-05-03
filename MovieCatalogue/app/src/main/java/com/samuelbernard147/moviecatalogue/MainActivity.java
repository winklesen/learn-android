package com.samuelbernard147.moviecatalogue;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String[] dataTitle, dataDescription, dataReleaseDate, dataRating;
    TypedArray dataPoster;
    int[] dataDuration;
    ListView listView;
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new MovieAdapter(this);
        listView = findViewById(R.id.lv_list);
        listView.setAdapter(adapter);
        listView.setDivider(null);
        listView.setDividerHeight(0);

        prepare();
        addItem();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie selectedMovie = new Movie();
                selectedMovie.setJudul(dataTitle[position]);
                selectedMovie.setDeskripsi(dataDescription[position]);
                selectedMovie.setDurasi(dataDuration[position]);
                selectedMovie.setRating(dataRating[position]);
                selectedMovie.setTglRilis(dataReleaseDate[position]);
                selectedMovie.setPoster(dataPoster.getResourceId(position, -1));

                Toast.makeText(MainActivity.this, selectedMovie.getJudul(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra(DetailActivity.EXTRA_MOVIE, selectedMovie);
                startActivity(i);
            }
        });
    }

    private void prepare() {
        dataTitle = getResources().getStringArray(R.array.data_title);
        dataDescription = getResources().getStringArray(R.array.data_description);
        dataReleaseDate = getResources().getStringArray(R.array.data_release_date);
        dataPoster = getResources().obtainTypedArray(R.array.data_poster);
        dataDuration = getResources().getIntArray(R.array.data_duration);
        dataRating = getResources().getStringArray(R.array.data_rating);
    }

    private void addItem() {
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i = 0; i < dataTitle.length; i++) {
            Movie movie = new Movie();
            movie.setJudul(dataTitle[i]);
            movie.setDeskripsi(dataDescription[i]);
            movie.setPoster(dataPoster.getResourceId(i, -1));
            movies.add(movie);
        }
        adapter.setMovies(movies);
    }

}
