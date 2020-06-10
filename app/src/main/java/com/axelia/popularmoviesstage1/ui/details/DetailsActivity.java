package com.axelia.popularmoviesstage1.ui.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.axelia.popularmoviesstage1.R;
import com.axelia.popularmoviesstage1.data.model.MovieDetails;
import com.axelia.popularmoviesstage1.data.model.Resource;
import com.axelia.popularmoviesstage1.databinding.ActivityDetailsBinding;
import com.axelia.popularmoviesstage1.ui.details.cast.CastAdapter;
import com.axelia.popularmoviesstage1.ui.details.reviews.ReviewsAdapter;
import com.axelia.popularmoviesstage1.ui.details.trailers.TrailersAdapter;
import com.axelia.popularmoviesstage1.utils.Constants;
import com.axelia.popularmoviesstage1.utils.InjectionHandler;
import com.axelia.popularmoviesstage1.utils.ViewModelFactory;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    private static final int DEFAULT_ID = -1;
    private ActivityDetailsBinding mBinding;
    private MovieDetailsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeLight);
        super.onCreate(savedInstanceState);

        final long movieId = getIntent().getLongExtra(EXTRA_MOVIE_ID, DEFAULT_ID);
        if (movieId == DEFAULT_ID) {
            closeOnError();
            return;
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        mBinding.setLifecycleOwner(this);

        mViewModel = obtainViewModel();
        mViewModel.init(movieId);
        setupToolbar();
        setupTrailersAdapter();
        setupCastAdapter();
        setupReviewsAdapter();

        mViewModel.getResult().observe(this, new Observer<Resource<MovieDetails>>() {
            @Override
            public void onChanged(Resource<MovieDetails> resource) {
                if (resource.data != null &&
                        resource.data.getMovie() != null) {
                    mViewModel.setFavorite(resource.data.getMovie().getFavorite());
                    invalidateOptionsMenu();
                }
                mBinding.setResource(resource);
                mBinding.setMovieDetailsItem(resource.data);
            }
        });

        mBinding.networkState.retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.retry(movieId);
            }
        });

        mViewModel.getSnackbarPrompt().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer message) {
                Snackbar.make(mBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        NestedScrollView movie_details = findViewById(R.id.movie_details);

        int isVisible = movie_details.getVisibility();

        if (isVisible == View.VISIBLE) {
            Log.d("movie_details", "YES");
        } else {
            Log.d("movie_details", "NO");
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            handleCollapsedToolbarTitle();
        }
    }

    private void setupTrailersAdapter() {
        RecyclerView recyclerviewTrailers = mBinding.movieDetailsInfo.recyclerviewTrailers;
        recyclerviewTrailers.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerviewTrailers.setHasFixedSize(true);
        recyclerviewTrailers.setAdapter(new TrailersAdapter());
        ViewCompat.setNestedScrollingEnabled(recyclerviewTrailers, false);
    }

    private void setupCastAdapter() {
        RecyclerView recyclerviewCast = mBinding.movieDetailsInfo.recyclerviewCast;
        recyclerviewCast.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerviewCast.setAdapter(new CastAdapter());
        ViewCompat.setNestedScrollingEnabled(recyclerviewCast, false);
    }

    private void setupReviewsAdapter() {
        RecyclerView recyclerviewReviews = mBinding.movieDetailsInfo.recyclerviewReviews;
        recyclerviewReviews.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerviewReviews.setAdapter(new ReviewsAdapter());
        ViewCompat.setNestedScrollingEnabled(recyclerviewReviews, false);
    }

    private MovieDetailsViewModel obtainViewModel() {
        ViewModelFactory factory = InjectionHandler.provideViewModelFactory(this);
        return ViewModelProviders.of(this, factory).get(MovieDetailsViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_details, menu);

        MenuItem favoriteItem = menu.findItem(R.id.action_favorite);
        if (mViewModel.isFavorite()) {
            favoriteItem.setIcon(R.drawable.ic_favorite_black_24dp)
                    .setTitle(R.string.action_remove_from_favorites);
        } else {
            favoriteItem.setIcon(R.drawable.ic_favorite_border_black_24dp)
                    .setTitle(R.string.action_favorite);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share: {
                MovieDetails movieDetails = mViewModel.getResult().getValue().data;
                Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                        .setType("text/plain")
                        .setSubject(movieDetails.getMovie().getTitle() + " movie trailer")
                        .setText("Check out " + movieDetails.getMovie().getTitle() + " movie trailer at " +
                                Uri.parse(Constants.YOUTUBE_WEB_URL +
                                        movieDetails.getTrailerList().get(0).getKey())
                        )
                        .createChooserIntent();

                int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
                if (Build.VERSION.SDK_INT >= 21)
                    flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;

                shareIntent.addFlags(flags);
                if (shareIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(shareIntent);
                }
                return true;
            }
            case R.id.action_favorite: {
                mViewModel.onFavoriteClicked();
                invalidateOptionsMenu();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closeOnError() {
        throw new IllegalArgumentException("Access denied.");
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
                // verify if the toolbar is completely collapsed and set the movie name as the title
                if (scrollRange + verticalOffset == 0) {
                    mBinding.collapsingToolbar.setTitle(
                            mViewModel.getResult().getValue().data.getMovie().getTitle());
                    isShow = true;
                } else if (isShow) {
                    // display an empty string when toolbar is expanded
                    mBinding.collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
}
