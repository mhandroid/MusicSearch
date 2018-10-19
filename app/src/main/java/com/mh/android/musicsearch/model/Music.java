
package com.mh.android.musicsearch.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for song track
 */
public class Music implements Serializable {

    @SerializedName("resultCount")
    private Integer resultCount;
    @SerializedName("results")
    private List<Track> results = null;

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public List<Track> getResults() {
        return results;
    }

    public void setResults(List<Track> results) {
        this.results = results;
    }

}
