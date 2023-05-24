package com.plants.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.ViewModel;

import com.plants.app.plants.Root;
import com.plants.app.user.User;
import com.plants.app.utils.JSONHelper;
import com.plants.app.utils.ReadAndWrite;

import java.util.ArrayList;
import java.util.Arrays;


public class DataModel extends ViewModel {
    private User user;
    private Root root;
    private Bitmap avatar;
    private ArrayList<Integer> image = new ArrayList<>(Arrays.asList(
            R.drawable.echinocactus,
            R.drawable.mimosa,
            R.drawable.monstera,
            R.drawable.orchid,
            R.drawable.rose));

    public User getUser(Context context){
        if (user == null) user = User.getUser(context);
        return user;
    }

    public Root getRoot(Context context){
        if (root == null) root = Root.getRoot(context);
        return root;
    }

    public Bitmap getAvatar(Context context){
        if (avatar == null) {
            if (ReadAndWrite.loadAvatar(context) != null)
                return ReadAndWrite.loadAvatar(context);
            else return BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar);
        }
        return avatar;
    }

    public void saveAvatar(Context context, Bitmap avatar){
        this.avatar = avatar;
        ReadAndWrite.saveAvatar(context, avatar);
    }

    public void saveUser(Context context){
        JSONHelper.saveJsonUser(context,user);
    }

    public ArrayList<Integer> getImage() {
        return image;
    }
}
