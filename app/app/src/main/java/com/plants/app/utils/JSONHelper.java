package com.plants.app.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.plants.app.plants.Root;
import com.plants.app.user.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class JSONHelper {
    private static final String FILE_NAME_PLANTS = "data.json";
    private static final String FILE_NAME_USER = "user.json";
    private static Gson gson = new Gson();

    public static Root importJsonPlants(Context context){

        try(InputStream inputStream = context.getAssets().open(FILE_NAME_PLANTS);
            InputStreamReader streamReader = new InputStreamReader(inputStream)){

            return gson.fromJson(streamReader, Root.class);
        }
        catch(IOException e){
            Log.e("JSONHelper", "ERROR: import JSON plants",e);
        }
        return null;
    }
    public static User importJsonUser(Context context){

        try(InputStream inputStream = context.openFileInput(FILE_NAME_USER);
            InputStreamReader streamReader = new InputStreamReader(inputStream)){

            return gson.fromJson(streamReader, User.class);
        }
        catch(IOException e){
            Log.e("JSONHelper", "ERROR: import JSON user",e);
        }
        return null;
    }
    public static void saveJsonUser(Context context, User user){

        try(PrintWriter printWriter = new PrintWriter(context.openFileOutput(FILE_NAME_USER, Context.MODE_PRIVATE))){
            String jsonString = gson.toJson(user);
            printWriter.write(jsonString);
        }
        catch(IOException e){
            Log.e("JSONHelper", "JSON import error",e);
        }
    }
}
