package com.plants.app.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.plants.app.R;
import com.plants.app.adapters.ImageClassifier;
import com.plants.app.adapters.JSONHelper;
import com.plants.app.adapters.ReadAndWrite;
import com.plants.app.user.LoadUser;
import com.plants.app.user.User;
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
        binding.done.setVisibility(View.INVISIBLE);
        avatar = binding.userImage.findViewById(R.id.userImage);

        handlerAvatar(getContext());
        User user = handlerUser(getContext());

        binding.upload.setOnClickListener(viewCast -> startGallery.launch(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)));
        handlerButtonsRename(getContext(), user);
    }
    private void handlerButtonsRename(Context context, User user){
        binding.edit.setOnClickListener(viewCast -> {
            binding.username.setVisibility(View.INVISIBLE);
            binding.editUsername.setVisibility(View.VISIBLE);

            binding.edit.setVisibility(View.INVISIBLE);
            binding.done.setVisibility(View.VISIBLE);
        });

        binding.editUsername.setOnKeyListener((view1, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER){
                rename(context, getView(), user);
                return true;
            }
            return false;
        });

        binding.done.setOnClickListener(view -> {rename(context, view, user);});
    }
    private void rename(Context context, View view, User user){
        String newUsername = binding.editUsername.getText().toString();
        binding.username.setText(newUsername);

        binding.editUsername.setVisibility(View.INVISIBLE);
        binding.done.setVisibility(View.INVISIBLE);

        binding.edit.setVisibility(View.VISIBLE);
        binding.username.setVisibility(View.VISIBLE);

        user.setUsername(newUsername);
        JSONHelper.saveJsonUser(context,user);
        hideKeyboard(context,view);
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    ActivityResultLauncher<Intent> startGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImage = result.getData().getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                        avatar.setImageURI(selectedImage);
                        ReadAndWrite.saveAvatar(getContext(),bitmap);
                    } catch (IOException e) {
                        Log.e("Profile.startGallery", "Failed to import image from gallery");
                    }
                }
                else Toast.makeText(getContext(), "Изображение не выбрано",Toast.LENGTH_LONG).show();
            });

    private void handlerAvatar(Context context) {
        if (ReadAndWrite.loadAvatar(context) != null)
            avatar.setImageBitmap(ReadAndWrite.loadAvatar(context));
    }

    private User handlerUser(Context context){
        User user = LoadUser.getUser(context);

        binding.username.setText(String.valueOf(user.getUsername()));
        binding.countEchinocactus.setText(user.getCountPlants().get(ImageClassifier.getPlants()[0]));
        binding.countMimosa.setText(user.getCountPlants().get(ImageClassifier.getPlants()[1]));
        binding.countMonstera.setText(user.getCountPlants().get(ImageClassifier.getPlants()[2]));
        binding.countOrchid.setText(user.getCountPlants().get(ImageClassifier.getPlants()[3]));
        binding.countRose.setText(user.getCountPlants().get(ImageClassifier.getPlants()[4]));

        return user;
    }
}