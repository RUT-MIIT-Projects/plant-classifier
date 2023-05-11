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
        binding.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission(getContext());
            }

        });
        binding.gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGallery.launch(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
            }
        });
    }

    private void checkCameraPermission(Context context){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            startCamera.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
        }
        else requestPermissionLauncher.launch(Manifest.permission.CAMERA);
    }

    ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted){
                    checkCameraPermission(getContext());
                }
                else Toast.makeText(getContext(),"We need your mermission",Toast.LENGTH_SHORT).show();
            });

    ActivityResultLauncher<Intent> startCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bundle bundle = result.getData().getExtras();
                        bitmap = (Bitmap) bundle.get("data");
                    }
                }
            }
    );

    //TODO: refactoring
    ActivityResultLauncher<Intent> startGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                        } catch (IOException e) {
                            Log.e("HomeFragment.startGallery", "Failed to import image from gallery");
                        }
                    }
                }
            });

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