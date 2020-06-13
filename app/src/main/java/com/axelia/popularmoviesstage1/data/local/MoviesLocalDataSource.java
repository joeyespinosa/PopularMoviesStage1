package com.axelia.popularmoviesstage1.data.local;

import androidx.lifecycle.LiveData;

import com.axelia.popularmoviesstage1.data.database.MoviesDatabase;
import com.axelia.popularmoviesstage1.data.model.Cast;
import com.axelia.popularmoviesstage1.data.model.Movie;
import com.axelia.popularmoviesstage1.data.model.MovieDetails;
import com.axelia.popularmoviesstage1.data.model.Review;
import com.axelia.popularmoviesstage1.data.model.Trailer;
import com.axelia.popularmoviesstage1.utils.AppExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;


@Singleton
public class MoviesLocalDataSource {

    private final MoviesDatabase mDatabase;

    @Inject
    public MoviesLocalDataSource(MoviesDatabase database) {
        mDatabase = database;
    }

    public void saveMovie(Movie movie) {
        mDatabase.moviesDao().insertMovie(movie);
        insertTrailers(movie.getTrailersResponse().getTrailers(), movie.getId());
        insertCastList(movie.getCreditsResponse().getCast(), movie.getId());
        insertReviews(movie.getReviewsResponse().getReviews(), movie.getId());
    }

    private void insertReviews(List<Review> reviews, long movieId) {
        for (Review review : reviews) {
            review.setMovieId(movieId);
        }
        mDatabase.reviewsDao().insertAllReview(reviews);
        Timber.d("%s reviews saved into database.", reviews.size());
    }

    private void insertCastList(List<Cast> castList, long movieId) {
        for (Cast cast : castList) {
            cast.setMovieId(movieId);
        }
        mDatabase.castsDao().insertAllCasts(castList);
        Timber.d("%s cast saved into database.", castList.size());
    }

    private void insertTrailers(List<Trailer> trailers, long movieId) {
        for (Trailer trailer : trailers) {
            trailer.setMovieId(movieId);
        }
        mDatabase.trailersDao().insertAllTrailer(trailers);
        Timber.d("%s trailers saved into database.", trailers.size());
    }

    public LiveData<MovieDetails> getMovie(long movieId) {
        return mDatabase.moviesDao().getMovie(movieId);
    }

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return mDatabase.moviesDao().getAllFavoriteMovies();
    }

    public void favoriteMovie(Movie movie) {
        mDatabase.moviesDao().favoriteMovie(movie.getId());
    }

    public void removeAsFavortiteMovie(Movie movie) {
        mDatabase.moviesDao().removeAsFavoriteMovie(movie.getId());
    }
}
