package com.plants.app.adapters;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.plants.app.plants.Plant;
import com.plants.app.plants.Root;
import com.plants.app.user.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class JSONHelper {
    private static final String FILE_NAME_PLANTS = "data.json";
    private static final String FILE_NAME_USER = "user.json";

    public static Root importJsonPlants(Context context){
        Gson gson = new Gson();

        try(InputStream inputStream = context.getAssets().open(FILE_NAME_PLANTS);
            InputStreamReader streamReader = new InputStreamReader(inputStream)){
            //InputStreamReader to Convert byte streams to character streams

            Root root = gson.fromJson(streamReader, Root.class);

            return root;
        }
        catch(IOException e){
            Log.e("JSONHelper", "ERROR: import JSON plants",e);
        }
        return null;
    }
    public static User importJsonUser(Context context){

        try(InputStream inputStream = context.openFileInput(FILE_NAME_USER);
            InputStreamReader streamReader = new InputStreamReader(inputStream)){

            Gson gson = new Gson();
            User user = gson.fromJson(streamReader, User.class);

            return user;
        }
        catch(IOException e){
            Log.e("JSONHelper", "ERROR: import JSON user",e);
        }
        return null;
    }
    public static void saveJsonUser(Context context, User user){

        try(PrintWriter printWriter = new PrintWriter(context.openFileOutput(FILE_NAME_USER, Context.MODE_PRIVATE))){
            Gson gson = new Gson();
            String jsonString = gson.toJson(user);
            printWriter.write(jsonString);
        }
        catch(IOException e){
            Log.e("JSONHelper", "JSON import error",e);
        }
    }
}
