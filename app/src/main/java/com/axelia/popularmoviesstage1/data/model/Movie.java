package com.axelia.popularmoviesstage1.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

@Entity(tableName = "movie")
public class Movie {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    public Long id = 0L;

    @SerializedName("title")
    String title;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    String posterPath;

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    String backdropPath;

    @SerializedName("overview")
    String overview;

    @SerializedName("popularity")
    Double popularity = Double.valueOf(0);

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    Double voteAverage = Double.valueOf(0);

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    Integer voteCount = 0;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    String releaseDate;

    @ColumnInfo(name = "genres")
    @SerializedName("genres")
    List<Genre> genres;

    @Ignore
    @SerializedName("videos")
    TrailersResponse trailersResponse;

    @Ignore
    @SerializedName("credits")
    CreditsResponse creditsResponse;

    @Ignore
    @SerializedName("reviews")
    ReviewsResponse reviewsResponse;

    @ColumnInfo(name = "is_favorite")
    Boolean isFavorite = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public TrailersResponse getTrailersResponse() {
        return trailersResponse;
    }

    public void setTrailersResponse(TrailersResponse trailersResponse) {
        this.trailersResponse = trailersResponse;
    }

    public CreditsResponse getCreditsResponse() {
        return creditsResponse;
    }

    public void setCreditsResponse(CreditsResponse creditsResponse) {
        this.creditsResponse = creditsResponse;
    }

    public ReviewsResponse getReviewsResponse() {
        return reviewsResponse;
    }

    public void setReviewsResponse(ReviewsResponse reviewsResponse) {
        this.reviewsResponse = reviewsResponse;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (o == null) {
            return false;
        }
        Movie movie = (Movie) o;
        return id == movie.id &&
                movie.popularity.compareTo(popularity) == 0 &&
                movie.voteAverage.compareTo(voteAverage) == 0 &&
                title == movie.title &&
                posterPath == movie.posterPath &&
                overview == movie.overview &&
                releaseDate == movie.releaseDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, posterPath, overview, popularity, voteAverage, releaseDate);
    }
}
