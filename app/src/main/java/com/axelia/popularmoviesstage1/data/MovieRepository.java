package com.axelia.popularmoviesstage1.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.axelia.popularmoviesstage1.data.local.MoviesLocalDataSource;
import com.axelia.popularmoviesstage1.data.model.Movie;
import com.axelia.popularmoviesstage1.data.model.MovieDetails;
import com.axelia.popularmoviesstage1.data.model.MoviesFilterType;
import com.axelia.popularmoviesstage1.data.model.RepoMoviesResult;
import com.axelia.popularmoviesstage1.data.model.Resource;
import com.axelia.popularmoviesstage1.data.remote.MoviesRemoteDataSource;
import com.axelia.popularmoviesstage1.data.remote.api.ApiResponse;
import com.axelia.popularmoviesstage1.utils.AppExecutors;

import java.util.List;


import timber.log.Timber;

public class MovieRepository implements DataSource {

    private static volatile MovieRepository sInstance;
    private final MoviesLocalDataSource mLocalDataSource;
    private final MoviesRemoteDataSource mRemoteDataSource;
    private final AppExecutors mExecutors;

    private MovieRepository(MoviesLocalDataSource localDataSource,
                            MoviesRemoteDataSource remoteDataSource,
                            AppExecutors executors) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
        mExecutors = executors;
    }

    public static MovieRepository getInstance(MoviesLocalDataSource localDataSource,
                                              MoviesRemoteDataSource remoteDataSource,
                                              AppExecutors executors) {
        if (sInstance == null) {
            synchronized (MovieRepository.class) {
                if (sInstance == null) {
                    sInstance = new MovieRepository(localDataSource, remoteDataSource, executors);
                }
            }
        }
        return sInstance;
    }

    @Override
    public LiveData<Resource<MovieDetails>> loadMovie(final long movieId) {
        return new NetworkBoundResource<MovieDetails, Movie>(mExecutors) {

            @Override
            protected void saveCallResult(@NonNull Movie item) {
                mLocalDataSource.saveMovie(item);
                Timber.d("Movie saved to database");
            }

            @Override
            protected boolean shouldFetch(@Nullable MovieDetails data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<MovieDetails> loadFromDb() {
                Timber.d("Loading movie");
                return mLocalDataSource.getMovie(movieId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Movie>> createCall() {
                Timber.d("Downloading movie");
                return mRemoteDataSource.loadMovieDetails(movieId);
            }

            @NonNull
            @Override
            protected void onFetchFailed() {
                Timber.d("Fetch failed");
            }
        }.getAsLiveData();
    }

    @Override
    public RepoMoviesResult loadFilteredMovie(MoviesFilterType filterType) {
        return mRemoteDataSource.loadMoviesFilteredBy(filterType);
    }

    @Override
    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return mLocalDataSource.getAllFavoriteMovies();
    }

    @Override
    public void favoriteMovie(final Movie movie) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Timber.d("Adding movie to favorites");
                mLocalDataSource.favoriteMovie(movie);
            }
        });
    }

    @Override
    public void removeAsFavoriteMovie(final Movie movie) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Timber.d("Removing movie from favorites");
                mLocalDataSource.removeAsFavortiteMovie(movie);
            }
        });
    }
}
