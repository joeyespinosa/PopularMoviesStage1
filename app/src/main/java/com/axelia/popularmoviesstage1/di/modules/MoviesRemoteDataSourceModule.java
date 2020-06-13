package com.axelia.popularmoviesstage1.di.modules;

import com.axelia.popularmoviesstage1.data.remote.MoviesRemoteDataSource;
import com.axelia.popularmoviesstage1.data.remote.api.ApiClient;
import com.axelia.popularmoviesstage1.data.remote.api.MovieService;
import com.axelia.popularmoviesstage1.utils.AppExecutors;

import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = {
                AppExecutorsModule.class,
                MovieServiceModule.class
        }
)
public class MoviesRemoteDataSourceModule {

    @Singleton
    @Provides
    MoviesRemoteDataSource provideMoviesRemoteDataSource(MovieService movieService, AppExecutors executors) {
        return new MoviesRemoteDataSource(movieService, executors);
    }
}

