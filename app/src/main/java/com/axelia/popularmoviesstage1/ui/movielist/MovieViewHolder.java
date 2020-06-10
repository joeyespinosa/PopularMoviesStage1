package com.axelia.popularmoviesstage1.ui.movielist;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axelia.popularmoviesstage1.data.model.Movie;
import com.axelia.popularmoviesstage1.databinding.ItemMovieBinding;
import com.axelia.popularmoviesstage1.ui.details.DetailsActivity;
import com.axelia.popularmoviesstage1.utils.Constants;
import com.squareup.picasso.Picasso;


public class MovieViewHolder extends RecyclerView.ViewHolder {

    private final ItemMovieBinding binding;

    public MovieViewHolder(@NonNull ItemMovieBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    void bindTo(final Movie movie) {

        Picasso.get()
                .load(Constants.IMAGE_URL + movie.getPosterPath())
                .placeholder(android.R.color.holo_red_dark)
                .into(binding.imageviewMoviePoster);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_MOVIE_ID, movie.getId());
                view.getContext().startActivity(intent);
            }
        });

        binding.executePendingBindings();
    }

    static MovieViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMovieBinding binding = ItemMovieBinding.inflate(layoutInflater, parent, false);
        return new MovieViewHolder(binding);
    }
}
