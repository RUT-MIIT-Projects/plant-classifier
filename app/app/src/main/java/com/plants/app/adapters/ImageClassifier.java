package com.plants.app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.plants.app.MainActivity;
import com.plants.app.ml.Model1;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImageClassifier {
    private static final String[] plants = {"Кактус", "Мимоза", "Монстера", "Орхидея", "Роза"};
    public static int IMAGE_SIZE = 100;

    public static String classifyImage(Bitmap bitmap, Context context) {
        int imageSize = IMAGE_SIZE;
        bitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false);

        try {
            Model1 model = Model1.newInstance(context);
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 100, 100, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
            int pixel = 0;

            for(int i = 0; i < imageSize; i++) {
                for(int j = 0; j < imageSize; j++) {
                    int pixelValue = intValues[pixel++]; //RGB together
                    byteBuffer.putFloat(((pixelValue >> 16) & 0xFF) * (1.f));
                    byteBuffer.putFloat(((pixelValue >> 8) & 0xFF) * (1.f));
                    byteBuffer.putFloat((pixelValue & 0xFF) * (1.f));
                }
            }
            inputFeature0.loadBuffer(byteBuffer);

            Model1.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] confidence = outputFeature0.getFloatArray();

            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidence.length; i++) {
                if (confidence[i] > maxConfidence) {
                    maxConfidence = confidence[i];
                    maxPos = i;
                }
            }
            model.close();
            return plants[maxPos];

        } catch (IOException e) {
            Log.e("ImageClassifier", "Import error",e);
        }
        return null;
    }

    public static String[] getPlants() {
        return plants;
    }
}
