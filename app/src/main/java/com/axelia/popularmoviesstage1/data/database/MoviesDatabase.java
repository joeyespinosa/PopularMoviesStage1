package com.axelia.popularmoviesstage1.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.axelia.popularmoviesstage1.data.local.dao.CastsDao;
import com.axelia.popularmoviesstage1.data.local.dao.MoviesDao;
import com.axelia.popularmoviesstage1.data.local.dao.ReviewsDao;
import com.axelia.popularmoviesstage1.data.local.dao.TrailersDao;
import com.axelia.popularmoviesstage1.data.model.Cast;
import com.axelia.popularmoviesstage1.data.model.Movie;
import com.axelia.popularmoviesstage1.data.model.Review;
import com.axelia.popularmoviesstage1.data.model.Trailer;
import com.axelia.popularmoviesstage1.data.model.UtilConverters;


@Database(
        entities = {Movie.class, Trailer.class, Cast.class, Review.class},
        version = 1,
        exportSchema = false)
@TypeConverters(UtilConverters.class)
public abstract class MoviesDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "movies.db";

    private static MoviesDatabase INSTANCE;

    private static final Object sLock = new Object();

    public abstract MoviesDao moviesDao();

    public abstract TrailersDao trailersDao();

    public abstract CastsDao castsDao();

    public abstract ReviewsDao reviewsDao();

    public static MoviesDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = buildDatabase(context);
            }
            return INSTANCE;
        }
    }

    private static MoviesDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                MoviesDatabase.class,
                DATABASE_NAME).build();
    }
}
