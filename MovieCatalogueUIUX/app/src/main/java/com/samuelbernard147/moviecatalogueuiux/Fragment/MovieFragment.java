package com.samuelbernard147.moviecatalogueuiux.Fragment;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.samuelbernard147.moviecatalogueuiux.DetailActivity;
import com.samuelbernard147.moviecatalogueuiux.ItemClickSupport;
import com.samuelbernard147.moviecatalogueuiux.Movie;
import com.samuelbernard147.moviecatalogueuiux.MovieAdapter;
import com.samuelbernard147.moviecatalogueuiux.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private RecyclerView rvMovie;
    private ArrayList<Movie> list;
    String[] dataTitle, dataDescription, dataReleaseDate, dataRating, dataWriter, dataDirector, dataStar, dataGenre;
    TypedArray dataPoster;
    int[] dataDuration;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rvMovie = view.findViewById(R.id.rv_movie);
        rvMovie.setHasFixedSize(true);

        list = new ArrayList<>();
        prepareDataMovie();
        addDataMovie();
        showRecycler();
    }

    private void showRecycler(){
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        MovieAdapter movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.setListMovie(list);
        rvMovie.setAdapter(movieAdapter);

//        Item Click support
        ItemClickSupport.addTo(rvMovie).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Movie selectedMovie = new Movie();
                selectedMovie.setJudul(dataTitle[position]);
                selectedMovie.setDeskripsi(dataDescription[position]);
                selectedMovie.setDurasi(dataDuration[position]);
                selectedMovie.setRating(dataRating[position]);
                selectedMovie.setTglRilis(dataReleaseDate[position]);
                selectedMovie.setPoster(dataPoster.getResourceId(position, -1));
                selectedMovie.setGenre(dataGenre[position]);
                selectedMovie.setDirector(dataDirector[position]);
                selectedMovie.setWriter(dataWriter[position]);
                selectedMovie.setStar(dataStar[position]);
                showSelectedMovie(selectedMovie);
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(DetailActivity.EXTRA_MOVIE, selectedMovie);
                startActivity(i);
            }
        });
    }

//    Toast untuk movie yang dipilih
    private void showSelectedMovie(Movie movie) {
        Toast.makeText(getActivity(), movie.getJudul(), Toast.LENGTH_SHORT).show();
    }

    //    Mengambil data movie
    public void prepareDataMovie() {
        dataTitle = getResources().getStringArray(R.array.movie_title);
        dataDescription = getResources().getStringArray(R.array.movie_description);
        dataReleaseDate = getResources().getStringArray(R.array.movie_release_date);
        dataPoster = getResources().obtainTypedArray(R.array.movie_poster);
        dataDuration = getResources().getIntArray(R.array.movie_duration);
        dataRating = getResources().getStringArray(R.array.movie_rating);
        dataGenre = getResources().getStringArray(R.array.movie_genre);
        dataDirector = getResources().getStringArray(R.array.movie_director);
        dataWriter = getResources().getStringArray(R.array.movie_writer);
        dataStar = getResources().getStringArray(R.array.movie_star);
    }

    //    Set data Movie kedalam POJO
    public void addDataMovie(){
        ArrayList<Movie> listData = new ArrayList<>();
        for (int i = 0; i < dataTitle.length; i++){
            Movie movie = new Movie();
            movie.setJudul(dataTitle[i]);
            movie.setDeskripsi(dataDescription[i]);
            movie.setDurasi(dataDuration[i]);
            movie.setPoster(dataPoster.getResourceId(i, -1));
            movie.setRating(dataRating[i]);
            movie.setTglRilis(dataReleaseDate[i]);
            movie.setGenre(dataGenre[i]);
            movie.setDirector(dataDirector[i]);
            movie.setWriter(dataWriter[i]);
            movie.setStar(dataStar[i]);
            listData.add(movie);
        }
        list.addAll(listData);
    }
}