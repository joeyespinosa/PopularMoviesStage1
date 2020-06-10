package com.axelia.popularmoviesstage1.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.axelia.popularmoviesstage1.data.model.Trailer;

import java.util.List;


@Dao
public interface TrailersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllTrailer(List<Trailer> trailers);
}
