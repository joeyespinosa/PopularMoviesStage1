package com.axelia.popularmoviesstage1.di.modules;

import android.app.Application;

import androidx.room.Room;

import com.axelia.popularmoviesstage1.data.database.MoviesDatabase;
import com.axelia.popularmoviesstage1.data.local.dao.CastsDao;
import com.axelia.popularmoviesstage1.data.local.dao.MoviesDao;
import com.axelia.popularmoviesstage1.data.local.dao.ReviewsDao;
import com.axelia.popularmoviesstage1.data.local.dao.TrailersDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomDatabaseModule {

    private MoviesDatabase moviesDatabase;

    public RoomDatabaseModule(Application application) {
        moviesDatabase = Room.databaseBuilder(application, MoviesDatabase.class, MoviesDatabase.DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    MoviesDatabase provideDatabase() {
        return moviesDatabase;
    }

    @Singleton
    @Provides
    CastsDao provideCastsDao() {
        return moviesDatabase.castsDao();
    }

    @Singleton
    @Provides
    MoviesDao provideMoviesDao() {
        return moviesDatabase.moviesDao();
    }

    @Singleton
    @Provides
    ReviewsDao provideReviewsDao() {
        return moviesDatabase.reviewsDao();
    }

    @Singleton
    @Provides
    TrailersDao provideTrailersDao() {
        return moviesDatabase.trailersDao();
    }

}

