package com.axelia.popularmoviesstage1.ui.movielist.browse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;

import com.axelia.popularmoviesstage1.R;
import com.axelia.popularmoviesstage1.ui.movielist.favorites.FavoritesFragment;
import com.axelia.popularmoviesstage1.utils.ActivityUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewFragment();
        setupToolbar();
        setupBottomNavigation();
    }
    private void setupViewFragment() {
        BrowseMoviesFragment browseMoviesFragment = BrowseMoviesFragment.newInstance();
        ActivityUtils.replaceFragment(
                getSupportFragmentManager(), browseMoviesFragment, R.id.fragment_container);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_browse:
                        ActivityUtils.replaceFragment(
                                getSupportFragmentManager(), BrowseMoviesFragment.newInstance(),
                                R.id.fragment_container);
                        return true;
                    case R.id.action_favorites:
                        ActivityUtils.replaceFragment(
                                getSupportFragmentManager(), FavoritesFragment.newInstance(),
                                R.id.fragment_container);
                        return true;
                }
                return false;
            }
        });
    }
}
