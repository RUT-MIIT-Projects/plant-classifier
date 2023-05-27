package com.plants.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadAndWrite {

    public static Bitmap importImage(Context context, String fileName){
        try (InputStream inputStream = context.getAssets().open("images/" + fileName)){
            return BitmapFactory.decodeStream(inputStream);
        }
        catch (IOException e){
            Log.e("ImportImage", "Image not found");
        }
        return null;
    }

    public static void saveAvatar(Context context, Bitmap bitmap){
        try (FileOutputStream fileOutputStream = context.openFileOutput("avatar.jpg", Context.MODE_PRIVATE)){
            bitmap.compress(Bitmap.CompressFormat.JPEG,50, fileOutputStream);
            fileOutputStream.flush();
        }
        catch (IOException e){
            Log.e("saveAvatar", "Save avatar error");
        }
    }

    public static Bitmap loadAvatar(Context context){
        try (InputStream inputStream = context.openFileInput("avatar.jpg")){

            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);

            return BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
        }
        catch (IOException e){
            Log.e("loadAvatar", "Image not found" + e);
        }
        return null;
    }
}
