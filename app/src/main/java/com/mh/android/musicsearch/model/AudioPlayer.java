package com.mh.android.musicsearch.model;

import android.support.annotation.NonNull;

/**
 * Model class for Audio player status
 */
public class AudioPlayer {
    public final AudioStatus status;
    public final int position;

    private AudioPlayer(@NonNull AudioStatus status, int position) {
        this.status = status;
        this.position = position;

    }

    public static AudioPlayer playState(AudioStatus status,int position) {
        return new AudioPlayer(status,position);
    }

    public enum AudioStatus {PLAY,PLAYING, PAUSE, RESET,COMPLETED,PREPARED,START,STOP}
}
