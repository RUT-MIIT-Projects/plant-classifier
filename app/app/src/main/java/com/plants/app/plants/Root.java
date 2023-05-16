package com.plants.app.plants;

import android.content.Context;
import java.util.ArrayList;

public class Root {
    private ArrayList<Plant> plants;

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public Plant importPlant(String name, Context context){

        for (Plant plant : plants){
            if (plant.getName().equals(name)) return plant;
        }
        return null;
    }
}
