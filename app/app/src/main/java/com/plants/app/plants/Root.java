package com.plants.app.plants;

import android.content.Context;

import com.plants.app.utils.JSONHelper;

import java.util.ArrayList;

public class Root {
    private ArrayList<Plant> plants;

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public Plant getPlant(String name){

        for (Plant plant : plants){
            if (plant.getName().equals(name)) return plant;
        }
        return null;
    }

    public static Root getRoot(Context context){
        return JSONHelper.importJsonPlants(context);
    }

}
