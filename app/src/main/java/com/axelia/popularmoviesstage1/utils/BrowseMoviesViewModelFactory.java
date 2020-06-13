package com.axelia.popularmoviesstage1.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.axelia.popularmoviesstage1.data.MovieRepository;
import com.axelia.popularmoviesstage1.ui.details.MovieDetailsViewModel;
import com.axelia.popularmoviesstage1.ui.movielist.browse.BrowseMoviesViewModel;
import com.axelia.popularmoviesstage1.ui.movielist.favorites.FavoritesViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BrowseMoviesViewModelFactory implements ViewModelProvider.Factory {

    private final MovieRepository repository;

    @Inject
    BrowseMoviesViewModelFactory(MovieRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BrowseMoviesViewModel(repository);
    }
}
