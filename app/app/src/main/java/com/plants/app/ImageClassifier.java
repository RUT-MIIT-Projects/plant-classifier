package com.plants.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.plants.app.ml.Model1;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImageClassifier {
    static String[] plants = {"Echinocactus", "Mimosa", "Monstera", "Orchid", "Rose"};

    public static String classifyImage(Bitmap bitmap, Context context) {
        int imageSize = MainActivity.IMAGE_SIZE;

        try {
            Model1 model = Model1.newInstance(context);

            // Creates inputs for reference
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 100, 100, 3}, DataType.FLOAT32);

            // byteBuffer contains pixels from the image. 4 byte (float) * area * RGB
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            //pixel arrays
            int[] intValues = new int[imageSize * imageSize];
            bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
            int pixel = 0;

            //iterate over each pixel and extract R, G, B values. Add those values individually to the byteBuffer
            for(int i = 0; i < imageSize; i++) {
                for(int j = 0; j < imageSize; j++) {
                    int pixelValue = intValues[pixel++]; //RGB together
                    byteBuffer.putFloat(((pixelValue >> 16) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat(((pixelValue >> 8) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat((pixelValue & 0xFF) * (1.f / 1));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result
            Model1.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidence = outputFeature0.getFloatArray();

            // find the index of the class with the biggest confidence
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidence.length; i++) {
                if (confidence[i] > maxConfidence) {
                    maxConfidence = confidence[i];
                    maxPos = i;
                }
            }

            // Releases model resources if no longer used.
            model.close();

            return plants[maxPos];

        } catch (IOException e) {
            Log.e("ImageClassifier", "Import error",e);
        }
        return "UNKNOWN! UNEXPECTED ERROR! XXX";
    }
}
