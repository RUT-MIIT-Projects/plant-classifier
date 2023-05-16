package com.plants.app.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plants.app.R;
import com.plants.app.adapters.ImageClassifier;
import com.plants.app.adapters.JSONHelper;
import com.plants.app.databinding.FragmentArticlesBinding;

public class ArticlesFragment extends Fragment {
    private FragmentArticlesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentArticlesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.echinocactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                broadcast(ImageClassifier.getPlants()[0], view, getContext());
            }
        });
        binding.mimosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                broadcast(ImageClassifier.getPlants()[1], view, getContext());
            }
        });
        binding.monstera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                broadcast(ImageClassifier.getPlants()[2], view, getContext());
            }
        });
        binding.orchid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                broadcast(ImageClassifier.getPlants()[3], view, getContext());
            }
        });
        binding.rose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                broadcast(ImageClassifier.getPlants()[4], view, getContext());
            }
        });

    }

    public static void broadcast(String namePlant, View view, Context context){
        Bundle bundle = new Bundle();
        bundle.putParcelable("Plant", JSONHelper.importJsonPlants(context).importPlant(namePlant,context));
        Navigation.findNavController(view).navigate(R.id.action_articlesFragment_to_articleFragment,bundle);
    }

}