package com.plants.app.screens;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plants.app.DataModel;
import com.plants.app.R;
import com.plants.app.utils.ImageClassifier;
import com.plants.app.buttons.Button;
import com.plants.app.buttons.ButtonAdapter;
import com.plants.app.databinding.FragmentArticlesBinding;

import java.util.ArrayList;
public class ArticlesFragment extends Fragment {
    private FragmentArticlesBinding binding;
    private DataModel dataModel;
    private ArrayList<Button> buttonsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataModel = new ViewModelProvider(requireActivity()).get(DataModel.class);
    }

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
        initButtons(getContext());
    }

    private void initButtons(Context context){

        for (int i = 0; i < ImageClassifier.getPlants().length; i++){
            buttonsList.add(new Button(ImageClassifier.getPlants()[i], dataModel.getImage().get(i)));
        }
        ButtonAdapter adapter = new ButtonAdapter(buttonsList);
        binding.recyclerViewButtons.setLayoutManager(new LinearLayoutManager(context));
        binding.recyclerViewButtons.setHasFixedSize(true);
        binding.recyclerViewButtons.setAdapter(adapter);
    }

    public static void broadcast(String namePlant, View view){
        Bundle bundle = new Bundle();
        bundle.putString("Plant", namePlant);
        Navigation.findNavController(view).navigate(R.id.action_articlesFragment_to_articleFragment,bundle);
    }
}