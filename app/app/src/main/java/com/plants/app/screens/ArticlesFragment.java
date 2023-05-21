package com.plants.app.screens;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plants.app.R;
import com.plants.app.utils.ImageClassifier;
import com.plants.app.utils.JSONHelper;
import com.plants.app.buttons.Button;
import com.plants.app.buttons.ButtonAdapter;
import com.plants.app.databinding.FragmentArticlesBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class ArticlesFragment extends Fragment {
    private FragmentArticlesBinding binding;

    private ArrayList<Button> buttonsList;
    public  static ArrayList<Integer> image = new ArrayList<>(Arrays.asList(
            R.drawable.echinocactus,
            R.drawable.mimosa,
            R.drawable.monstera,
            R.drawable.orchid,
            R.drawable.rose));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentArticlesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonsList = new ArrayList<>();
        init(getContext());
    }

    public static void broadcast(String namePlant, View view, Context context){
        Bundle bundle = new Bundle();
        bundle.putParcelable("Plant", JSONHelper.importJsonPlants(context).importPlant(namePlant,context));
        Navigation.findNavController(view).navigate(R.id.action_articlesFragment_to_articleFragment,bundle);
    }

    private void init(Context context){

        for (int i = 0; i < ImageClassifier.getPlants().length; i++){
            buttonsList.add(new Button(ImageClassifier.getPlants()[i], image.get(i)));
        }
        ButtonAdapter adapter = new ButtonAdapter(buttonsList, context);
        binding.recyclerViewButtons.setLayoutManager(new LinearLayoutManager(context));
        binding.recyclerViewButtons.setHasFixedSize(true);
        binding.recyclerViewButtons.setAdapter(adapter);
    }
}