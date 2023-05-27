package com.plants.app.screens;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plants.app.DataModel;
import com.plants.app.utils.ReadAndWrite;
import com.plants.app.plants.Plant;
import com.plants.app.databinding.FragmentArticleBinding;

public class ArticleFragment extends Fragment {
    private FragmentArticleBinding binding;
    private DataModel dataModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataModel = new ViewModelProvider(requireActivity()).get(DataModel.class);
    }

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
            replacementArticle(getArguments(), getContext());
        }
        else Log.e("ArticleFragment", "Argument not found");
    }

    private void replacementArticle(Bundle bundle, Context context){
        Plant plant = dataModel.getRoot().getPlant(bundle.getString("Plant"));

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(plant.getName());

        binding.name.setText(plant.getName());
        binding.WateringInfo.setText(plant.getWatering());
        binding.InsolationInfo.setText(plant.getInsolation());
        binding.PruningInfo.setText(plant.getPruning());
        binding.SoilInfo.setText(plant.getSoil());
        binding.PotSizeInfo.setText(plant.getPot_size());

        binding.imagePlant.setImageBitmap(ReadAndWrite.importImage(getContext(),plant.getPicture()));
    }
}