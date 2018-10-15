package com.mh.android.musicsearch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mh.android.musicsearch.api.ApiClient;
import com.mh.android.musicsearch.api.ApiInterface;
import com.mh.android.musicsearch.exception.NoNetworkException;
import com.mh.android.musicsearch.model.Music;
import com.mh.android.musicsearch.model.Resource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository class to fetch data
 */
public class UserRepository {
    private static final String TAG = UserRepository.class.getSimpleName();
    private final MutableLiveData<Resource<Music>> data;
    private final MutableLiveData<Resource<Music>> dataQuery;
    private Context mContext;

    public UserRepository(Context context) {
        mContext = context;
        data = new MutableLiveData<>();
        dataQuery = new MutableLiveData<>();
    }

    /**
     * Fetch list of data from network
     *
     * @return
     */
    public LiveData<Resource<Music>> fetchFromWeb() {
        ApiInterface apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);

        Call<Music> call = apiService.getListOfMusic();
        call.enqueue(new Callback<Music>() {
            @Override
            public void onResponse(Call<Music> call, Response<Music> response) {
                if (response.isSuccessful() && response.body() != null) {

                    data.setValue(Resource.success(response.body()));

                } else {
                    data.setValue(Resource.error(response.message(), response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Music> call, @NonNull Throwable t) {
                //network failure :( inform the user and possibly retry
                if (t instanceof NoNetworkException) {
                    Log.d(TAG,"No connectivity exception");
                    data.postValue(Resource.noInternet(t.getMessage(),new Music()));

                } else
                    data.setValue(Resource.error(t.getMessage(), new Music()));

            }
        });


        return data;
    }

    /**
     * Fetch list of data from network
     *
     * @return
     */
    public LiveData<Resource<Music>> fetchFromWeb(String search) {
        ApiInterface apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);

        Call<Music> call = apiService.getListOfMusic(search);
        call.enqueue(new Callback<Music>() {
            @Override
            public void onResponse(Call<Music> call, Response<Music> response) {
                if (response.isSuccessful() && response.body() != null) {

                    dataQuery.setValue(Resource.success(response.body()));

                } else {
                    dataQuery.setValue(Resource.error(response.message(), response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Music> call, @NonNull Throwable t) {
                //network failure :( inform the user and possibly retry
                if (t instanceof NoNetworkException) {
                    Log.d(TAG,"No connectivity exception");
                    dataQuery.setValue(Resource.noInternet(t.getMessage(),new Music()));

                } else
                    dataQuery.setValue(Resource.error(t.getMessage(), new Music()));

            }
        });


        return dataQuery;
    }
}
