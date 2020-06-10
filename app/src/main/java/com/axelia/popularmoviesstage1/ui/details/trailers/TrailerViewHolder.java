package com.axelia.popularmoviesstage1.ui.details.trailers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axelia.popularmoviesstage1.R;
import com.axelia.popularmoviesstage1.data.model.Trailer;
import com.axelia.popularmoviesstage1.databinding.ItemTrailerBinding;
import com.axelia.popularmoviesstage1.utils.Constants;
import com.squareup.picasso.Picasso;


public class TrailerViewHolder extends RecyclerView.ViewHolder {

    private ItemTrailerBinding binding;

    private Context context;

    public TrailerViewHolder(@NonNull ItemTrailerBinding binding, Context context) {
        super(binding.getRoot());

        this.binding = binding;
        this.context = context;
    }

    public void bindTo(final Trailer trailer) {
        String thumbnail =
                "https://img.youtube.com/vi/" + trailer.getKey() + "/hqdefault.jpg";

        Picasso.get()
                .load(thumbnail)
                .placeholder(android.R.color.darker_gray)
                .into(binding.imageviewTrailer);
        binding.trailerName.setText(trailer.getTitle());
        binding.cardviewTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("vnd.youtube:" + trailer.getKey()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(Constants.YOUTUBE_WEB_URL + trailer.getKey()));
                if (appIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(appIntent);
                } else {
                    context.startActivity(webIntent);
                }
            }
        });

        binding.executePendingBindings();
    }

    public static TrailerViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTrailerBinding binding = ItemTrailerBinding.inflate(layoutInflater, parent, false);
        return new TrailerViewHolder(binding, parent.getContext());
    }
}
