package com.samuelbernard147.moviecataloguelocalstorage.Fragment;


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
import android.widget.TextView;
import android.widget.Toast;

import com.samuelbernard147.moviecataloguelocalstorage.Adapter.MovieAdapter;
import com.samuelbernard147.moviecataloguelocalstorage.DetailActivity;
import com.samuelbernard147.moviecataloguelocalstorage.ItemClickSupport;
import com.samuelbernard147.moviecataloguelocalstorage.Model.Movie;
import com.samuelbernard147.moviecataloguelocalstorage.MovieLoader;
import com.samuelbernard147.moviecataloguelocalstorage.R;
import com.samuelbernard147.moviecataloguelocalstorage.SettingsPreference;

import java.util.ArrayList;
import java.util.Locale;


public class MainMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    ProgressBar progressBarMovie;
    TextView tvLoading;
    RecyclerView recyclerViewMovie;
    ArrayList<Movie> listMovie;
    String language;
    MovieAdapter movieAdapter;
    Bundle bundle;

    private SettingsPreference mSettingPreference;

    //    Key untuk SaveInstanceState
    static final String STATE_MOVIE = "state_movie";
    static final String STATE_LANG = "state_language";

    public MainMovieFragment() {
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
        mSettingPreference = new SettingsPreference(getActivity());

//        Recyclerview
        recyclerViewMovie = view.findViewById(R.id.rv_movie);
        showRecycler();

//        Progress bar muncul
        progressBarMovie = view.findViewById(R.id.pb_movie);
        progressBarMovie.setVisibility(View.VISIBLE);
        tvLoading = view.findViewById(R.id.tv_loading_movie);
        tvLoading.setVisibility(View.VISIBLE);

//      Inisiasi dari Loader
        getLoaderManager().initLoader(0, bundle, MainMovieFragment.this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save Data Fragment
        outState.putParcelableArrayList(STATE_MOVIE, this.listMovie);
        outState.putString(STATE_LANG, this.language);
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
            movieAdapter.setListMovie(stateData);

//            Menghilangkan progress bar
            progressBarMovie.setVisibility(View.INVISIBLE);
            tvLoading.setVisibility(View.INVISIBLE);

//            Menghentikan loader
            getLoaderManager().destroyLoader(0);
        }
    }

    void showRecycler() {
        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();
        recyclerViewMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMovie.setAdapter(movieAdapter);

//        Item Click support
        ItemClickSupport.addTo(recyclerViewMovie).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Movie selectedMovie = listMovie.get(position);
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(DetailActivity.EXTRA_ID, selectedMovie.getId());
                i.putExtra(DetailActivity.EXTRA_LANG, language);
                i.putExtra(DetailActivity.EXTRA_TYPE, selectedMovie.TYPE_MOVIE);

//                TODO: rubah ini jadi passing model movie
                startActivity(i);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieAdapter.clearListMovie();
        getLoaderManager().destroyLoader(0);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, @Nullable Bundle args) {
//        Menentukan bahasa
        String lang = mSettingPreference.getLang();
        if (lang != null) {
            this.language = lang;
        } else {
            this.language = Locale.getDefault().getLanguage();
        }
        return new MovieLoader(getActivity(), MovieLoader.ID_MOVIE, language, MovieLoader.TYPE_API);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        movieAdapter.setListMovie(movies);
        this.listMovie = movies;
        stopLoader(0);
//        Progress bar menghilang
        progressBarMovie.setVisibility(View.INVISIBLE);
        tvLoading.setVisibility(View.INVISIBLE);

        if (movies.size()<=0){
            Toast.makeText(getActivity(),getResources().getString(R.string.fail),Toast.LENGTH_SHORT).show();
        }
    }

    void stopLoader(int id) {
        getLoaderManager().destroyLoader(id);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        movieAdapter.setListMovie(null);
    }
}