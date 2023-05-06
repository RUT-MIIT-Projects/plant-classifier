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
import com.plants.app.adapters.parser.JSONHelper;
import com.plants.app.adapters.parser.Plant;
import com.plants.app.databinding.FragmentArticlesBinding;

public class ArticlesFragment extends Fragment {
    FragmentArticlesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentArticlesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = new Bundle();

        binding.echinocactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putParcelable("Plant", importPlant("Кактус",getContext()));
                Navigation.findNavController(view).navigate(R.id.action_articlesFragment_to_articleFragment,bundle);
            }
        });
        binding.mimosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putParcelable("Plant", importPlant("Мимоза",getContext()));
                Navigation.findNavController(view).navigate(R.id.action_articlesFragment_to_articleFragment,bundle);
            }
        });
        binding.monstera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putParcelable("Plant", importPlant("Монстера",getContext()));
                Navigation.findNavController(view).navigate(R.id.action_articlesFragment_to_articleFragment,bundle);
            }
        });
        binding.orchid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putParcelable("Plant", importPlant("Орхидея",getContext()));
                Navigation.findNavController(view).navigate(R.id.action_articlesFragment_to_articleFragment,bundle);
            }
        });
        binding.rose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putParcelable("Plant", importPlant("Роза",getContext()));
                Navigation.findNavController(view).navigate(R.id.action_articlesFragment_to_articleFragment,bundle);
            }
        });

    }

    public static Plant importPlant(String name, Context context){

        for (Plant plant : JSONHelper.importFromJson(context)){
            if (plant.getName().equals(name)) return plant;
        }
        return null;
    }

}