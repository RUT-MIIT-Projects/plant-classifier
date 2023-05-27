package com.plants.app;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.plants.app.plants.Root;
import com.plants.app.user.User;
import com.plants.app.utils.JSONHelper;
import com.plants.app.utils.ReadAndWrite;

import java.util.ArrayList;
import java.util.Arrays;


public class DataModel extends AndroidViewModel {
    private User user;
    private Root root;
    private Bitmap avatar;
    private ArrayList<Integer> image = new ArrayList<>(Arrays.asList(
            R.drawable.echinocactus,
            R.drawable.mimosa,
            R.drawable.monstera,
            R.drawable.orchid,
            R.drawable.rose));

    public DataModel(@NonNull Application application) {
        super(application);
        user = User.getUser(application.getApplicationContext());
        root = Root.getRoot(application.getApplicationContext());
        initAvatar();
    }

    public User getUser(){
        return user;
    }

    public Root getRoot(){
        return root;
    }

    private void initAvatar(){
        if (ReadAndWrite.loadAvatar(getApplication().getApplicationContext()) != null)
            avatar = ReadAndWrite.loadAvatar(getApplication().getApplicationContext());
        else avatar = BitmapFactory.decodeResource(getApplication().getApplicationContext().getResources(), R.drawable.avatar);
    }

    public Bitmap getAvatar(){
        return avatar;
    }

    public void saveAvatar(Bitmap avatar){
        this.avatar = avatar;
        ReadAndWrite.saveAvatar(getApplication().getApplicationContext(), avatar);
    }

    public void saveUser(){
        JSONHelper.saveJsonUser(getApplication().getApplicationContext(), user);
    }

    public ArrayList<Integer> getImage() {
        return image;
    }
}
