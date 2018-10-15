package com.mh.android.musicsearch.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.mh.android.musicsearch.model.AudioPlayer;

import java.io.IOException;

/**
 * Activity to play preview track
 */
public class MediaPlayerViewModel extends ViewModel {
    private final MutableLiveData<AudioPlayer> data;
    private MediaPlayer mMediaPlayer;
    private String url;
    private String TAG = MediaPlayerViewModel.class.getSimpleName();
    public boolean startOnlyFirstTime = true;

    /**
     * Constructor to initialize MediaPlayerViewModel class
     */
    public MediaPlayerViewModel() {
        initializeMediaPlayer();
        data = new MutableLiveData<>();
    }

    /**
     * Method to initialize media player
     */
    private void initializeMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stop();
                    data.setValue(AudioPlayer.playState(AudioPlayer.AudioStatus.COMPLETED, 0));
                }
            });

            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });

        }
    }


    /**
     * Observable method to play media player
     * @param url
     * @return
     */
    public LiveData<AudioPlayer> play(String url) {
        if (startOnlyFirstTime) {
            this.url = url;
            Log.d(TAG, url);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mMediaPlayer.setDataSource(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMediaPlayer.prepareAsync();

            startOnlyFirstTime = false;
        }
        return data;
    }

    /**
     * Method to stop media player
     */
    public void stop() {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        data.setValue(AudioPlayer.playState(AudioPlayer.AudioStatus.STOP, 0));
    }

    /**
     * Method to start media player
     */
    public void start() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mMediaPlayer.setDataSource(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMediaPlayer.prepareAsync();
            data.setValue(AudioPlayer.playState(AudioPlayer.AudioStatus.START, mMediaPlayer.getCurrentPosition()));
        }
    }


    /**
     * Method to get whether player is currently running or not.
     * @return media player status
     */
    public boolean isPlaying() {
        if (mMediaPlayer != null)
            return mMediaPlayer.isPlaying();

        return false;
    }

    /**
     * Method to release media player object
     */
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
