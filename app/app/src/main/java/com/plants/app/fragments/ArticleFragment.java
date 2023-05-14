package com.plants.app.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plants.app.adapters.ReadAndWrite;
import com.plants.app.adapters.plants.Plant;
import com.plants.app.databinding.FragmentArticleBinding;

public class ArticleFragment extends Fragment {
    private FragmentArticleBinding binding;

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
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(plant.getName());
            binding.name.setText(plant.getName());
            binding.WateringInfo.setText(plant.getWatering());
            binding.InsolationInfo.setText(plant.getInsolation());
            binding.PruningInfo.setText(plant.getPruning());
            binding.SoilInfo.setText(plant.getSoil());
            binding.PotSizeInfo.setText(plant.getPot_size());

            Drawable drawable = ReadAndWrite.importImage(getContext(),plant.getPicture());
            binding.imagePlant.setImageDrawable(drawable);
        }
        else Log.e("ArticleFragment", "Argument not found");
    }
}