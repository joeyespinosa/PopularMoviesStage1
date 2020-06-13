package com.axelia.popularmoviesstage1.data.remote.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.axelia.popularmoviesstage1.data.remote.api.MovieService;
import com.axelia.popularmoviesstage1.data.model.Movie;
import com.axelia.popularmoviesstage1.data.model.MoviesFilterType;
import com.axelia.popularmoviesstage1.data.model.MoviesResponse;
import com.axelia.popularmoviesstage1.data.model.Resource;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviePageKeyedDataSource extends PageKeyedDataSource<Integer, Movie> {

    private static final int FIRST_PAGE = 1;
    public MutableLiveData<Resource> networkState = new MutableLiveData<>();

    @Inject
    MovieService movieService;

    private final Executor networkExecutor;
    private final MoviesFilterType filterType;
    public RetryCallback retryCallback = null;

    public MoviePageKeyedDataSource(MovieService movieService,
                                    Executor networkExecutor, MoviesFilterType filterType) {
        this.movieService = movieService;
        this.networkExecutor = networkExecutor;
        this.filterType = filterType;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, Movie> callback) {
        networkState.postValue(Resource.loading(null));

        // loadInitial
        Call<MoviesResponse> request;
        if (filterType == MoviesFilterType.POPULAR) {
            request = movieService.getPopularMovies(FIRST_PAGE);
        } else if (filterType == MoviesFilterType.TOP_RATED){
            request = movieService.getTopRatedMovies(FIRST_PAGE);
        } else {
            request = movieService.getNowPlayingMovies(FIRST_PAGE);
        }

        try {
            Response<MoviesResponse> response = request.execute();
            MoviesResponse data = response.body();
            List<Movie> movieList =
                    data != null ? data.getMovies() : Collections.<Movie>emptyList();

            retryCallback = null;
            networkState.postValue(Resource.success(null));
            callback.onResult(movieList, null, FIRST_PAGE + 1);
        } catch (IOException e) {
            retryCallback = new RetryCallback() {
                @Override
                public void invokeCallback() {
                    networkExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            loadInitial(params, callback);
                        }
                    });

                }
            };
            networkState.postValue(Resource.error(e.getMessage(), null));
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, Movie> callback) {
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params,
                          @NonNull final LoadCallback<Integer, Movie> callback) {
        // loadAfter
        networkState.postValue(Resource.loading(null));

        Call<MoviesResponse> request;
        if (filterType == MoviesFilterType.POPULAR) {
            request = movieService.getPopularMovies(params.key);
        } else if (filterType == MoviesFilterType.TOP_RATED) {
            request = movieService.getTopRatedMovies(params.key);
        } else {
            request = movieService.getNowPlayingMovies(params.key);
        }

        request.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    MoviesResponse data = response.body();
                    List<Movie> movieList = data != null ? data.getMovies() : Collections.<Movie>emptyList();
                    retryCallback = null;
                    callback.onResult(movieList, params.key + 1);
                    networkState.postValue(Resource.success(null));
                } else {
                    retryCallback = new RetryCallback() {
                        @Override
                        public void invokeCallback() {
                            loadAfter(params, callback);
                        }
                    };
                    networkState.postValue(Resource.error("Error Code: " + response.code(), null));
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                retryCallback = new RetryCallback() {
                    @Override
                    public void invokeCallback() {
                        networkExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                loadAfter(params, callback);
                            }
                        });
                    }
                };
                networkState.postValue(Resource.error(t != null ? t.getMessage() : "Unknown error", null));
            }
        });
    }

    public interface RetryCallback {
        void invokeCallback();
    }

}
