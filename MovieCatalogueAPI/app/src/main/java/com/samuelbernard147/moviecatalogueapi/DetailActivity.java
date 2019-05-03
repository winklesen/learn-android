package com.samuelbernard147.moviecatalogueapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DetailActivity extends AppCompatActivity {
    //    Key Extra untuk Intent
    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_LANG = "extra_language";
    public static final String EXTRA_TYPE = "extra_type";

    //    Key untuk saveinstancestate
    static final String STATE_TITLE = "movie_title";
    static final String STATE_RELEASE = "movie_release_date";
    static final String STATE_LANG = "movie_language";
    static final String STATE_OVERVIEW = "movie_overview";
    static final String STATE_DURATION = "movie_duration";
    static final String STATE_POSTER = "movie_poster";
    static final String STATE_BACKDROP = "movie_backdrop";

    ImageView imgPoster, imgBackdrop;
    TextView tvTitle, tvReleaseDate, tvOriginalLang, tvTime, tvOverview;
    TextView tvReleaseHead, tvLangHead, tvTimeHead, tvOverviewHead;
    ProgressBar progressBarDetail;
    int idMovie;
    String langMovie;
    String type;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imgPoster = findViewById(R.id.img_poster);
        imgBackdrop = findViewById(R.id.img_backdrop);
        tvTitle = findViewById(R.id.tv_title);
        tvReleaseDate = findViewById(R.id.tv_date);
        tvOriginalLang = findViewById(R.id.tv_lang);
        tvTime = findViewById(R.id.tv_time);
        tvOverview = findViewById(R.id.tv_desc);
        progressBarDetail = findViewById(R.id.pb_detail);

        tvReleaseHead = findViewById(R.id.tv_date_head);
        tvLangHead = findViewById(R.id.tv_lang_head);
        tvTimeHead = findViewById(R.id.tv_time_head);
        tvOverviewHead = findViewById(R.id.tv_desc_head);

//        Getextra
        idMovie = getIntent().getIntExtra(EXTRA_ID, 0);
        langMovie = getIntent().getStringExtra(EXTRA_LANG);
        type = getIntent().getStringExtra(EXTRA_TYPE);

//        Menampilkan backbutton
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Movie Catalogue");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        Bila state tidak kosong maka akan diambil data yang ada didalamnya
        if (savedInstanceState != null) {
            progressBarDetail.setVisibility(View.INVISIBLE);
            String title = (String) savedInstanceState.get(STATE_TITLE);
            String release = (String) savedInstanceState.get(STATE_RELEASE);
            String lang = (String) savedInstanceState.get(STATE_LANG);
            String time = (String) savedInstanceState.get(STATE_DURATION);
            String overview = (String) savedInstanceState.get(STATE_OVERVIEW);
            Bitmap poster = savedInstanceState.getParcelable(STATE_POSTER);
            Bitmap backdrop = savedInstanceState.getParcelable(STATE_BACKDROP);

            tvTitle.setText(title);
            tvReleaseDate.setText(release);
            tvOriginalLang.setText(lang);
            tvTime.setText(time);
            tvOverview.setText(overview);

//            Set image dari bitmap
            imgPoster.setImageBitmap(poster);
            imgBackdrop.setImageBitmap(backdrop);

        } else {
//        Menampilkan Progress bar
            progressBarDetail.setVisibility(View.VISIBLE);

            tvOverviewHead.setVisibility(View.INVISIBLE);
            tvTimeHead.setVisibility(View.INVISIBLE);
            tvLangHead.setVisibility(View.INVISIBLE);
            tvReleaseHead.setVisibility(View.INVISIBLE);

//        Memanggil fungsi load data
            getDataMovie();
        }
    }

    //    Untuk back
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //    Menyimpan data
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_TITLE, tvTitle.getText().toString());
        outState.putString(STATE_RELEASE, tvReleaseDate.getText().toString());
        outState.putString(STATE_OVERVIEW, tvOverview.getText().toString());
        outState.putString(STATE_DURATION, tvTime.getText().toString());
        outState.putString(STATE_LANG, tvOriginalLang.getText().toString());
        outState.putParcelable(STATE_POSTER, convertBitmap(imgPoster));
        outState.putParcelable(STATE_BACKDROP, convertBitmap(imgBackdrop));
    }

    //    Fungsi merubah imageview ke dalam bentuk bitmap
    private Bitmap convertBitmap(ImageView imageView) {
        return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    }

    //    Untuk load data movie
    public void getDataMovie() {
        AsyncHttpClient client = new AsyncHttpClient();

//        Default language
        String language = "en-US";

//        tipe movie
        final String movieType = this.type;

//        Menentukan bahasa
        if (this.langMovie.equals("en")) {
            language = "en-US";
        } else if (this.langMovie.equals("in")) {
            language = "id-ID";
        }

//        URL
        String url = "https://api.themoviedb.org/3/" + movieType + "/" + idMovie + "?api_key=" + MovieLoader.API_KEY + "&language=" + language;
        client.get(this, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject object = new JSONObject(result);
//                    set data sesuai dengan tipe (movie/tv)
                    if (movieType.equals("movie")) {
                        String dataTitle = object.getString("title");
                        String dataReleaseDate = object.getString("release_date");
                        String dataOriginalLang = object.getString("original_language");
                        String dataRuntime = object.getString("runtime");
                        String dataOverview = object.getString("overview");
                        String dataPoster = object.getString("poster_path");
                        String dataBackdrop = object.getString("backdrop_path");

//                    Load detail
                        tvTitle.setText(dataTitle);
                        tvReleaseDate.setText(dataReleaseDate);
                        tvOriginalLang.setText(dataOriginalLang);
                        tvTime.setText(String.format(getResources().getString(R.string.minute), dataRuntime));
                        tvOverview.setText(dataOverview);

//                    Load gambar
                        Picasso.get()
                                .load("https://image.tmdb.org/t/p/w500" + dataPoster)
                                .into(imgPoster);

                        Picasso.get()
                                .load("https://image.tmdb.org/t/p/w500" + dataBackdrop)
                                .into(imgBackdrop);
                    } else {
                        String dataTitle = object.getString("name");
                        String dataReleaseDate = object.getString("first_air_date");
                        String dataOriginalLang = object.getString("original_language");
                        String dataRuntime = object.getJSONArray("episode_run_time").getString(0);
                        String dataOverview = object.getString("overview");
                        String dataPoster = object.getString("poster_path");
                        String dataBackdrop = object.getString("backdrop_path");

//                    Load detail
                        tvTitle.setText(dataTitle);
                        tvReleaseDate.setText(dataReleaseDate);
                        tvOriginalLang.setText(dataOriginalLang);
                        tvTime.setText(String.format(getResources().getString(R.string.minute), dataRuntime));
                        tvOverview.setText(dataOverview);

//                    Load gambar
                        Picasso.get()
                                .load("https://image.tmdb.org/t/p/w500" + dataPoster)
                                .into(imgPoster);

                        Picasso.get()
                                .load("https://image.tmdb.org/t/p/w500" + dataBackdrop)
                                .into(imgBackdrop);

                    }
//                    Menghilangkan Progress bar
                    progressBarDetail.setVisibility(View.INVISIBLE);

                    tvOverviewHead.setVisibility(View.VISIBLE);
                    tvTimeHead.setVisibility(View.VISIBLE);
                    tvLangHead.setVisibility(View.VISIBLE);
                    tvReleaseHead.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, getResources().getString(R.string.fail), Toast.LENGTH_SHORT).show();
            }
        });
    }
}