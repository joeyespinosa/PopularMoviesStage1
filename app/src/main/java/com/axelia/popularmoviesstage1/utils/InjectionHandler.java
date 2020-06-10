package com.axelia.popularmoviesstage1.utils;


import com.axelia.popularmoviesstage1.data.MovieRepository;
import com.axelia.popularmoviesstage1.data.remote.api.ApiClient;

public class InjectionHandler {

    public static MovieRepository provideMovieRepository() {
        return new MovieRepository(ApiClient.getInstance(), AppExecutors.getInstance());
    }
}
