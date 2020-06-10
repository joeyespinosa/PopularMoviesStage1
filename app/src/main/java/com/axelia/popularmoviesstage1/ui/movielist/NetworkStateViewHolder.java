package com.axelia.popularmoviesstage1.ui.movielist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axelia.popularmoviesstage1.data.remote.api.NetworkState;
import com.axelia.popularmoviesstage1.data.remote.api.Status;
import com.axelia.popularmoviesstage1.databinding.ItemNetworkStateBinding;

public class NetworkStateViewHolder extends RecyclerView.ViewHolder {

    private ItemNetworkStateBinding binding;

    public NetworkStateViewHolder(@NonNull ItemNetworkStateBinding binding,
                                  final BrowseMoviesViewModel viewModel) {
        super(binding.getRoot());
        this.binding = binding;

        binding.retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.retry();
            }
        });
    }

    static NetworkStateViewHolder create(ViewGroup parent, BrowseMoviesViewModel viewModel) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemNetworkStateBinding binding = ItemNetworkStateBinding.inflate(layoutInflater, parent, false);
        return new NetworkStateViewHolder(binding, viewModel);
    }

    void bindTo(NetworkState networkState) {
        binding.progressBar.setVisibility(
                isVisible(networkState.getStatus() == Status.RUNNING));
        binding.retryButton.setVisibility(
                isVisible(networkState.getStatus() == Status.FAILED));
        binding.errorMsg.setVisibility(
                isVisible(networkState.getMsg() != null));
        binding.errorMsg.setText(networkState.getMsg());
    }

    private int isVisible(boolean condition) {
        if (condition)
            return View.VISIBLE;
        else
            return View.GONE;
    }
}
