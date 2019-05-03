package com.samuelbernard147.moviecatalogue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Movie> movies;

    //    Setter
    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    //    Constructor
    public MovieAdapter(Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
        }

        Movie movie = (Movie) getItem(position);
        TextView txtTitle = view.findViewById(R.id.tv_title);
        TextView txtDesc = view.findViewById(R.id.tv_desc);
        ImageView imgPoster = view.findViewById(R.id.img_poster);
        txtTitle.setText(movie.getJudul());
        txtDesc.setText(movie.getDeskripsi());
        Glide.with(view).load(movie.getPoster()).into(imgPoster);
//        imgPoster.setImageResource(movie.getPoster());
        return view;

    }
}