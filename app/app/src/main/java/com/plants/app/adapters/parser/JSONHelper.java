package com.plants.app.adapters.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JSONHelper {
    private static final String FILE_NAME = "data.json";

    public static ArrayList<Plant> importFromJson(Context context){
        Gson gson = new Gson();

        try(FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
            InputStreamReader streamReader = new InputStreamReader(fileInputStream)){

            Root root = gson.fromJson(streamReader, Root.class);

            return root.getPlants();
        }
        catch(IOException e){
            Log.e("JSONHelper", "JSON import error",e);
        }
        return null;
    }
}
