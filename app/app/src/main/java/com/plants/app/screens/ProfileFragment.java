package com.plants.app.screens;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.plants.app.DataModel;
import com.plants.app.utils.ImageClassifier;
import com.plants.app.info.Info;
import com.plants.app.info.InfoAdapter;
import com.plants.app.user.User;
import com.plants.app.databinding.FragmentProfileBinding;

import java.io.IOException;
import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private DataModel dataModel;
    private Bitmap bitmap;
    private ArrayList<Info> infoList;
    private ActivityResultLauncher<Intent> openGallery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataModel = new ViewModelProvider(requireActivity()).get(DataModel.class);
        initModules();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        infoList = new ArrayList<>();
        initInfo(getContext(), dataModel.getUser());

        binding.editUsername.setVisibility(View.INVISIBLE);
        binding.done.setVisibility(View.INVISIBLE);

        binding.userImage.setImageBitmap(dataModel.getAvatar());

        User user = dataModel.getUser();
        binding.username.setText(String.valueOf(user.getUsername()));

        binding.upload.setOnClickListener(viewCast -> openGallery.launch(new Intent(MediaStore.ACTION_PICK_IMAGES)));
        handlerButtonsRename(getContext(), user);
    }

    private void initModules() {
        openGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri selectedImage = result.getData().getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                    binding.userImage.setImageURI(selectedImage);
                    dataModel.saveAvatar(bitmap);
                } catch (IOException e) {
                    Log.e("Profile.startGallery", "Failed to import image from gallery");
                }
            } else
                Toast.makeText(getContext(), "Изображение не выбрано", Toast.LENGTH_LONG).show();
        });
    }

    private void initInfo(Context context, User user){

        for (int i = 0; i < ImageClassifier.getPlants().length; i++){
            infoList.add(new Info(user.getCountPlants().get(ImageClassifier.getPlants()[i]), dataModel.getImage().get(i)));
        }

        InfoAdapter adapter = new InfoAdapter(infoList);
        binding.recyclerViewInfo.setLayoutManager(new GridLayoutManager(context, 2));
        binding.recyclerViewInfo.setHasFixedSize(true);
        binding.recyclerViewInfo.setAdapter(adapter);
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
        String newUsername = binding.editUsername.getText().toString().trim();
        binding.username.setText(newUsername);
        binding.editUsername.setText(newUsername);

        binding.editUsername.setVisibility(View.INVISIBLE);
        binding.done.setVisibility(View.INVISIBLE);

        binding.edit.setVisibility(View.VISIBLE);
        binding.username.setVisibility(View.VISIBLE);

        user.setUsername(newUsername);
        dataModel.saveUser();

        hideKeyboard(context,view);
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}