package com.axelia.popularmoviesstage1.data;

import com.axelia.popularmoviesstage1.data.model.MoviesFilterType;
import com.axelia.popularmoviesstage1.data.model.RepoMovieDetailsResult;
import com.axelia.popularmoviesstage1.data.model.RepoMoviesResult;

public interface DataSource {

    RepoMovieDetailsResult getMovie(long movieId);

    RepoMoviesResult getFilteredMoviesBy(MoviesFilterType sortBy);
}
