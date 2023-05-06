package com.plants.app.adapters.parser;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JSONHelper {
    private static final String FILE_NAME = "data.json";

    public static ArrayList<Plant> importFromJson(Context context){
        Gson gson = new Gson();

        try(InputStream inputStream = context.getAssets().open(FILE_NAME);
            InputStreamReader streamReader = new InputStreamReader(inputStream)){
            //InputStreamReader to Convert byte streams to character streams

            Root root = gson.fromJson(streamReader, Root.class);

            return root.getPlants();
        }
        catch(IOException e){
            Log.e("JSONHelper", "JSON import error",e);
        }
        return null;
    }
}
