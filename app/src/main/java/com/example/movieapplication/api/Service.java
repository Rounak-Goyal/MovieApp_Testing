package com.example.movieapplication.api;

import com.example.movieapplication.model.ResponseNowPlaying;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Service {
    String IMAGE_BASE_URL_500 = "https://image.tmdb.org/t/p/w500";
    String IMAGE_BASE_URL_1280 = "https://image.tmdb.org/t/p/w1280";

    @GET("movie/now_playing")
    Call<ResponseNowPlaying> getNowPlaying(
            @Query("api_key") String api_key,
            @Query("page") int page
    );
}

