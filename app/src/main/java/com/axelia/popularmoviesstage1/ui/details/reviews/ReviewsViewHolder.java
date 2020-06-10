package com.axelia.popularmoviesstage1.ui.details.reviews;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.axelia.popularmoviesstage1.data.model.Review;
import com.axelia.popularmoviesstage1.databinding.ItemReviewBinding;

public class ReviewsViewHolder extends RecyclerView.ViewHolder {

    private ItemReviewBinding binding;

    public ReviewsViewHolder(@NonNull ItemReviewBinding binding) {
        super(binding.getRoot());

        this.binding = binding;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.frame.setClipToOutline(false);
        }
    }

    public void bindTo(final Review review) {
        String userName = review.getAuthor();

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(userName.substring(0, 1).toUpperCase(), color);
        binding.imageviewAuthor.setImageDrawable(drawable);
        binding.textviewAuthor.setText(userName);
        binding.textviewContent.setText(review.getContent());
        binding.executePendingBindings();
    }

    public static ReviewsViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemReviewBinding binding = ItemReviewBinding.inflate(layoutInflater, parent, false);
        return new ReviewsViewHolder(binding);
    }
}
