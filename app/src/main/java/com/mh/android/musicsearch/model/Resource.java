package com.mh.android.musicsearch.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Generic class that contains data and status about loading data.
 * @param <T>
 */
public class Resource<T> {
    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable public final String message;
    private Resource(@NonNull Status status, @Nullable T data,
                     @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    /**
     * When request gets success
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    /**
     * Method for unsuccessful backend request
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, null, msg);
    }

    /**
     * Method for no internet connection
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Resource<T> noInternet(String msg,T data) {
        return new Resource<>(Status.NO_INTERNET, data, msg);
    }

    public enum Status { SUCCESS, ERROR, NO_INTERNET }
}
