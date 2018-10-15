package com.mh.android.musicsearch.api;

import com.mh.android.musicsearch.model.Music;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface of api resource
 */
public interface ApiInterface {

    @GET("/search?term=amirkhan&media=music")
    Call<Music> getListOfMusic();

    @GET("/search")
    Call<Music> getListOfMusic(@Query("term") String search);
}
