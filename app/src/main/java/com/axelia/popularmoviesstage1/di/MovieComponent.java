package com.axelia.popularmoviesstage1.di;

import com.axelia.popularmoviesstage1.di.modules.AppExecutorsModule;
import com.axelia.popularmoviesstage1.di.modules.MovieServiceModule;
import com.axelia.popularmoviesstage1.di.modules.MoviesLocalDataSourceModule;
import com.axelia.popularmoviesstage1.di.modules.MoviesRemoteDataSourceModule;
import com.axelia.popularmoviesstage1.di.modules.RoomDatabaseModule;
import com.axelia.popularmoviesstage1.ui.details.DetailsActivity;
import com.axelia.popularmoviesstage1.ui.movielist.browse.BrowseMoviesFragment;
import com.axelia.popularmoviesstage1.ui.movielist.favorites.FavoritesFragment;
import com.axelia.popularmoviesstage1.utils.AppExecutors;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {
        AppExecutorsModule.class,
        MoviesRemoteDataSourceModule.class,
        MoviesLocalDataSourceModule.class,
        RoomDatabaseModule.class,
        MovieServiceModule.class
})
public interface MovieComponent {

    void inject(DetailsActivity detailsActivity);
    void inject(BrowseMoviesFragment browseMoviesFragment);
    void inject(FavoritesFragment favoritesFragment);
}
