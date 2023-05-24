package com.plants.app.screens;

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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.plants.app.DataModel;
import com.plants.app.R;
import com.plants.app.utils.ImageClassifier;
import com.plants.app.databinding.CustomDialogDoneBinding;
import com.plants.app.databinding.CustomDialogFailedBinding;
import com.plants.app.databinding.FragmentHomeBinding;

import java.io.IOException;


public class HomeFragment extends Fragment {
    public FragmentHomeBinding binding;
    public Bitmap bitmap;
    private DataModel dataModel;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> openCamera;
    private ActivityResultLauncher<Intent> openGallery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataModel = new ViewModelProvider(requireActivity()).get(DataModel.class);
        registerPermission();
        initModules();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.camera.setOnClickListener(viewCast -> checkCameraPermission(getContext()));
        binding.gallery.setOnClickListener(viewCast -> openGallery.launch(new Intent(MediaStore.ACTION_PICK_IMAGES)));
    }

    private void checkCameraPermission(Context context){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            openCamera.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
        }
        else requestPermissionLauncher.launch(Manifest.permission.CAMERA);
    }
    private void registerPermission() {
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) checkCameraPermission(getContext());
                    else Toast.makeText(getContext(), "Нужно ваше разрешение", Toast.LENGTH_SHORT).show();
        });
    }

    private void initModules() {
        openCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                        Bundle bundle = result.getData().getExtras();
                        bitmap = (Bitmap) bundle.get("data");

                        handlerResult(getContext());
                    } else Toast.makeText(getContext(), "Изображение не сделано", Toast.LENGTH_LONG).show();
        });

        openGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                        Uri selectedImage = result.getData().getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                            handlerResult(getContext());
                        } catch (IOException e) {
                            Log.e("HomeFragment.startGallery", "Failed to import image from gallery");
                        }
                    } else Toast.makeText(getContext(), "Изображение не выбрано", Toast.LENGTH_LONG).show();
        });
    }

    private void handlerResult(Context context){
        if (bitmap != null) {
            String result = ImageClassifier.classifyImage(bitmap, context);
            if (result != null) {
                dataModel.getUser(getContext()).saveResult(result);
                dataModel.saveUser(context);

                showDialog(context, "DONE", result);
            }
            else showDialog(context, "FAILED", "None");
        }
    }

    private void showDialog(Context context, String command, String result){
        Dialog dialog = new Dialog(context);

        if (command.equals("DONE")) {
            dialog.setContentView(R.layout.custom_dialog_done);
            CustomDialogDoneBinding bindingDone = CustomDialogDoneBinding.bind(dialog.findViewById(R.id.dialog));
            bindingDone.result.setText(result);

            bindingDone.close.setOnClickListener(view -> dialog.cancel());
            bindingDone.toArticle.setOnClickListener(view -> {
                        dialog.cancel();
                        broadcast(result, getView());
                    });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
        else if (command.equals("FAILED")){
            dialog.setContentView(R.layout.custom_dialog_failed);
            CustomDialogFailedBinding bindingFailed = CustomDialogFailedBinding.bind(dialog.findViewById(R.id.dialog));

            bindingFailed.close.setOnClickListener(view -> dialog.cancel());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
        else Log.e("showDialog","Command error");
    }

    public static void broadcast(String namePlant, View view){
        Bundle bundle = new Bundle();
        bundle.putString("resultPlant", namePlant);
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_articleFragment,bundle);
    }
}