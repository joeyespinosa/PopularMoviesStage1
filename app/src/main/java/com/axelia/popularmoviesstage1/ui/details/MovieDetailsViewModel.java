package com.axelia.popularmoviesstage1.ui.details;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.axelia.popularmoviesstage1.R;
import com.axelia.popularmoviesstage1.data.MovieRepository;
import com.axelia.popularmoviesstage1.data.model.MovieDetails;
import com.axelia.popularmoviesstage1.data.model.Resource;
import com.axelia.popularmoviesstage1.utils.SnackbarPrompt;

import javax.inject.Inject;

import timber.log.Timber;

public class MovieDetailsViewModel extends ViewModel {

    private final MovieRepository repository;
    private LiveData<Resource<MovieDetails>> result;
    private MutableLiveData<Long> movieIdLiveData = new MutableLiveData<>();
    private final SnackbarPrompt mSnackbarText = new SnackbarPrompt();
    private boolean isFavorite;

    @Inject
    public MovieDetailsViewModel(final MovieRepository repository) {
        this.repository = repository;
    }

    public void init(long movieId) {
        if (result != null) {
            return;
        }
        Timber.d("Initializing viewModel");

        result = Transformations.switchMap(movieIdLiveData,
                new Function<Long, LiveData<Resource<MovieDetails>>>() {
                    @Override
                    public LiveData<Resource<MovieDetails>> apply(Long movieId) {
                        return repository.loadMovie(movieId);
                    }
                });

        setMovieIdLiveData(movieId); // trigger loading movie
    }

    public LiveData<Resource<MovieDetails>> getResult() {
        return result;
    }

    public SnackbarPrompt getSnackbarPrompt() {
        return mSnackbarText;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    private void setMovieIdLiveData(long movieId) {
        movieIdLiveData.setValue(movieId);
    }

    public void retry(long movieId) {
        setMovieIdLiveData(movieId);
    }

    public void onFavoriteClicked() {
        MovieDetails movieDetails = result.getValue().data;
        if (!isFavorite) {
            repository.favoriteMovie(movieDetails.getMovie());
            isFavorite = true;
            showSnackbarPrompt(R.string.movie_added_successfully);
        } else {
            repository.removeAsFavoriteMovie(movieDetails.getMovie());
            isFavorite = false;
            showSnackbarPrompt(R.string.movie_removed_successfully);
        }
    }

    private void showSnackbarPrompt(Integer message) {
        mSnackbarText.setValue(message);
    }
}
