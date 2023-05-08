package com.plants.app.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plants.app.adapters.ImportImage;
import com.plants.app.adapters.parser.Plant;
import com.plants.app.databinding.FragmentArticleBinding;

public class ArticleFragment extends Fragment {
    FragmentArticleBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentArticleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            Plant plant = getArguments().getParcelable("Plant");
            binding.name.setText(plant.getName());
            binding.WateringInfo.setText(plant.getWatering());
            binding.InsolationInfo.setText(plant.getInsolation());
            binding.PruningInfo.setText(plant.getPruning());
            binding.SoilInfo.setText(plant.getSoil());
            binding.PotSizeInfo.setText(plant.getPot_size());

            Drawable drawable = ImportImage.importImage(getContext(),plant.getPicture());
            binding.imagePlant.setImageDrawable(drawable);
        }
        else Log.e("ArticleFragment", "Argument not found");
    }
}