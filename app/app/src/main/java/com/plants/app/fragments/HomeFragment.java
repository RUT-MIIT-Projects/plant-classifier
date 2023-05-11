package com.plants.app.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.plants.app.R;
import com.plants.app.databinding.CustomDialogDoneBinding;
import com.plants.app.databinding.CustomDialogFailedBinding;
import com.plants.app.databinding.FragmentHomeBinding;

import java.io.IOException;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    public Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showDialog(String command){
        Dialog dialog = new Dialog(getContext());

        if (command.equals("DONE")) {
            dialog.setContentView(R.layout.custom_dialog_done);
            CustomDialogDoneBinding bindingDone = CustomDialogDoneBinding.bind(dialog.findViewById(R.id.dialog));
            bindingDone.close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });
            bindingDone.toArticle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
        else if (command.equals("FAILED")){
            dialog.setContentView(R.layout.custom_dialog_failed);
            CustomDialogFailedBinding bindingFailed = CustomDialogFailedBinding.bind(dialog.findViewById(R.id.dialog));

            bindingFailed.close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
        else Log.e("showDialog","Error command");


    }

}