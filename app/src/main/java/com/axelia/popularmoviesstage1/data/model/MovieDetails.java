package com.axelia.popularmoviesstage1.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MovieDetails {

    @Embedded
    public Movie movie;

    @Relation(parentColumn = "id", entityColumn = "movie_id")
    public List<Trailer> trailerList;

    @Relation(parentColumn = "id", entityColumn = "movie_id")
    public List<Cast> castList;

    @Relation(parentColumn = "id", entityColumn = "movie_id")
    public List<Review> reviewList;

    public MovieDetails(Movie movie, List<Trailer> trailerList, List<Cast> castList, List<Review> reviewList) {
        this.movie = movie;
        this.trailerList = trailerList;
        this.castList = castList;
        this.reviewList = reviewList;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Trailer> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }

    public List<Cast> getCastList() {
        return castList;
    }

    public void setCastList(List<Cast> castList) {
        this.castList = castList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }
}
