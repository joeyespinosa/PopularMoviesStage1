package com.axelia.popularmoviesstage1;

import android.app.Application;
import android.content.Context;

import androidx.databinding.library.BuildConfig;

import com.axelia.popularmoviesstage1.di.DaggerMovieComponent;
import com.axelia.popularmoviesstage1.di.MovieComponent;
import com.axelia.popularmoviesstage1.di.modules.AppExecutorsModule;
import com.axelia.popularmoviesstage1.di.modules.MovieServiceModule;
import com.axelia.popularmoviesstage1.di.modules.MoviesLocalDataSourceModule;
import com.axelia.popularmoviesstage1.di.modules.MoviesRemoteDataSourceModule;
import com.axelia.popularmoviesstage1.di.modules.RoomDatabaseModule;
import com.axelia.popularmoviesstage1.utils.AppExecutors;

import timber.log.Timber;

public class MovieApplication extends Application {

    private MovieComponent component;

    public static MovieComponent getComponent(Context context) {
        return ((MovieApplication) context.getApplicationContext()).component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        component = DaggerMovieComponent.builder()
                .appExecutorsModule(new AppExecutorsModule())
                .roomDatabaseModule(new RoomDatabaseModule(this))
                .movieServiceModule(new MovieServiceModule())
                .moviesLocalDataSourceModule(new MoviesLocalDataSourceModule())
                .moviesRemoteDataSourceModule(new MoviesRemoteDataSourceModule())
                .build();
    }
}
