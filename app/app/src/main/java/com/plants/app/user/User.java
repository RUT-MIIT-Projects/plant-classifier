package com.plants.app.user;

import android.content.Context;

import com.plants.app.utils.ImageClassifier;
import com.plants.app.utils.JSONHelper;

import java.util.HashMap;

public class User {
    private String username;
    private HashMap<String, String> countPlants;

    public User(String username) {
        this.username = username;
        loadCountPlants();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    private void loadCountPlants(){
        countPlants = new HashMap<>();
        for (String name : ImageClassifier.getPlants()){
            countPlants.put(name, "0");
        }
    }

    public HashMap<String, String> getCountPlants() {
        return countPlants;
    }

    public void saveResult(String result){
        if (countPlants.containsKey(result)){
            countPlants.put(result, String.valueOf(Integer.parseInt(countPlants.get(result))+1));
        }
    }

    public static User getUser(Context context){
        User user;
        if (JSONHelper.importJsonUser(context) == null){
            user = new User("username");
            JSONHelper.saveJsonUser(context, user);
        }
        else{
            user = JSONHelper.importJsonUser(context);
        }
        return user;
    }
}
