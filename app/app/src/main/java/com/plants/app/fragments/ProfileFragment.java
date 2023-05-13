package com.plants.app.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.plants.app.R;
import com.plants.app.databinding.FragmentProfileBinding;

import java.io.IOException;



public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    Bitmap bitmap;
    ImageView avatar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.editUsername.setVisibility(View.INVISIBLE);
        avatar = binding.userImage.findViewById(R.id.userImage);

        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGallery.launch(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
            }
        });

        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.username.setVisibility(View.INVISIBLE);
                binding.editUsername.setVisibility(View.VISIBLE);
            }
        });
        binding.editUsername.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    binding.username.setText(binding.editUsername.getText().toString());
                    binding.editUsername.setVisibility(View.INVISIBLE);
                    binding.username.setVisibility(View.VISIBLE);
                    return true;

                }
                return false;
            }
        });
    }
    ActivityResultLauncher<Intent> startGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                            avatar.setImageURI(selectedImage);
                        } catch (IOException e) {
                            Log.e("Profile.startGallery", "Failed to import image from gallery");
                        }
                    }
                    else Toast.makeText(getContext(), "Изображение не выбрано",Toast.LENGTH_LONG).show();
                }
            });
}