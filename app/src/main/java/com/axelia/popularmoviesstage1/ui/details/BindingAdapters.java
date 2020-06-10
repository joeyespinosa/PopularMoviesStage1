package com.axelia.popularmoviesstage1.ui.details;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.axelia.popularmoviesstage1.R;
import com.axelia.popularmoviesstage1.data.model.Genre;
import com.axelia.popularmoviesstage1.utils.Constants;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BindingAdapters {

    @BindingAdapter({"imageUrl", "isBackdrop"})
    public static void bindImage(ImageView imageView, String imagePath, boolean isBackdrop) {
        String baseUrl;
        if (isBackdrop) {
            baseUrl = Constants.BACKDROP_URL;
        } else {
            baseUrl = Constants.IMAGE_URL;
        }

        Picasso.get()
                .load(baseUrl + imagePath)
                .placeholder(android.R.color.darker_gray)
                .into(imageView);
    }

    @BindingAdapter({"imageUrl"})
    public static void bindImage(ImageView imageView, String imagePath) {
        Picasso.get()
                .load(Constants.IMAGE_URL + imagePath)
                .placeholder(android.R.color.darker_gray)
                .into(imageView);
    }

    @BindingAdapter("visibleGone")
    public static void showHide(View view, Boolean show) {
        if (show) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }

    @BindingAdapter("items")
    public static void setItems(ChipGroup view, List<Genre> genres) {
        if (genres == null
                // Since we are using liveData to observe data, any changes in that table(favorites)
                // will trigger the observer and hence rebinding data, which can lead to duplicates.
                || view.getChildCount() > 0)
            return;

        Context context = view.getContext();
        for (Genre genre : genres) {
            Chip chip = new Chip(context);
            chip.setText(genre.getName());
            chip.setChipStrokeWidth(dipToPixels(context, 1));
            chip.setChipStrokeColor(ColorStateList.valueOf(
                    context.getResources().getColor(android.R.color.darker_gray)));
            chip.setChipBackgroundColorResource(android.R.color.transparent);
            view.addView(chip);
        }
    }

    public static float dipToPixels(Context context, float dipValue){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,  dipValue, metrics);
    }

}
