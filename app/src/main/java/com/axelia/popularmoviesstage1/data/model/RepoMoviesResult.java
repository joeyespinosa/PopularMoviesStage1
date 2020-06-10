package com.axelia.popularmoviesstage1.data.model;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.axelia.popularmoviesstage1.data.remote.api.NetworkState;
import com.axelia.popularmoviesstage1.data.remote.paging.MoviePageKeyedDataSource;

public class RepoMoviesResult {
    public LiveData<PagedList<Movie>> data;
    public LiveData<NetworkState> networkState;
    public MutableLiveData<MoviePageKeyedDataSource> sourceLiveData;

    public RepoMoviesResult(LiveData<PagedList<Movie>> data, LiveData<NetworkState> networkState,
                            MutableLiveData<MoviePageKeyedDataSource> sourceLiveData) {
        this.data = data;
        this.networkState = networkState;
        this.sourceLiveData = sourceLiveData;
    }
}
