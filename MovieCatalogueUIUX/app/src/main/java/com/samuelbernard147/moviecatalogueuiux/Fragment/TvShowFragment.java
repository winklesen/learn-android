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
public class TvShowFragment extends Fragment {
    private RecyclerView rvTv;
    private ArrayList<Movie> list;
    String[] dataTitle, dataDescription, dataReleaseDate, dataRating, dataWriter, dataDirector, dataStar, dataGenre;
    TypedArray dataPoster;
    int[] dataDuration;


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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        rvTv = view.findViewById(R.id.rv_tv);
        rvTv.setHasFixedSize(true);

        list = new ArrayList<>();
        prepareDataTv();
        addDataTv();
        showRecycler();
    }

    private void showRecycler(){
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        MovieAdapter movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.setListMovie(list);
        rvTv.setAdapter(movieAdapter);

//        Item Click support
        ItemClickSupport.addTo(rvTv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Movie selectedTv = new Movie();
                selectedTv.setJudul(dataTitle[position]);
                selectedTv.setDeskripsi(dataDescription[position]);
                selectedTv.setDurasi(dataDuration[position]);
                selectedTv.setRating(dataRating[position]);
                selectedTv.setTglRilis(dataReleaseDate[position]);
                selectedTv.setPoster(dataPoster.getResourceId(position, -1));
                selectedTv.setGenre(dataGenre[position]);
                selectedTv.setDirector(dataDirector[position]);
                selectedTv.setWriter(dataWriter[position]);
                selectedTv.setStar(dataStar[position]);
                showselectedTv(selectedTv);
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(DetailActivity.EXTRA_MOVIE, selectedTv);
                startActivity(i);
            }
        });
    }

    //    Toast untuk Tv yang dipilih
    private void showselectedTv(Movie tv) {
        Toast.makeText(getActivity(), tv.getJudul(), Toast.LENGTH_SHORT).show();
    }

    //    Mengambil data tv
    public void prepareDataTv() {
        dataTitle = getResources().getStringArray(R.array.tv_title);
        dataDescription = getResources().getStringArray(R.array.tv_description);
        dataReleaseDate = getResources().getStringArray(R.array.tv_release_date);
        dataPoster = getResources().obtainTypedArray(R.array.tv_poster);
        dataDuration = getResources().getIntArray(R.array.tv_duration);
        dataRating = getResources().getStringArray(R.array.tv_rating);
        dataGenre = getResources().getStringArray(R.array.tv_genre);
        dataDirector = getResources().getStringArray(R.array.tv_director);
        dataWriter = getResources().getStringArray(R.array.tv_writer);
        dataStar = getResources().getStringArray(R.array.tv_star);
    }

    //    Set data Tv kedalam POJO
    public void addDataTv(){
        ArrayList<Movie> listData = new ArrayList<>();
        for (int i = 0; i < dataTitle.length; i++){
            Movie tv = new Movie();
            tv.setJudul(dataTitle[i]);
            tv.setDeskripsi(dataDescription[i]);
            tv.setDurasi(dataDuration[i]);
            tv.setPoster(dataPoster.getResourceId(i, -1));
            tv.setRating(dataRating[i]);
            tv.setTglRilis(dataReleaseDate[i]);
            tv.setGenre(dataGenre[i]);
            tv.setDirector(dataDirector[i]);
            tv.setWriter(dataWriter[i]);
            tv.setStar(dataStar[i]);
            listData.add(tv);
        }
        list.addAll(listData);
    }
}