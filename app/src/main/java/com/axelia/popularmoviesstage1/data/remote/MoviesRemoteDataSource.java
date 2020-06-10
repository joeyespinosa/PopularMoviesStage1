package com.axelia.popularmoviesstage1.data.remote;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.axelia.popularmoviesstage1.data.model.Movie;
import com.axelia.popularmoviesstage1.data.model.MoviesFilterType;
import com.axelia.popularmoviesstage1.data.model.RepoMoviesResult;
import com.axelia.popularmoviesstage1.data.model.Resource;

import com.axelia.popularmoviesstage1.data.remote.api.ApiResponse;
import com.axelia.popularmoviesstage1.data.remote.api.MovieService;
import com.axelia.popularmoviesstage1.data.remote.paging.MovieDataSourceFactory;
import com.axelia.popularmoviesstage1.data.remote.paging.MoviePageKeyedDataSource;
import com.axelia.popularmoviesstage1.utils.*;


public class MoviesRemoteDataSource {

    private static final int PAGE_SIZE = 30;
    private final AppExecutors mExecutors;
    private static volatile MoviesRemoteDataSource sInstance;
    private final MovieService mMovieService;

    private MoviesRemoteDataSource(MovieService movieService,
                                   AppExecutors executors) {
        mMovieService = movieService;
        mExecutors = executors;
    }

    public static MoviesRemoteDataSource getInstance(MovieService movieService,
                                                     AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppExecutors.class) {
                if (sInstance == null) {
                    sInstance = new MoviesRemoteDataSource(movieService, executors);
                }
            }
        }
        return sInstance;
    }

    public LiveData<ApiResponse<Movie>> loadMovieDetails(final long movieId) {
        return mMovieService.getMovieDetails(movieId);
    }


    public RepoMoviesResult loadMoviesFilteredBy(MoviesFilterType filterType) {
        MovieDataSourceFactory sourceFactory =
                new MovieDataSourceFactory(mMovieService, mExecutors.networkIO(), filterType);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();

        LiveData<PagedList<Movie>> moviesPagedList = new LivePagedListBuilder<>(sourceFactory, config)
                .setFetchExecutor(mExecutors.networkIO())
                .build();

        LiveData<Resource> networkState = Transformations.switchMap(sourceFactory.sourceLiveData, new Function<MoviePageKeyedDataSource, LiveData<Resource>>() {
            @Override
            public LiveData<Resource> apply(MoviePageKeyedDataSource input) {
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
