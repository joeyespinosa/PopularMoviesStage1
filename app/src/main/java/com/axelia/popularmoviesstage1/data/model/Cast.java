package com.axelia.popularmoviesstage1.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "cast",
        foreignKeys = @ForeignKey(entity = Movie.class,
            parentColumns = "id",
            childColumns = "movie_id",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE),
        indices = @Index(value = "movie_id"))
public class Cast {

    @PrimaryKey
    @NonNull
    @SerializedName("credit_id")
    public String id;

    @ColumnInfo(name = "movie_id")
    Long movieId = 0L;

    @SerializedName("character")
    String characterName = null;

    @SerializedName("gender")
    Integer gender = 0;

    @SerializedName("name")
    String actorName = null;

    @SerializedName("order")
    Integer order = 0;

    @SerializedName("profile_path")
    String profilePath = null;

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

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
