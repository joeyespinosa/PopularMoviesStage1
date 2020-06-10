package com.axelia.popularmoviesstage1.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;


import com.axelia.popularmoviesstage1.data.model.Review;

import java.util.List;

@Dao
public interface ReviewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllReview(List<Review> reviews);

}
