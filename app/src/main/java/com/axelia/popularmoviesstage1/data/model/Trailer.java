package com.axelia.popularmoviesstage1.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "trailer",
        foreignKeys = @ForeignKey(entity = Movie.class,
            parentColumns = "id",
            childColumns = "movie_id",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE),
        indices = @Index(value = "movie_id"))
public class Trailer {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    public String id;

    @ColumnInfo(name = "movie_id")
    Long movieId;

    @SerializedName("key")
    String key;

    @SerializedName("site")
    String site;

    @SerializedName("name")
    String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
