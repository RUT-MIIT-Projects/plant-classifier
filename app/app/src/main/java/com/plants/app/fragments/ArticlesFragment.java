package com.plants.app.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plants.app.R;
import com.plants.app.databinding.FragmentArticlesBinding;
import com.plants.app.databinding.FragmentHomeBinding;

public class ArticlesFragment extends Fragment {
    FragmentArticlesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentArticlesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}