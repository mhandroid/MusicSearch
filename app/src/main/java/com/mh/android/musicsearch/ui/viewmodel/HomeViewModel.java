package com.mh.android.musicsearch.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.mh.android.musicsearch.UserRepository;
import com.mh.android.musicsearch.model.Music;
import com.mh.android.musicsearch.model.Resource;

/**
 * View model class to provide list of music
 */
public class HomeViewModel extends AndroidViewModel {
    private UserRepository mUserRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mUserRepository = new UserRepository(application);
    }


    /**
     * Method to fetch list of music
     * @return Observable object of @Music model
     */
    public LiveData<Resource<Music>> getMusicList() {
        return mUserRepository.fetchFromWeb();
    }

    /**
     * Method to fetch list of music based on search string
     * @param @String search key
     * @return Observable object of music model
     */
    public LiveData<Resource<Music>> getMusicList(String search) {
        return mUserRepository.fetchFromWeb(search);
    }
}
