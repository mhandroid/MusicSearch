package com.mh.android.musicsearch.api;

import android.content.Context;

import com.mh.android.musicsearch.utils.ConnectivityInterceptor;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class to responsible to create api client for retrofit.
 */
public class ApiClient {
    private static final String BASE_URL = "https://itunes.apple.com/";
    private static Retrofit retrofit = null;


    /**
     * to get the retrofit client object
     *
     * @return Retrofit
     */
    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new ConnectivityInterceptor(context))
                    .addInterceptor(httpLoggingInterceptor)

                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

}
