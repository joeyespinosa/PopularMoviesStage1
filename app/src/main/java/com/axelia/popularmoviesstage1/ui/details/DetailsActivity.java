package com.axelia.popularmoviesstage1.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.axelia.popularmoviesstage1.R;
import com.axelia.popularmoviesstage1.data.model.Movie;
import com.axelia.popularmoviesstage1.data.remote.api.NetworkState;
import com.axelia.popularmoviesstage1.data.remote.api.Status;
import com.axelia.popularmoviesstage1.databinding.ActivityDetailsBinding;
import com.axelia.popularmoviesstage1.utils.Constants;
import com.axelia.popularmoviesstage1.utils.InjectionHandler;
import com.axelia.popularmoviesstage1.utils.ViewModelFactory;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    private static final int DEFAULT_ID = -1;
    private ActivityDetailsBinding mBinding;
    private MovieDetailsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        final long movieId = intent.getLongExtra(EXTRA_MOVIE_ID, DEFAULT_ID);
        if (movieId == DEFAULT_ID) {
            closeOnError();
        }

        setupToolbar();

        ViewModelFactory factory = ViewModelFactory.getInstance(InjectionHandler.provideMovieRepository());
        mViewModel = ViewModelProviders.of(this, factory).get(MovieDetailsViewModel.class);

        if (savedInstanceState == null) {
            mViewModel.setMovieId(movieId);
        }

        mViewModel.getNetworkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                handleNetworkState(networkState);
            }
        });

        mViewModel.getMovieLiveData().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                updateUi(movie);
            }
        });

        mBinding.retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.retrySetMovieId(movieId);
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            handleCollapsedToolbarTitle();
        }
    }

    private void updateUi(Movie movie) {
        Picasso.get()
                .load(Constants.BACKDROP_URL + movie.getBackdropPath())
                .into(mBinding.imageviewMovieBackdrop);

        Picasso.get()
                .load(Constants.IMAGE_URL + movie.getPosterPath())
                .into(mBinding.imagePoster);

        mBinding.textviewTitle.setText(movie.getTitle());
        mBinding.textviewReleaseDate.setText(movie.getReleaseDate());
        mBinding.textviewVote.setText(String.valueOf(movie.getVoteAverage()));
        mBinding.textviewOverview.setText(movie.getOverview());
        mBinding.labelVote.setText("Rating");
        mBinding.executePendingBindings();
    }

    private void closeOnError() {
        throw new RuntimeException("Access denied.");
    }

    private void handleCollapsedToolbarTitle() {
        mBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                //verify if the toolbar is completely collapsed and set the movie name as the title
                if (scrollRange + verticalOffset == 0) {
                    mBinding.collapsingToolbar.setTitle(
                            mViewModel.getMovieLiveData().getValue().getTitle());
                    isShow = true;
                } else if (isShow) {
                    //display an empty string when toolbar is expanded
                    mBinding.collapsingToolbar.setTitle(" ");
                    isShow = false;
                }

            }
        });
    }

    private void handleNetworkState(NetworkState networkState) {
        boolean isLoaded = networkState == NetworkState.LOADED;
        mBinding.appbar.setVisibility(isVisible(isLoaded));
        mBinding.progressBar.setVisibility(
                isVisible(networkState.getStatus() == Status.RUNNING));
        mBinding.retryButton.setVisibility(
                isVisible(networkState.getStatus() == Status.FAILED));
        mBinding.errorMsg.setVisibility(
                isVisible(networkState.getMsg() != null));
        mBinding.errorMsg.setText(networkState.getMsg());
    }

    private int isVisible(boolean condition) {
        if (condition)
            return View.VISIBLE;
        else
            return View.GONE;
    }
}
