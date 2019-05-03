package com.samuelbernard147.moviecatalogueapi.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.samuelbernard147.moviecatalogueapi.DetailActivity;
import com.samuelbernard147.moviecatalogueapi.ItemClickSupport;
import com.samuelbernard147.moviecatalogueapi.Movie;
import com.samuelbernard147.moviecatalogueapi.MovieAdapter;
import com.samuelbernard147.moviecatalogueapi.MovieLoader;
import com.samuelbernard147.moviecatalogueapi.R;

import java.util.ArrayList;
import java.util.Locale;

public class MovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    ProgressBar progressBarMovie;
    RecyclerView recyclerViewMovie;
    ArrayList<Movie> listMovie;
    String language;
    MovieAdapter adapter;
    Bundle bundle;

    static final int MOVIE_ID = 100;
    static final String MOVIE_LANG = "movie_language";
    static final String STATE_MOVIE = "state_movie";
    static final String STATE_LANG = "state_language";

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Recyclerview
        recyclerViewMovie = view.findViewById(R.id.rv_movie);
        showRecycler();

//        Progress bar muncul
        progressBarMovie = view.findViewById(R.id.pb_movie);
        progressBarMovie.setVisibility(View.VISIBLE);

//      Inisiasi dari Loader
        getLoaderManager().initLoader(0, bundle, MovieFragment.this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save Data Fragment
        outState.putParcelableArrayList(STATE_MOVIE,this.listMovie);
        outState.putString(STATE_LANG,this.language);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
//            Restore Data Fragment
            ArrayList<Movie> stateData = savedInstanceState.getParcelableArrayList(STATE_MOVIE);
            this.listMovie = stateData;
            this.language = savedInstanceState.getString(STATE_LANG);

//            Setdata ke adapter
            adapter.setData(stateData);

//            Menghilangkan progress bar
            progressBarMovie.setVisibility(View.INVISIBLE);

//            Menghentikan loader
            getLoaderManager().destroyLoader(0);
        }
    }

    void showRecycler(){
        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();
        recyclerViewMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMovie.setAdapter(adapter);

//        Item Click support
        ItemClickSupport.addTo(recyclerViewMovie).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Movie selectedMovie = listMovie.get(position);
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(DetailActivity.EXTRA_ID, selectedMovie.getId());
                i.putExtra(DetailActivity.EXTRA_LANG, language);
                i.putExtra(DetailActivity.EXTRA_TYPE,"movie");
                startActivity(i);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.clearData();
        getLoaderManager().destroyLoader(0);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, @Nullable Bundle args) {
//        Menentukan bahasa
        this.language = Locale.getDefault().getLanguage();
        if (args != null){
            language = args.getString(MOVIE_LANG);
        }
        return new MovieLoader(getActivity(), MOVIE_ID, language);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        adapter.setData(movies);
        this.listMovie = movies;
        stopLoader(0);
//        Progress bar menghilang
        progressBarMovie.setVisibility(View.INVISIBLE);
    }

    void stopLoader(int id) {
        getLoaderManager().destroyLoader(id);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        adapter.setData(null);
    }
}