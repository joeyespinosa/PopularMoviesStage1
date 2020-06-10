package com.axelia.popularmoviesstage1.data;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.axelia.popularmoviesstage1.data.model.Movie;
import com.axelia.popularmoviesstage1.data.model.MoviesFilterType;
import com.axelia.popularmoviesstage1.data.model.RepoMovieDetailsResult;
import com.axelia.popularmoviesstage1.data.model.RepoMoviesResult;
import com.axelia.popularmoviesstage1.data.remote.api.MovieService;
import com.axelia.popularmoviesstage1.data.remote.api.NetworkState;
import com.axelia.popularmoviesstage1.data.remote.paging.MovieDataSourceFactory;
import com.axelia.popularmoviesstage1.data.remote.paging.MoviePageKeyedDataSource;
import com.axelia.popularmoviesstage1.utils.AppExecutors;

import java.io.IOException;

import retrofit2.Response;

public class MovieRepository implements DataSource {

    private static final int PAGE_SIZE = 30;
    private MovieService mMovieService;
    private AppExecutors mExecutors;

    public MovieRepository(MovieService movieService,
                            AppExecutors executors) {
        mMovieService = movieService;
        mExecutors = executors;
    }

    @Override
    public RepoMovieDetailsResult getMovie(final long movieId) {
        final MutableLiveData<NetworkState> networkState = new MutableLiveData<>();
        final MutableLiveData<Movie> movieLiveData = new MutableLiveData<>();
        networkState.setValue(NetworkState.LOADING);
        mExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<Movie> response = mMovieService.getMovieDetails(movieId).execute();
                    networkState.postValue(NetworkState.LOADED);
                    movieLiveData.postValue(response.body());
                } catch (IOException e) {
                    NetworkState error = NetworkState.error(e.getMessage());
                    networkState.postValue(error);
                }
            }
        });
        return new RepoMovieDetailsResult(movieLiveData, networkState);
    }

    @Override
    public RepoMoviesResult getFilteredMoviesBy(MoviesFilterType filterType) {
        MovieDataSourceFactory sourceFactory =
                new MovieDataSourceFactory(mMovieService, mExecutors.networkIO(), filterType);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();

        LiveData<PagedList<Movie>> moviesPagedList = new LivePagedListBuilder<>(sourceFactory, config)
                .setFetchExecutor(mExecutors.networkIO())
                .build();

        LiveData<NetworkState> networkState = Transformations.switchMap(sourceFactory.sourceLiveData,
                new Function<MoviePageKeyedDataSource, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(MoviePageKeyedDataSource input) {
                        return input.networkState;
                    }
                });

        return new RepoMoviesResult(
                moviesPagedList,
                networkState,
                sourceFactory.sourceLiveData
        );
    }
}
