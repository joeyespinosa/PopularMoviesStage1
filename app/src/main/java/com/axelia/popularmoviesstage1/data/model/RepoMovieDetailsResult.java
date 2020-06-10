package com.axelia.popularmoviesstage1.data.model;

import androidx.lifecycle.LiveData;

import com.axelia.popularmoviesstage1.data.remote.api.NetworkState;

public class RepoMovieDetailsResult {
    public LiveData<Movie> data;
    public LiveData<NetworkState> networkState;

    public RepoMovieDetailsResult(LiveData<Movie> data, LiveData<NetworkState> networkState) {
        this.data = data;
        this.networkState = networkState;
    }
}
