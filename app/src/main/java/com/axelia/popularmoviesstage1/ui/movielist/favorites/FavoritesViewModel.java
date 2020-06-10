package com.axelia.popularmoviesstage1.ui.movielist.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.axelia.popularmoviesstage1.data.MovieRepository;
import com.axelia.popularmoviesstage1.data.model.Movie;

import java.util.List;


public class FavoritesViewModel extends ViewModel {

    private LiveData<List<Movie>> favoriteListLiveData;

    public FavoritesViewModel(MovieRepository repository) {
        favoriteListLiveData = repository.getAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getFavoriteListLiveData() {
        return favoriteListLiveData;
    }
}
