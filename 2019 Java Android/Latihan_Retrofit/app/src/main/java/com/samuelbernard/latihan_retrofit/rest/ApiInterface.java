package com.samuelbernard.latihan_retrofit.rest;

import com.samuelbernard.latihan_retrofit.model.Student;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    //    @GET("depok/{periode}/dayli.json")
//    Call<Items> getSchedule(@Path("periode") String periode);

    @GET("api.php?method=get-data")
    Call<Student> getAllStudent();
}