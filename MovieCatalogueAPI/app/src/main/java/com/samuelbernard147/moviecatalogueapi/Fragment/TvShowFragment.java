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

import com.samuelbernard147.moviecatalogueapi.DetailActivity;
import com.samuelbernard147.moviecatalogueapi.ItemClickSupport;
import com.samuelbernard147.moviecatalogueapi.Movie;
import com.samuelbernard147.moviecatalogueapi.MovieAdapter;
import com.samuelbernard147.moviecatalogueapi.MovieLoader;
import com.samuelbernard147.moviecatalogueapi.R;

import java.util.ArrayList;
import java.util.Locale;

public class TvShowFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    ProgressBar progressBarTv;
    RecyclerView recyclerViewTv;
    ArrayList<Movie> listTv;
    String language;
    MovieAdapter adapter;
    Bundle bundle;

    static final int TV_ID = 101;
    static final String TVLANG = "extra_language";

//    Key untuk saveinstancestate
    static final String STATE_TV = "state_TV";
    static final String STATE_LANG = "state_language";

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Recyclerview
        recyclerViewTv = view.findViewById(R.id.rv_tv);
        showRecyler();

//        Progress bar muncul
        progressBarTv = view.findViewById(R.id.pb_tvShow);
        progressBarTv.setVisibility(View.VISIBLE);

//      Inisiasi dari Loader, dimasukkan ke dalam onCreate
        getLoaderManager().initLoader(1, bundle, TvShowFragment.this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//       Save Data Fragment
        outState.putParcelableArrayList(STATE_TV, this.listTv);
        outState.putString(STATE_LANG, this.language);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //Restore Data Fragment
            ArrayList<Movie> stateData = savedInstanceState.getParcelableArrayList(STATE_TV);
            this.listTv = stateData;
            this.language = savedInstanceState.getString(STATE_LANG);

//            Setdata ke adapter
            adapter.setData(stateData);

//            Menghilangkan progress bar
            progressBarTv.setVisibility(View.INVISIBLE);

//            Menghentikan loader
            getLoaderManager().destroyLoader(1);
        }
    }

    void showRecyler() {
        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();
        recyclerViewTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewTv.setAdapter(adapter);

//        Item Click support
        ItemClickSupport.addTo(recyclerViewTv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Movie selectedTv = listTv.get(position);
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(DetailActivity.EXTRA_ID, selectedTv.getId());
                i.putExtra(DetailActivity.EXTRA_LANG, language);
                i.putExtra(DetailActivity.EXTRA_TYPE, "tv");
                startActivity(i);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.clearData();
        getLoaderManager().destroyLoader(1);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, @Nullable Bundle args) {
//        Menentukan bahasa
        this.language = Locale.getDefault().getLanguage();
        if (args != null) {
            language = args.getString(TVLANG);
        }
        return new MovieLoader(getActivity(), TV_ID, language);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> tv) {
        this.listTv = tv;
        adapter.setData(tv);
        stopLoader(1);
//        Progress bar menghilang
        progressBarTv.setVisibility(View.INVISIBLE);
    }

    void stopLoader(int id) {
        getLoaderManager().destroyLoader(id);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        adapter.setData(null);
    }
}
