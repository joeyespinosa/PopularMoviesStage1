package com.axelia.popularmoviesstage1.di.modules;

import android.app.Application;

import androidx.room.Room;

import com.axelia.popularmoviesstage1.data.database.MoviesDatabase;
import com.axelia.popularmoviesstage1.data.local.MoviesLocalDataSource;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = RoomDatabaseModule.class
)
public class MoviesLocalDataSourceModule {

    @Singleton
    @Provides
    MoviesLocalDataSource provideMoviesLocalDataSource(MoviesDatabase database) {
        return new MoviesLocalDataSource(database);
    }
}

