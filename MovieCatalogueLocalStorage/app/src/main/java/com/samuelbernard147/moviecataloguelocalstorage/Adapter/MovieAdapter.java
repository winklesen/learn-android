package com.samuelbernard147.moviecataloguelocalstorage.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samuelbernard147.moviecataloguelocalstorage.Model.Movie;
import com.samuelbernard147.moviecataloguelocalstorage.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final ArrayList<Movie> movieList = new ArrayList<>();


    public MovieAdapter() {

    }

    //    Set list movie
    public void setListMovie(ArrayList<Movie> listMovie) {
        if (listMovie.size() > 0) {
            movieList.clear();
        }
        movieList.addAll(listMovie);
        notifyDataSetChanged();
    }

    //    Clear list movie
    public void clearListMovie() {
        movieList.clear();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_row, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.tvTitle.setText(movieList.get(position).getTitle());
        holder.tvDesc.setText(movieList.get(position).getOverview());
//        Poster menggunakan urlnya tersendiri
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" + movieList.get(position).getPoster())
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgPoster;
        final TextView tvTitle, tvDesc;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            imgPoster = itemView.findViewById(R.id.img_poster);
        }
    }
}