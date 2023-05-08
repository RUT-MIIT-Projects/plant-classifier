package com.plants.app.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class ImportImage {

    public static Drawable importImage(Context context, String fileName){
        try (InputStream inputStream = context.getAssets().open("images/" + fileName)){
            return Drawable.createFromStream(inputStream, null);
        }
        catch (IOException e){
            Log.e("ImportImage", "Image not found");
        }
        return null;
    }
}
