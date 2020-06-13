package com.axelia.popularmoviesstage1.ui.movielist.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.axelia.popularmoviesstage1.MovieApplication;
import com.axelia.popularmoviesstage1.R;
import com.axelia.popularmoviesstage1.data.model.Movie;
import com.axelia.popularmoviesstage1.data.model.MoviesFilterType;
import com.axelia.popularmoviesstage1.data.model.Resource;
import com.axelia.popularmoviesstage1.ui.details.MovieDetailsViewModel;
import com.axelia.popularmoviesstage1.utils.BrowseMoviesViewModelFactory;
import com.axelia.popularmoviesstage1.utils.ItemOffsetDecoration;
import com.axelia.popularmoviesstage1.utils.MovieDetailsViewModelFactory;
import com.axelia.popularmoviesstage1.utils.ViewModelFactory;

import java.util.Objects;

import javax.inject.Inject;

public class BrowseMoviesFragment extends Fragment {

    private BrowseMoviesViewModel viewModel;

    @Inject
    BrowseMoviesViewModelFactory factory;

    public static BrowseMoviesFragment newInstance() {
        return new BrowseMoviesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieApplication.getComponent(Objects.requireNonNull(getActivity())).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_list_movies, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this, factory).get(BrowseMoviesViewModel.class);
        setupListAdapter();

        viewModel.getCurrentTitle().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer title) {
                ((MainActivity) getActivity()).getSupportActionBar().setTitle(title);
            }
        });
    }

    //region Options Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main, menu);

        if (viewModel.getCurrentSorting() == MoviesFilterType.POPULAR) {
            menu.findItem(R.id.action_popular_movies).setChecked(true);
        } else if (viewModel.getCurrentSorting() == MoviesFilterType.TOP_RATED) {
            menu.findItem(R.id.action_top_rated).setChecked(true);
        } else {
            menu.findItem(R.id.action_now_playing).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getGroupId() == R.id.menu_sort_group) {
            viewModel.setSortMoviesBy(item.getItemId());
            item.setChecked(true);
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion

    private void setupListAdapter() {
        RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerview_movie_list);
        final BrowseMoviesAdapter browseMoviesAdapter =
                new BrowseMoviesAdapter(viewModel);
        final GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.span_count));

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (browseMoviesAdapter.getItemViewType(position)) {
                    case R.layout.item_network_state:
                        return layoutManager.getSpanCount();
                    default:
                        return 1;
                }
            }
        });

        recyclerView.setAdapter(browseMoviesAdapter);
        recyclerView.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);


        viewModel.getPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                browseMoviesAdapter.submitList(movies);
            }
        });


        viewModel.getNetworkState().observe(getViewLifecycleOwner(), new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {
                browseMoviesAdapter.setNetworkState(resource);
            }
        });
    }
}
