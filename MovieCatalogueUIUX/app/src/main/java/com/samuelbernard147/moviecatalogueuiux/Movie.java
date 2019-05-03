package com.samuelbernard147.moviecatalogueuiux;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String judul;
    private String deskripsi;
    private String tglRilis;
    private String rating;
    private String genre;
    private String director;
    private String writer;
    private String star;
    private int poster;
    private int durasi;

    //    Getter and setter
    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTglRilis() {
        return tglRilis;
    }

    public void setTglRilis(String tglRilis) {
        this.tglRilis = tglRilis;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public int getDurasi() {
        return durasi;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }

    //    Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.judul);
        dest.writeString(this.deskripsi);
        dest.writeString(this.tglRilis);
        dest.writeString(this.rating);
        dest.writeString(this.genre);
        dest.writeString(this.director);
        dest.writeString(this.writer);
        dest.writeString(this.star);
        dest.writeInt(this.poster);
        dest.writeInt(this.durasi);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.judul = in.readString();
        this.deskripsi = in.readString();
        this.tglRilis = in.readString();
        this.rating = in.readString();
        this.genre = in.readString();
        this.director = in.readString();
        this.writer = in.readString();
        this.star = in.readString();
        this.poster = in.readInt();
        this.durasi = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
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
