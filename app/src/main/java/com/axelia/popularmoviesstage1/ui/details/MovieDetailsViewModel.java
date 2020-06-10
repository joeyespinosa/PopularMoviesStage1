package com.axelia.popularmoviesstage1.ui.details;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.axelia.popularmoviesstage1.data.MovieRepository;
import com.axelia.popularmoviesstage1.data.model.Movie;
import com.axelia.popularmoviesstage1.data.model.RepoMovieDetailsResult;
import com.axelia.popularmoviesstage1.data.remote.api.NetworkState;

public class MovieDetailsViewModel extends ViewModel {

    private MovieRepository repository;
    private LiveData<RepoMovieDetailsResult> resultLiveData;
    private MutableLiveData<Long> movieId = new MutableLiveData<>();
    private LiveData<Movie> movieLiveData;

    private LiveData<NetworkState> networkState;

    public MovieDetailsViewModel(final MovieRepository repository) {
        this.repository = repository;
        resultLiveData = Transformations.map(movieId, new Function<Long, RepoMovieDetailsResult>() {
            @Override
            public RepoMovieDetailsResult apply(Long input) {
                return repository.getMovie(input);
            }
        });
        movieLiveData = Transformations.switchMap(resultLiveData,
                new Function<RepoMovieDetailsResult, LiveData<Movie>>() {
                    @Override
                    public LiveData<Movie> apply(RepoMovieDetailsResult input) {
                        return input.data;
                    }
                });
        networkState = Transformations.switchMap(resultLiveData,
                new Function<RepoMovieDetailsResult, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(RepoMovieDetailsResult input) {
                        return input.networkState;
                    }
                });
    }

    LiveData<Movie> getMovieLiveData() {
        return movieLiveData;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    void setMovieId(long movieId) {
        this.movieId.setValue(movieId);
    }

    void retrySetMovieId(long movieId) {
        setMovieId(movieId);
    }
}
