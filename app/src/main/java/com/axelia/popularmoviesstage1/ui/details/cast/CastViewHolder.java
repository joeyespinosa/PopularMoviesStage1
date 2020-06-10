package com.axelia.popularmoviesstage1.ui.details.cast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axelia.popularmoviesstage1.R;
import com.axelia.popularmoviesstage1.data.model.Cast;
import com.axelia.popularmoviesstage1.databinding.ItemCastBinding;
import com.axelia.popularmoviesstage1.utils.Constants;
import com.squareup.picasso.Picasso;

public class CastViewHolder extends RecyclerView.ViewHolder {

    private ItemCastBinding binding;
    private Context context;

    public CastViewHolder(@NonNull ItemCastBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
    }

    public void bindTo(final Cast cast) {
        String profileImage =
                Constants.IMAGE_BASE_URL + Constants.PROFILE_SIZE_W200 + cast.getProfilePath();
        Picasso.get()
                .load(profileImage)
                .placeholder(android.R.color.darker_gray)
                .into(binding.circleimageviewCastProfile);
        binding.textviewCastName.setText(cast.getActorName());
        binding.executePendingBindings();
    }

    public static CastViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemCastBinding binding = ItemCastBinding.inflate(layoutInflater, parent, false);
        return new CastViewHolder(binding, parent.getContext());
    }
}
