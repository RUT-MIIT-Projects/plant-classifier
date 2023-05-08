package com.plants.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.plants.app.adapters.ImageClassifier;
import com.plants.app.databinding.ActivityMainBinding;
import com.plants.app.fragments.ArticlesFragment;
import com.plants.app.fragments.HomeFragment;
import com.plants.app.fragments.ProfileFragment;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    ActivityMainBinding binding;
    Bitmap bitmap;
    public final static int IMAGE_SIZE = 100;
    private static final int PICK_FROM_GALLERY = 1;
    private static final int GET_FROM_CAMERA = 2;
    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.articlesFragment, R.id.profileFragment).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        getPermission();

//
//        predictBtn.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onClick(View view) {
//                TextView resultOut = (TextView)findViewById(R.id.resultOut);
//
//                if (bitmap != null) {
//                    String result = ImageClassifier.classifyImage(bitmap, getApplicationContext());
//
//                    resultOut.setText("Result: " + result);
//                }
//                else Toast.makeText(getApplicationContext(), "Изображение не выбрано",Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    void getPermission(){
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
        if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, GALLERY_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE || requestCode == GALLERY_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                this.getPermission();
            }
        }
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(resultCode == RESULT_OK){
//            switch (requestCode) {
//                //gallery
//                case PICK_FROM_GALLERY:
//                    Uri selectedImage = data.getData();
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//
//                //camera
//                case GET_FROM_CAMERA:
//                    bitmap = (Bitmap) data.getExtras().get("data");
//                    break;
//            }
//            imageView.setImageBitmap(bitmap);
//            bitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_SIZE, IMAGE_SIZE, false);
//
//        }
//    }
}