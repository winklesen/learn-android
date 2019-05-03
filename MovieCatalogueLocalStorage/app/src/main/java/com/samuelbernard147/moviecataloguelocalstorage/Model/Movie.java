package com.samuelbernard147.moviecataloguelocalstorage.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

public class Movie implements Parcelable {
    private int id;
    private String title;
    private String poster;
    private String overview;

    public final String TYPE_MOVIE = "movie";
    public final String TYPE_TV = "tv";

    public Movie() {

    }

    /*
     * Method untuk setMovie melalui API themoviedb
     * parameter berupa JsonArray, posisi object,
     * dan identify untuk menentukan tipe yang akan di load (Movie/Tv)
     */
    public void setMovie(JSONArray listObject, int posisi, int identify) {
        if (identify == 100) {
            try {
                int id = listObject.getJSONObject(posisi).getInt("id");
                String title = listObject.getJSONObject(posisi).getString("title");
                String poster = listObject.getJSONObject(posisi).getString("poster_path");
                String overview = listObject.getJSONObject(posisi).getString("overview");

                this.id = id;
                this.title = title;
                this.poster = poster;
                this.overview = overview;

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                int id = listObject.getJSONObject(posisi).getInt("id");
                String title = listObject.getJSONObject(posisi).getString("original_name");
                String poster = listObject.getJSONObject(posisi).getString("poster_path");
                String overview = listObject.getJSONObject(posisi).getString("overview");

                this.id = id;
                this.title = title;
                this.poster = poster;
                this.overview = overview;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //    Getter Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    //  Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.poster);
        dest.writeString(this.overview);
    }

    private Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.poster = in.readString();
        this.overview = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}