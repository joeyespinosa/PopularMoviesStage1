package com.axelia.popularmoviesstage1.ui.movielist.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.axelia.popularmoviesstage1.MovieApplication;
import com.axelia.popularmoviesstage1.R;
import com.axelia.popularmoviesstage1.data.model.Movie;
import com.axelia.popularmoviesstage1.databinding.FragmentFavoriteMoviesBinding;
import com.axelia.popularmoviesstage1.ui.details.MovieDetailsViewModel;
import com.axelia.popularmoviesstage1.ui.movielist.browse.MainActivity;
import com.axelia.popularmoviesstage1.utils.FavoritesViewModelFactory;
import com.axelia.popularmoviesstage1.utils.ItemOffsetDecoration;
import com.axelia.popularmoviesstage1.utils.MovieDetailsViewModelFactory;
import com.axelia.popularmoviesstage1.utils.ViewModelFactory;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel viewModel;
    private FragmentFavoriteMoviesBinding binding;

    @Inject
    FavoritesViewModelFactory factory;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieApplication.getComponent(Objects.requireNonNull(getActivity())).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.favorites));
        viewModel = ViewModelProviders.of(this, factory).get(FavoritesViewModel.class);
        setupListAdapter();
    }

    private void setupListAdapter() {
        RecyclerView recyclerView = binding.moviesList.recyclerviewMovieList;
        final FavoritesAdapter favoritesAdapter = new FavoritesAdapter();
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);

        recyclerView.setAdapter(favoritesAdapter);
        recyclerView.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        
        viewModel.getFavoriteListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movieList) {
                if (movieList.isEmpty()) {
                    binding.moviesList.recyclerviewMovieList.setVisibility(View.GONE);
                    binding.textviewEmptyState.setVisibility(View.VISIBLE);
                } else {
                    binding.moviesList.recyclerviewMovieList.setVisibility(View.VISIBLE);
                    binding.textviewEmptyState.setVisibility(View.GONE);
                    favoritesAdapter.submitList(movieList);
                }
            }
        });
    }
}
